package com.chan.rider.controller;

import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.domain.Invoice;
import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.dto.invoice.InvoiceItemDto;
import com.chan.rider.dto.invoice.MatchingRequestDto;
import com.chan.rider.dto.invoice.MatchingResponseDto;
import com.chan.rider.dto.rider.RiderInfoDto;
import com.chan.rider.dto.workRequest.RiderRequestDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.service.RiderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Message> registerDelivery(@Valid @RequestBody WorkRequestRegisterDto dto) throws JsonProcessingException {
        Message message = new Message();

        WorkRequest workRequest = this.riderService.registerDelivery(dto);
        message.setStatus(StatusEnum.OK);
        message.setMessage("출근 등록 완료");
        //message.setData(workRequest);

        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/delivery")
    public ResponseEntity<Message> getRider(@Valid @RequestBody RiderRequestDto dto) throws JsonProcessingException {
        Message message = new Message();

        RiderInfoDto riderInfo = this.riderService.riderRequest(dto);

        message.setStatus(StatusEnum.OK);
        message.setMessage("라이더 반환 완료");
        message.setData(riderInfo);

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/delivery/match")
    public ResponseEntity<Message> matchDelivery(@Valid @RequestBody MatchingRequestDto dto) {
        Message message = new Message();

        List<Invoice> invoiceList = this.riderService.matchDelivery(dto);
        List<InvoiceItemDto> responseItemList = invoiceList.stream().map(InvoiceItemDto::new).toList();

        MatchingResponseDto matchingResponseDto = new MatchingResponseDto();
        matchingResponseDto.setInvoiceCount(invoiceList.size());
        matchingResponseDto.setInvoiceItemList(responseItemList);

        message.setStatus(StatusEnum.OK);
        message.setMessage("인보이스 매칭 완료");
        message.setData(matchingResponseDto);

        return ResponseEntity.ok().body(message);
    }
}
