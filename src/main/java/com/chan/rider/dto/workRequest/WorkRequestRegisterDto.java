package com.chan.rider.dto.workRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRequestRegisterDto {
    @NotEmpty
    private String accountId;

    private Long riderId;

    @NotNull
    private LocalDate date;

    @NotEmpty
    private String centerCode;

    private boolean pm;

    @PositiveOrZero
    private int count;

}
