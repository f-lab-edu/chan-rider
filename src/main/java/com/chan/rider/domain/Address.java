package com.chan.rider.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Address {

    @Column(name = "doro_address", length = 100)
    @NotEmpty
    private String doroAddress;

    @Column(name = "sigungu_code")
    @NotNull
    private int sigunguCode;

    public void setDoroAddress(String doroAddress) {
        this.doroAddress = doroAddress;
    }

    public void setSigunguCode(int sigunguCode) {
        this.sigunguCode = sigunguCode;
    }
}

