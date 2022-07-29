package com.chan.rider.dto;

import com.chan.rider.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RiderDto {

    @NotEmpty
    private String accountId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotEmpty
    private Address address;
}
