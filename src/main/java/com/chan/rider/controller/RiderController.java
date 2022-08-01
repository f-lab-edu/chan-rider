package com.chan.rider.controller;

import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.dto.invoice.InvoiceListDto;
import com.chan.rider.dto.workRequest.WorkRequestListDto;
import com.chan.rider.dto.workRequest.WorkRequestLogisticsDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rider")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/signUp")
    public ResponseEntity<Message> signUp(@Valid @RequestBody RiderDto dto) {
        Message message = new Message();

        Rider rider = riderService.signUp(dto);
        message.setStatus(StatusEnum.OK);
        message.setMessage("회원가입 완료");
        message.setData(rider);

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/delivery/register")
    public ResponseEntity<Message> registerDelivery(@Valid @RequestBody WorkRequestRegisterDto dto) {
        Message message = new Message();

        WorkRequest workRequest = this.riderService.registerDelivery(dto);
        message.setStatus(StatusEnum.OK);
        message.setMessage("출근 등록 완료");
        message.setData(workRequest);

        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/delivery")
    public ResponseEntity<Message> getRiders(@Valid @RequestBody WorkRequestLogisticsDto dto) {
        Message message = new Message();

        WorkRequestListDto workRequestListDto = this.riderService.createWorkRequestListDto(dto);

        message.setStatus(StatusEnum.OK);
        message.setMessage("ok");
        message.setData(workRequestListDto);

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/delivery/match")
    public ResponseEntity<Message> matchDelivery(@Valid @RequestBody InvoiceListDto dto) {
        Message message = this.riderService.matchDelivery(dto);
        return ResponseEntity.ok().body(message);
    }
}
