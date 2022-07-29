package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Table(name = "invoices")
public class Invoice extends NameEntity{

    @Embedded
    private Address address;

    @Column(name = "telephone", length = 12)
    @NotEmpty
    private String telephone;

    @Column(name = "logistics_invoice_id")
    private Long logisticsInvoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @Column(name = "status")
    @NotEmpty
    private InvoiceStatusEnum status = InvoiceStatusEnum.ORDER;

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLogisticsInvoiceId(Long logisticsInvoiceId) {
        this.logisticsInvoiceId = logisticsInvoiceId;
    }
}
