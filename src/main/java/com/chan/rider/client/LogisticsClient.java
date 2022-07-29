package com.chan.rider.client;

import com.chan.rider.common.Message;
import com.chan.rider.dto.WorkRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="logistics", url="${logistics.ribbon.listOfServers}")
public interface LogisticsClient {

    @PutMapping("/logistics/delivery")
    Message requestDelivery(@RequestBody WorkRequestDto deliveryRegisterDto);
}
