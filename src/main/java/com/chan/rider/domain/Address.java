package com.chan.rider.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getSigunguCode() == address.getSigunguCode() && Objects.equals(getDoroAddress(), address.getDoroAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDoroAddress(), getSigunguCode());
    }
}

