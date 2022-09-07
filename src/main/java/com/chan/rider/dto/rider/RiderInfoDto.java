package com.chan.rider.dto.rider;

import com.chan.rider.domain.Address;
import com.chan.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RiderInfoDto {

    private Long id;

    @NotEmpty
    private String accountId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotNull
    private Address address;

    public RiderInfoDto(Rider rider) {
        this.id = rider.getId();
        this.accountId = rider.getAccountId();
        this.name = rider.getName();
        this.telephone = rider.getTelephone();
        this.address = rider.getAddress();
    }
}
