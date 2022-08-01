package com.chan.rider.dto.workRequest;

import com.chan.rider.domain.WorkRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRequestListDto {
    @NotEmpty
    private List<WorkRequestItemDto> workRequests = new ArrayList<>();

    public void addWorkRequest(WorkRequest workRequest) {
        WorkRequestItemDto workRequestItemDto = new WorkRequestItemDto();
        workRequestItemDto.setRiderId(workRequest.getRider().getId());
        workRequestItemDto.setCount(workRequest.getCount());
        this.workRequests.add(workRequestItemDto);
    }
}
