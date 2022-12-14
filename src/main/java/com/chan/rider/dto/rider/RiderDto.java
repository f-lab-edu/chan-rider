package com.chan.rider.dto.rider;

import com.chan.rider.domain.Address;
import com.chan.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @NotNull
    private Address address;

}
