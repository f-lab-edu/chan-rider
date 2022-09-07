package com.chan.rider.repository;

import com.chan.rider.domain.WorkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkRequestRepository extends JpaRepository<WorkRequest, Integer> {
    WorkRequest findById(Long id);

    List<WorkRequest> findByLocalCodeAndDateAndIsPM(String localCode, LocalDate date, boolean isPM);

    WorkRequest findByRiderIdAndDateAndIsPM(Long riderId, LocalDate date, boolean isPM);

}
