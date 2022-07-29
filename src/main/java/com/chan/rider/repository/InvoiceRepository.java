package com.chan.rider.repository;

import com.chan.rider.domain.Invoice;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvoiceRepository extends Repository<Invoice, Integer> {
    void save(Invoice invoice);

    void saveAll(List<Invoice> invoices);
}
