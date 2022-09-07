package com.chan.rider.dao;

import com.chan.rider.domain.Rider;
import com.chan.rider.domain.WorkRequest;
import com.chan.rider.domain.WorkRequestStatusEnum;
import com.chan.rider.dto.rider.RiderDto;
import com.chan.rider.dto.rider.RiderInfoDto;
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

    public boolean pushRider(String localCode, LocalDate date, boolean isPM, RiderInfoDto riderInfo) throws JsonProcessingException {

        //대기 리스트에 등록
        String key = makeKey(localCode, date, isPM);
        String riderValue = objectMapper.writeValueAsString(riderInfo);

        return  this.redisTemplate.opsForList().rightPush(key, riderValue) != null ? true : false;
    }

    public RiderInfoDto popRider(String localCode, LocalDate date, boolean isPM) throws JsonProcessingException {

        //대기 리스트에서 대기중인 Rider pop
        String key = makeKey(localCode, date, isPM);
        Object riderValue = this.redisTemplate.opsForList().leftPop(key);
        if(riderValue != null){
            RiderInfoDto riderInfo = objectMapper.readValue(riderValue.toString(), RiderInfoDto.class);
            return riderInfo;
        }
        else{
            return null;
        }
    }

    private String makeKey(String localCode, LocalDate date, boolean isPM){

        String meridiem = isPM ? "PM" : "AM";

        return localCode + "_" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +  "_" + meridiem;

    }
}
