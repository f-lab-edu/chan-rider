package com.chan.rider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRequestDto {
    @NotEmpty
    private String deliveryCode;

    @NotEmpty
    private Long riderId;

    @NotEmpty
    private boolean isPM;

    @NotEmpty
    private int count;

}
