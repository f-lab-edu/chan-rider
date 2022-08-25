package com.chan.rider.service;

import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.domain.Invoice;
import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.domain.WorkRequestStatusEnum;
import com.chan.rider.dto.invoice.InvoiceItemDto;
import com.chan.rider.dto.invoice.InvoiceListDto;
import com.chan.rider.dto.workRequest.WorkRequestListDto;
import com.chan.rider.dto.workRequest.WorkRequestLogisticsDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.exception.RiderFindFailException;
import com.chan.rider.repository.InvoiceRepository;
import com.chan.rider.repository.RiderRepository;
import com.chan.rider.repository.WorkRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RiderService {

    private final RiderRepository riderRepository;

    private final InvoiceRepository invoiceRepository;

    private final WorkRequestRepository workRequestRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    @Transactional
    public Rider signUp(RiderDto dto) {
        Rider rider = new Rider();
        rider.setAccountId(dto.getAccountId());
        rider.setName(dto.getName());
        rider.setTelephone(dto.getTelephone());
        rider.setAddress(dto.getAddress());
        this.riderRepository.save(rider);

        return rider;
    }

    @Transactional
    public WorkRequest registerDelivery(WorkRequestRegisterDto dto) throws JsonProcessingException {
        Rider rider = this.riderRepository.findById(dto.getRiderId());

        if (rider == null) {
            throw new RuntimeException();
        }

        WorkRequest workRequest = new WorkRequest();
        workRequest.setCenterCode(dto.getCenterCode());
        workRequest.setPM(dto.isPm());
        workRequest.setCount(dto.getCount());
        workRequest.setDate(dto.getDate());
        workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.SUBMIT);

        rider.addWorkRequest(workRequest);

        this.workRequestRepository.save(workRequest);

        //대기 리스트에 등록
        String key = makeKey(dto.getCenterCode(), dto.getDate(), dto.isPm());
        RiderDto riderDto = new RiderDto(rider);
        String riderValue = objectMapper.writeValueAsString(riderDto);
        this.redisTemplate.opsForList().rightPush(key, riderValue);

        return workRequest;
    }

    @Transactional
    public RiderDto createWorkRequestListDto(WorkRequestLogisticsDto dto) throws JsonProcessingException {

        //대기 리스트에서 대기중인 Rider pop
        String key = makeKey(dto.getCenterCode(), dto.getDate(), dto.isPm());
        Object riderValue = this.redisTemplate.opsForList().leftPop(key);
        if(riderValue != null){
            RiderDto riderInfo = objectMapper.readValue(riderValue.toString(), RiderDto.class);

            //해당하는 라이더의 근무요청 데이터 찾아서 상태 변경
            WorkRequest workRequest = this.workRequestRepository.findByRiderIdAndDateAndIsPM(riderInfo.getId(), dto.getDate(), dto.isPm());
            workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.DELIVERY_WAIT);
            this.workRequestRepository.save(workRequest);

            return riderInfo;
        }
        else {
            throw new RiderFindFailException("출근 대기중인 라이더가 없습니다.");
        }

    }

    @Transactional
    public Message matchDelivery(InvoiceListDto dto) {
        Message message = new Message();
        WorkRequest workRequest = this.workRequestRepository.findByRiderIdAndDateAndIsPM(dto.getRiderId(), dto.getDate(), dto.isPM());

        if (workRequest == null) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("일치하는 라이더가 없습니다.");
            return message;
        }

        for (InvoiceItemDto item : dto.getInvoices()) {
            Invoice invoice = new Invoice();
            invoice.setLogisticsInvoiceId(item.getInvoiceId());
            invoice.setName(item.getName());
            invoice.setAddress(item.getAddress());
            invoice.setTelephone(item.getTelephone());
            workRequest.addInvoice(invoice);
            this.invoiceRepository.save(invoice);
        }

        message.setStatus(StatusEnum.OK);
        return message;
    }

    private String makeKey(String centerCode, LocalDate date, boolean isPM){

        String meridiem = isPM ? "PM" : "AM";

        return centerCode + "_" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +  "_" + meridiem;

    }

}
