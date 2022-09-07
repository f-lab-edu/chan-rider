package com.chan.rider.service;

import com.chan.rider.dao.RiderWaitingListDao;
import com.chan.rider.domain.*;
import com.chan.rider.dto.invoice.InvoiceItemDto;
import com.chan.rider.dto.invoice.MatchingRequestDto;
import com.chan.rider.dto.rider.RiderInfoDto;
import com.chan.rider.dto.workRequest.RiderRequestDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.exception.RiderFindFailException;
import com.chan.rider.repository.InvoiceRepository;
import com.chan.rider.repository.RiderRepository;
import com.chan.rider.repository.WorkRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RiderService {

    private final RiderRepository riderRepository;

    private final InvoiceRepository invoiceRepository;

    private final WorkRequestRepository workRequestRepository;

    private final RiderWaitingListDao riderWaitingListDao;

    @Transactional
    public Rider signUp(RiderDto dto) {
        Rider rider = new Rider();
        rider.setAccountId(dto.getAccountId());
        rider.setName(dto.getName());
        rider.setTelephone(dto.getTelephone());
        rider.setAddress(dto.getAddress());

        return this.riderRepository.save(rider);
    }

    @Transactional
    public WorkRequest registerDelivery(WorkRequestRegisterDto dto) throws JsonProcessingException {

        Rider rider = this.riderRepository.findById(dto.getRiderId());

        if (rider == null) {
            throw new RiderFindFailException("라이더 정보를 찾을 수 없습니다.");
        }

        WorkRequest workRequest = new WorkRequest();
        workRequest.setLocalCode(dto.getLocalCode());
        workRequest.setPM(dto.isPm());
        workRequest.setCount(dto.getCount());
        workRequest.setDate(dto.getDate());
        workRequest.setWorkRequestStatusEnum(WorkRequestStatusEnum.SUBMIT);

        rider.addWorkRequest(workRequest);

        this.workRequestRepository.save(workRequest);

        //대기 리스트에 등록
        RiderInfoDto riderInfo = new RiderInfoDto(rider);
        riderWaitingListDao.pushRider(dto.getLocalCode(), dto.getDate(), dto.isPm(), riderInfo);

        return workRequest;
    }

    @Transactional
    public RiderInfoDto riderRequest(RiderRequestDto dto) throws JsonProcessingException {

        //대기 리스트에서 대기중인 Rider pop
        RiderInfoDto riderInfo = riderWaitingListDao.popRider(dto.getLocalCode(), dto.getDate(), dto.isPm());
        if(riderInfo != null){
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
    public List<Invoice> matchDelivery(MatchingRequestDto dto) {
        WorkRequest workRequest = this.workRequestRepository.findByRiderIdAndDateAndIsPM(dto.getRiderId(), dto.getDate(), dto.isPM());

        List<Invoice> invoiceList = dto.getInvoices().stream().map(item ->{
            Invoice invoice = item.toEntity();
            invoice.setStatus(InvoiceStatusEnum.MATCHING);
            workRequest.addInvoice(invoice);
            return invoice;
        }).toList();

        return this.invoiceRepository.saveAll(invoiceList);
    }

}
