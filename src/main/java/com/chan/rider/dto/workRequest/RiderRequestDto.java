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
public class RiderRequestDto {
    @NotEmpty
    private String localCode;

    @NotNull
    private LocalDate date;

    private boolean pm;
}
