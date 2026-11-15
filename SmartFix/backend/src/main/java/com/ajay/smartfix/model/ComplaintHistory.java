package com.ajay.smartfix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="complaint_history")
public class ComplaintHistory {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="complaint_id") private Complaint complaint;
    @ManyToOne(optional=false) @JoinColumn(name="changed_by") private AppUser changedBy;
    private String action;
    private String note;
    private LocalDateTime createdAt=LocalDateTime.now();
    public ComplaintHistory() {}
    public ComplaintHistory(Complaint c, AppUser u, String a, String n){complaint=c;changedBy=u;action=a;note=n;}
    public Long getId(){return id;} public Complaint getComplaint(){return complaint;}
    public AppUser getChangedBy(){return changedBy;} public String getAction(){return action;}
    public String getNote(){return note;} public LocalDateTime getCreatedAt(){return createdAt;}
}
