package com.chan.rider.dto.workRequest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRequestItemDto {
    @NotEmpty
    private Long riderId;

    @NotEmpty
    private int count;
}
