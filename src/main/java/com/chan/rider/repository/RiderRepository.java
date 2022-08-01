package com.chan.rider.repository;

import com.chan.rider.domain.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Integer> {
    Rider findById(Long id);
}
