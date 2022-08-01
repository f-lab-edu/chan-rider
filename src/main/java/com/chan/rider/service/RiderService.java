package com.chan.rider.service;

import com.chan.rider.client.LogisticsClient;
import com.chan.rider.common.Message;
import com.chan.rider.common.StatusEnum;
import com.chan.rider.domain.Invoice;
import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.domain.WorkRequestStatusEnum;
import com.chan.rider.dto.InvoiceMatchDto;
import com.chan.rider.dto.InvoiceRequestDto;
import com.chan.rider.dto.WorkRequestDto;
import com.chan.rider.dto.RiderDto;
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

    private final LogisticsClient logisticsClient;

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
    public Message registerDelivery(WorkRequestDto dto) {
        Rider rider = this.riderRepository.findById(dto.getRiderId());

        if (rider == null) {
            throw new RuntimeException();
        }

        WorkRequest workRequest = new WorkRequest();
        workRequest.setDeliveryCode(dto.getDeliveryCode());
        workRequest.setPM(dto.isPM());
        workRequest.setCount(dto.getCount());
        workRequest.setDate(dto.getDate());
        workRequest.setRider(rider);
        workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.SUBMIT);
        this.workRequestRepository.save(workRequest);

        InvoiceRequestDto invoiceRequestDto = new InvoiceRequestDto();
        invoiceRequestDto.setWorkRequestId(workRequest.getId());
        invoiceRequestDto.setRiderId(rider.getId());
        invoiceRequestDto.setDate(workRequest.getDate());
        invoiceRequestDto.setDeliveryCode(workRequest.getDeliveryCode());
        invoiceRequestDto.setPM(workRequest.isPM());
        invoiceRequestDto.setCount(workRequest.getCount());

        Message message = this.logisticsClient.requestDelivery(invoiceRequestDto);

        if (message.getStatus().equals(StatusEnum.BAD_REQUEST)) {
            throw new RuntimeException();
        }

        return message;
    }


    @Transactional
    public Message matchDelivery(InvoiceMatchDto dto) {
        Message message = new Message();
        WorkRequest workRequest = this.workRequestRepository.findById(dto.getWorkRequestId());

        if (workRequest == null) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("일치하는 라이더가 없습니다.");
            return message;
        }

        workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.DELIVERY_WAIT);
        List<Invoice> invoices = dto.getInvoices();
        workRequest.setInvoices(invoices);
        this.invoiceRepository.saveAll(invoices);

        message.setStatus(StatusEnum.OK);
        return message;
    }
}
