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
import com.chan.rider.repository.InvoiceRepository;
import com.chan.rider.repository.RiderRepository;
import com.chan.rider.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    private final InvoiceRepository invoiceRepository;

    private final WorkRequestRepository workRequestRepository;

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
    public WorkRequest registerDelivery(WorkRequestRegisterDto dto) {
        Rider rider = this.riderRepository.findById(dto.getRiderId());

        if (rider == null) {
            throw new RuntimeException();
        }

        WorkRequest workRequest = new WorkRequest();
        workRequest.setCenterCode(dto.getCenterCode());
        workRequest.setPM(dto.isPM());
        workRequest.setCount(dto.getCount());
        workRequest.setDate(dto.getDate());
        workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.SUBMIT);
        rider.addWorkRequest(workRequest);
        this.workRequestRepository.save(workRequest);

        return workRequest;
    }

    @Transactional
    public WorkRequestListDto createWorkRequestListDto(WorkRequestLogisticsDto dto) {
        List<WorkRequest> workRequests = this.workRequestRepository.findByCenterCodeAndDateAndIsPM(dto.getCenterCode(), dto.getDate(), dto.isPM());

        WorkRequestListDto workRequestListDto = new WorkRequestListDto();
        for (WorkRequest workRequest : workRequests) {
            workRequestListDto.addWorkRequest(workRequest);
            workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.DELIVERY_WAIT);
        }
        return workRequestListDto;
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

}
