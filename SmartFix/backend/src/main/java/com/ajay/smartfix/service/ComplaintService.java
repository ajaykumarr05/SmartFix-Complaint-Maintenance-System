package com.ajay.smartfix.service;

import com.ajay.smartfix.dto.*;
import com.ajay.smartfix.model.*;
import com.ajay.smartfix.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ComplaintService {
    private final ComplaintRepository complaints; private final UserRepository users; private final HistoryRepository history;
    public ComplaintService(ComplaintRepository c,UserRepository u,HistoryRepository h){complaints=c;users=u;history=h;}
    public List<ComplaintResponse> list(AppUser actor){
        List<Complaint> all = switch(actor.getRole()){
            case ADMIN -> complaints.findAll();
            case TECHNICIAN -> complaints.findByTechnicianIdOrderByCreatedAtDesc(actor.getId());
            default -> complaints.findByResidentIdOrderByCreatedAtDesc(actor.getId());
        };
        return all.stream().sorted(Comparator.comparing(Complaint::getCreatedAt).reversed()).map(this::map).toList();
    }
    public ComplaintResponse get(Long id,AppUser actor){
        Complaint c=find(id); ensureCanView(c,actor); return map(c);
    }
    @Transactional public ComplaintResponse create(ComplaintRequest r,AppUser actor){
        if(actor.getRole()==Role.TECHNICIAN) throw new IllegalArgumentException("Technicians cannot submit resident complaints");
        Complaint c=new Complaint();c.setTitle(r.title());c.setDescription(r.description());c.setCategory(r.category());
        c.setPriority(r.priority());c.setLocation(r.location());c.setResident(actor);
        int hours=switch(r.priority()){case CRITICAL->4;case HIGH->24;case MEDIUM->72;case LOW->120;};
        c.setDueAt(LocalDateTime.now().plusHours(hours)); complaints.save(c);
        history.save(new ComplaintHistory(c,actor,"CREATED","Complaint submitted"));
        return map(c);
    }
    @Transactional public ComplaintResponse assign(Long id,Long techId,AppUser actor){
        requireRole(actor,Role.ADMIN); Complaint c=find(id);
        AppUser tech=users.findById(techId).orElseThrow(()->new NoSuchElementException("Technician not found"));
        if(tech.getRole()!=Role.TECHNICIAN) throw new IllegalArgumentException("Selected user is not a technician");
        c.setTechnician(tech);c.setStatus(ComplaintStatus.ASSIGNED);c.setUpdatedAt(LocalDateTime.now());complaints.save(c);
        history.save(new ComplaintHistory(c,actor,"ASSIGNED","Assigned to "+tech.getName()));return map(c);
    }
    @Transactional public ComplaintResponse updateStatus(Long id,StatusRequest r,AppUser actor){
        Complaint c=find(id);
        if(actor.getRole()!=Role.ADMIN && (actor.getRole()!=Role.TECHNICIAN || c.getTechnician()==null || !c.getTechnician().getId().equals(actor.getId())))
            throw new IllegalArgumentException("Only the assigned technician or admin can update this complaint");
        if(actor.getRole()==Role.TECHNICIAN && r.status()==ComplaintStatus.CLOSED) throw new IllegalArgumentException("Only admin can close a complaint");
        c.setStatus(r.status());c.setUpdatedAt(LocalDateTime.now());
        if(r.status()==ComplaintStatus.RESOLVED)c.setResolvedAt(LocalDateTime.now());
        complaints.save(c);history.save(new ComplaintHistory(c,actor,"STATUS_CHANGED",r.note()==null?r.status().name():r.note()));
        return map(c);
    }
    public List<Map<String,Object>> history(Long id,AppUser actor){
        Complaint c=find(id);ensureCanView(c,actor);
        return history.findByComplaintIdOrderByCreatedAtDesc(id).stream().map(h->{
            Map<String,Object> m=new LinkedHashMap<>();m.put("id",h.getId());m.put("action",h.getAction());
            m.put("note",h.getNote());m.put("changedBy",h.getChangedBy().getName());m.put("createdAt",h.getCreatedAt());return m;
        }).toList();
    }
    public List<Map<String,Object>> technicians(){
        return users.findByRole(Role.TECHNICIAN).stream().map(u->Map.<String,Object>of("id",u.getId(),"name",u.getName(),"email",u.getEmail())).toList();
    }
    public Map<String,Object> stats(AppUser actor){
        List<ComplaintResponse> list=list(actor);Map<String,Object> m=new LinkedHashMap<>();
        m.put("total",list.size());m.put("open",list.stream().filter(x->x.status()==ComplaintStatus.OPEN).count());
        m.put("inProgress",list.stream().filter(x->x.status()==ComplaintStatus.IN_PROGRESS||x.status()==ComplaintStatus.ASSIGNED).count());
        m.put("resolved",list.stream().filter(x->x.status()==ComplaintStatus.RESOLVED||x.status()==ComplaintStatus.CLOSED).count());
        m.put("overdue",list.stream().filter(x->x.dueAt()!=null&&x.dueAt().isBefore(LocalDateTime.now())&&x.status()!=ComplaintStatus.RESOLVED&&x.status()!=ComplaintStatus.CLOSED).count());
        return m;
    }
    private Complaint find(Long id){return complaints.findById(id).orElseThrow(()->new NoSuchElementException("Complaint not found"));}
    private void ensureCanView(Complaint c,AppUser u){
        if(u.getRole()==Role.ADMIN)return;
        if(u.getRole()==Role.TECHNICIAN && c.getTechnician()!=null && c.getTechnician().getId().equals(u.getId()))return;
        if(c.getResident().getId().equals(u.getId()))return;
        throw new IllegalArgumentException("You are not allowed to view this complaint");
    }
    private void requireRole(AppUser u,Role r){if(u.getRole()!=r)throw new IllegalArgumentException("Admin access required");}
    private ComplaintResponse map(Complaint c){return new ComplaintResponse(c.getId(),c.getTitle(),c.getDescription(),c.getCategory(),c.getPriority(),c.getStatus(),
        c.getResident().getName(),c.getResident().getId(),c.getTechnician()==null?null:c.getTechnician().getName(),
        c.getTechnician()==null?null:c.getTechnician().getId(),c.getLocation(),c.getCreatedAt(),c.getUpdatedAt(),c.getDueAt(),c.getResolvedAt());}
}
