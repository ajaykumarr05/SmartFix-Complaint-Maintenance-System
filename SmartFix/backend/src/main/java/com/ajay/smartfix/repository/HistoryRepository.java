package com.ajay.smartfix.repository;
import com.ajay.smartfix.model.ComplaintHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface HistoryRepository extends JpaRepository<ComplaintHistory,Long> {
    List<ComplaintHistory> findByComplaintIdOrderByCreatedAtDesc(Long id);
}
