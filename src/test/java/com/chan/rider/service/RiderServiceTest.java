package com.chan.rider.service;

import com.chan.rider.DatabaseTest;
import com.chan.rider.dao.RiderWaitingListDao;
import com.chan.rider.domain.Address;
import com.chan.rider.domain.Rider;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.dto.workRequest.WorkRequestRegisterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

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
        riderDto.setName("woong");
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

        Address address = new Address();
        address.setDoroAddress("천안시 서북구");
        address.setSigunguCode(123456789);

        RiderDto riderDto = new RiderDto();
        riderDto.setName("woong");
        riderDto.setTelephone("010-1234-5678");
        riderDto.setAddress(address);
        riderDto.setAccountId("1234567890");

        Rider rider = riderService.signUp(riderDto);

        WorkRequestRegisterDto workRequestRegisterDto = new WorkRequestRegisterDto();

        workRequestRegisterDto.setAccountId(rider.getAccountId());
        workRequestRegisterDto.setRiderId(rider.getId());
        workRequestRegisterDto.setDate(LocalDate.now());
        workRequestRegisterDto.setCenterCode("A01");
        workRequestRegisterDto.setPm(true);
        workRequestRegisterDto.setCount(1);

        riderService.registerDelivery(workRequestRegisterDto);

        RiderDto riderInfo = riderWaitingListDao.popRider(
                workRequestRegisterDto.getCenterCode(),
                workRequestRegisterDto.getDate(),
                workRequestRegisterDto.isPm());


        Assertions.assertEquals(workRequestRegisterDto.getAccountId(), riderInfo.getAccountId());
        Assertions.assertEquals(workRequestRegisterDto.getRiderId(), riderInfo.getId());

    }

}