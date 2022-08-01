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
public class WorkRequestRegisterDto {
    @NotEmpty
    private String accountId;

    @NotEmpty
    private Long riderId;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private String centerCode;

    @NotEmpty
    private boolean isPM;

    @NotEmpty
    private int count;

}
