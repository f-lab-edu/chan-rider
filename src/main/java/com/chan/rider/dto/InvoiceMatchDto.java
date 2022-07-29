package com.chan.rider.dto;

import com.chan.rider.domain.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceMatchDto {

    @NotEmpty
    private Long riderId;

    @NotEmpty
    private List<Invoice> invoices;
}
