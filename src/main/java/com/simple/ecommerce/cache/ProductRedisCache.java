package com.simple.ecommerce.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.ecommerce.dto.GetProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisCache {

    String KEY_SUMMARY = "product:summary:";
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    Optional<GetProductResponseDto> getSummary(Long id){
        String responseJson = stringRedisTemplate.opsForValue().get(KEY_SUMMARY + id);
        if(responseJson == null){
            return Optional.empty(); // its cache miss, data not present in cache
        }
        try {
            GetProductResponseDto getProductResponseDto = objectMapper.convertValue(responseJson, GetProductResponseDto.class);
            return Optional.of(getProductResponseDto);
        } catch (Exception e) {
            log.error("Error parsing product summary {}",e.getMessage());
            stringRedisTemplate.delete(KEY_SUMMARY + id);
            return Optional.empty();
        }
    }

    private void putSummary(Long id, GetProductResponseDto getProductResponseDto){
        try{
            stringRedisTemplate.opsForValue().set(KEY_SUMMARY + id,objectMapper.writeValueAsString(getProductResponseDto) );
        } catch (Exception e) {
           throw  new RuntimeException("Error parsing product summary" + e.getMessage());
        }
    }

}
