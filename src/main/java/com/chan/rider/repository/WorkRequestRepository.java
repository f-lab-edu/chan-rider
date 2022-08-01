package com.chan.rider.repository;

import com.chan.rider.domain.WorkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRequestRepository extends JpaRepository<WorkRequest, Integer> {
    WorkRequest findById(Long id);
}
