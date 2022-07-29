package com.chan.rider.repository;

import com.chan.rider.domain.WorkRequest;
import org.springframework.data.repository.Repository;

public interface WorkRequestRepository extends Repository<WorkRequest, Integer> {
    void save(WorkRequest workRequest);
}
