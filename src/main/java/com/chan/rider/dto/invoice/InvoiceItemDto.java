package com.chan.rider.dto.invoice;

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
public class InvoiceItemDto {
    @NotEmpty
    private Long invoiceId;

    @NotEmpty
    private String name;

    @NotEmpty
    private Address address;

    @NotEmpty
    private String telephone;
}
