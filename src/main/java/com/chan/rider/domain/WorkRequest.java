package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Table(name = "work_requests")
public class WorkRequest extends BaseEntity{

    @Column(name = "delivery_code", length = 5)
    @NotEmpty
    private String deliveryCode;

    @Column(name = "is_pm")
    @NotEmpty
    private boolean isPM;

    @Column(name = "count")
    @NotEmpty
    private int count;

    @OneToOne(mappedBy = "workRequest")
    private Rider rider;

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public void setPM(boolean PM) {
        isPM = PM;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }
}
