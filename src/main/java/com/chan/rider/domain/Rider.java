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

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
    private List<WorkRequest> workRequests = new ArrayList<>();


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addWorkRequest(WorkRequest workRequest) {
        workRequest.setRider(this);
        workRequests.add(workRequest);
    }
}
