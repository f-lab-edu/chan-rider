package com.chan.rider.dao;

import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.domain.WorkRequestStatusEnum;
import com.chan.rider.dto.rider.RiderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
@RequiredArgsConstructor
public class RiderWaitingListDao {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    public boolean pushRider(String centerCode, LocalDate date, boolean isPM, Rider rider) throws JsonProcessingException {

        //대기 리스트에 등록
        String key = makeKey(centerCode, date, isPM);
        RiderDto riderDto = new RiderDto(rider);
        String riderValue = objectMapper.writeValueAsString(riderDto);

        return  this.redisTemplate.opsForList().rightPush(key, riderValue) != null ? true : false;
    }

    public RiderDto popRider(String centerCode, LocalDate date, boolean isPM) throws JsonProcessingException {

        //대기 리스트에서 대기중인 Rider pop
        String key = makeKey(centerCode, date, isPM);
        Object riderValue = this.redisTemplate.opsForList().leftPop(key);
        if(riderValue != null){
            RiderDto riderInfo = objectMapper.readValue(riderValue.toString(), RiderDto.class);
            return riderInfo;
        }
        else{
            return null;
        }
    }

    private String makeKey(String centerCode, LocalDate date, boolean isPM){

        String meridiem = isPM ? "PM" : "AM";

        return centerCode + "_" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +  "_" + meridiem;

    }
}
