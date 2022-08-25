package com.chan.rider.dto.workRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRequestLogisticsDto {
    @NotEmpty
    private String centerCode;

    @NotNull
    private LocalDate date;

    private boolean pm;
}
