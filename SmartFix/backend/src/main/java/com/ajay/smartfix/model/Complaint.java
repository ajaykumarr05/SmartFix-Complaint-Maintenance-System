package com.ajay.smartfix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="complaints")
public class Complaint {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(nullable=false, length=2000) private String description;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Category category;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Priority priority;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private ComplaintStatus status=ComplaintStatus.OPEN;
    @ManyToOne(optional=false) @JoinColumn(name="resident_id") private AppUser resident;
    @ManyToOne @JoinColumn(name="technician_id") private AppUser technician;
    private String location;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedAt=LocalDateTime.now();
    private LocalDateTime dueAt;
    private LocalDateTime resolvedAt;

    public Complaint() {}
    public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Category getCategory(){return category;} public void setCategory(Category v){category=v;}
    public Priority getPriority(){return priority;} public void setPriority(Priority v){priority=v;}
    public ComplaintStatus getStatus(){return status;} public void setStatus(ComplaintStatus v){status=v;}
    public AppUser getResident(){return resident;} public void setResident(AppUser v){resident=v;}
    public AppUser getTechnician(){return technician;} public void setTechnician(AppUser v){technician=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
    public void setUpdatedAt(LocalDateTime v){updatedAt=v;} public LocalDateTime getDueAt(){return dueAt;} public void setDueAt(LocalDateTime v){dueAt=v;}
    public LocalDateTime getResolvedAt(){return resolvedAt;} public void setResolvedAt(LocalDateTime v){resolvedAt=v;}
}
