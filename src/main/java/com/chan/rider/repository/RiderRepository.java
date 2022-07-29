package com.chan.rider.repository;

import com.chan.rider.domain.Rider;
import org.springframework.data.repository.Repository;

public interface RiderRepository extends Repository<Rider, Integer> {
    void save(Rider rider);

    Rider findById(Long id);
}
