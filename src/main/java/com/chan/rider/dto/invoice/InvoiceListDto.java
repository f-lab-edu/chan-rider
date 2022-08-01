package com.chan.rider.dto.invoice;

import com.chan.rider.domain.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceListDto {
    @NotEmpty
    private Long riderId;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private boolean isPM;

    @NotEmpty
    private List<InvoiceItemDto> invoices;

}
