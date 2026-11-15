package com.ajay.smartfix.repository;
import com.ajay.smartfix.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByResidentIdOrderByCreatedAtDesc(Long id);
    List<Complaint> findByTechnicianIdOrderByCreatedAtDesc(Long id);
    long countByStatus(ComplaintStatus status);
}
