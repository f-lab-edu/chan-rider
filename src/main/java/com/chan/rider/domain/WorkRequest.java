package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "work_requests")
public class WorkRequest extends BaseEntity{

    @Column(name = "local_code", length = 3)
    @NotEmpty
    private String localCode;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "is_pm")
    private boolean isPM;

    @Column(name = "count")
    @Positive
    private int count;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private WorkRequestStatusEnum workRequestStatusEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @OneToMany(mappedBy = "workRequest", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPM(boolean PM) {
        isPM = PM;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setWorkRequestStatusEnum(WorkRequestStatusEnum workRequestStatusEnum) {
        this.workRequestStatusEnum = workRequestStatusEnum;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public void addInvoice(Invoice invoice) {
        invoice.setWorkRequest(this);
        this.invoices.add(invoice);
    }
}
