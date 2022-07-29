package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "riders")
public class Rider extends NameEntity {

    @Column(name = "account_id")
    @NotEmpty
    private String accountId;

    @Column(name = "telephone", length = 12)
    @NotEmpty
    private String telephone;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "work_request_id")
    private WorkRequest workRequest;

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addInvoice(Invoice invoice) {
        invoice.setRider(this);
        this.invoices.add(invoice);
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }
}
