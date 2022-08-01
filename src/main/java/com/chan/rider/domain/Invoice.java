package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Table(name = "invoices")
public class Invoice extends NameEntity{

    @Column(name = "logistics_invoice_id")
    private Long logisticsInvoiceId;

    @Embedded
    private Address address;

    @Column(name = "telephone", length = 12)
    @NotEmpty
    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_request_id")
    private WorkRequest workRequest;

    @Column(name = "status")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum status = InvoiceStatusEnum.ORDER;


    public void setLogisticsInvoiceId(Long logisticsInvoiceId) {
        this.logisticsInvoiceId = logisticsInvoiceId;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }
}
