package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Getter
public class NameEntity extends BaseEntity {

    @Column(name = "name", length = 10)
    @NotBlank
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
