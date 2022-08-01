package com.chan.rider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceRequestDto {

    @NotEmpty
    private Long workRequestId;

    @NotEmpty
    private Long riderId;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private String deliveryCode;

    @NotEmpty
    private boolean isPM;

    @NotEmpty
    private int count;
}
