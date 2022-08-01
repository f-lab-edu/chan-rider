package com.chan.rider.dto.workRequest;

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
public class WorkRequestLogisticsDto {
    @NotEmpty
    private String centerCode;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private boolean isPM;
}
