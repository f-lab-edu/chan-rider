package com.chan.rider.service;

import com.chan.rider.DatabaseTest;
import com.chan.rider.dao.RiderWaitingListDao;
import com.chan.rider.domain.Address;
import com.chan.rider.domain.Invoice;
import com.chan.rider.domain.Rider;
import com.chan.rider.dto.invoice.InvoiceItemDto;
import com.chan.rider.dto.invoice.MatchingRequestDto;
import com.chan.rider.dto.invoice.MatchingResponseDto;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.dto.rider.RiderInfoDto;
import com.chan.rider.dto.workRequest.RiderRequestDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RiderServiceTest extends DatabaseTest {

    @Autowired
    RiderService riderService;

    @Autowired
    RiderWaitingListDao riderWaitingListDao;

    @Test
    public void RiderSignUp(){

        Address address = new Address();
        address.setDoroAddress("천안시 서북구");
        address.setSigunguCode(123456789);

        RiderDto riderDto = new RiderDto();
        riderDto.setName("Woong");
        riderDto.setTelephone("010-1234-5678");
        riderDto.setAddress(address);
        riderDto.setAccountId("1234567890");

        Rider rider = riderService.signUp(riderDto);

        Assertions.assertEquals(riderDto.getName(),rider.getName());
        Assertions.assertEquals(riderDto.getTelephone(),rider.getTelephone());
        Assertions.assertEquals(riderDto.getAddress(),rider.getAddress());
        Assertions.assertEquals(riderDto.getAccountId(),rider.getAccountId());

    }

    @Test
    public void registerDelivery() throws JsonProcessingException {

        String localCode = "A01";
        LocalDate localDate = LocalDate.now();
        boolean isPm = true;

        Rider rider = makeRider();

        makeWorkRequest(rider, localDate, localCode, isPm);

        RiderInfoDto riderInfo = riderWaitingListDao.popRider(
                localCode,
                localDate,
                isPm);

        Assertions.assertEquals(rider.getAccountId(), riderInfo.getAccountId());
        Assertions.assertEquals(rider.getId(), riderInfo.getId());

    }

    @Test
    public void getRider() throws JsonProcessingException {

        String localCode = "A01";
        LocalDate now = LocalDate.now();
        boolean isPm = true;

        Rider rider = makeRider();

        makeWorkRequest(rider, now, localCode, isPm);

        RiderRequestDto riderRequestDto = new RiderRequestDto();
        riderRequestDto.setLocalCode(localCode);
        riderRequestDto.setPm(isPm);
        riderRequestDto.setDate(now);

        RiderInfoDto riderInfo = riderService.riderRequest(riderRequestDto);

        Assertions.assertEquals(rider.getAccountId(), riderInfo.getAccountId());
        Assertions.assertEquals(rider.getId(), riderInfo.getId());

    }

    @Test
    public void matchDeliver() throws JsonProcessingException {

        String localCode = "A01";
        LocalDate localDate = LocalDate.now();
        boolean isPm = true;

        //Rider 및 출근 요청 생성
        Rider rider = makeRider();
        makeWorkRequest(rider, localDate, localCode, isPm);

        //인보이스 데이터
        InvoiceItemDto invoiceItemDto = InvoiceItemDto.builder()
                .invoiceId(1L)
                .invoiceCode("220830123011987_qwertasdfg")
                .deliveryDate(localDate)
                .storeAddress(rider.getAddress())
                .storeLocalCode(localCode)
                .customerAddress(rider.getAddress())
                .customerLocalCode(localCode)
                .build();

        //라이더와 인보이스 매칭 서비스 요청
        MatchingRequestDto matchingRequestDto = new MatchingRequestDto();
        matchingRequestDto.setRiderId(rider.getId());
        matchingRequestDto.setDate(localDate);
        matchingRequestDto.setPM(isPm);
        List<InvoiceItemDto> invoiceItemDtoList = new ArrayList<>();
        invoiceItemDtoList.add(invoiceItemDto);
        matchingRequestDto.setInvoices(invoiceItemDtoList);

        List<Invoice> invoiceList = this.riderService.matchDelivery(matchingRequestDto);
        Invoice invoice = invoiceList.get(0);

        Assertions.assertEquals(invoiceItemDto.getInvoiceId(), invoice.getLogisticsInvoiceId());
        Assertions.assertEquals(invoiceItemDto.getInvoiceCode(), invoice.getInvoiceCode());

        RiderInfoDto riderInfo = riderWaitingListDao.popRider(
                localCode,
                localDate,
                isPm);

    }

    private void makeWorkRequest(Rider rider,LocalDate now, String localCode, boolean isPm) throws JsonProcessingException {

        WorkRequestRegisterDto workRequestRegisterDto = new WorkRequestRegisterDto();
        workRequestRegisterDto.setAccountId(rider.getAccountId());
        workRequestRegisterDto.setRiderId(rider.getId());
        workRequestRegisterDto.setDate(now);
        workRequestRegisterDto.setLocalCode(localCode);
        workRequestRegisterDto.setPm(isPm);
        workRequestRegisterDto.setCount(1);

        riderService.registerDelivery(workRequestRegisterDto);

    }


    private Rider makeRider(){

        Address address = new Address();
        address.setDoroAddress("천안시 서북구");
        address.setSigunguCode(123456789);

        RiderDto riderDto = new RiderDto();
        riderDto.setName("woong");
        riderDto.setTelephone("010-1234-5678");
        riderDto.setAddress(address);
        riderDto.setAccountId("1234567890");

        return riderService.signUp(riderDto);

    }
}