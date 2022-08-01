package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "work_requests")
public class WorkRequest extends BaseEntity{

    @Column(name = "delivery_code", length = 5)
    @NotEmpty
    private String deliveryCode;

    @Column(name = "date")
    @NotEmpty
    private LocalDate date;

    @Column(name = "is_pm")
    @NotEmpty
    private boolean isPM;

    @Column(name = "count")
    @NotEmpty
    private int count;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private WorkRequestStatusEnum workRequestStatusEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @OneToMany(mappedBy = "workRequest", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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

    public void setInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            invoice.setWorkRequest(this);
        }
        this.invoices = invoices;
    }
}
