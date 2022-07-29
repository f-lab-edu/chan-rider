package com.chan.rider.controller;

import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.domain.Rider;
import com.chan.rider.dto.InvoiceMatchDto;
import com.chan.rider.dto.WorkRequestDto;
import com.chan.rider.dto.RiderDto;
import com.chan.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Message> registerDelivery(@Valid @RequestBody WorkRequestDto dto) {
        Message message = this.riderService.registerDelivery(dto);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/delivery/match")
    public ResponseEntity<Message> matchDelivery(@Valid @RequestBody InvoiceMatchDto dto) {
        Message message = this.riderService.matchDelivery(dto);
        return ResponseEntity.ok().body(message);
    }
}
