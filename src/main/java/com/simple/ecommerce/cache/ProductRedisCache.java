package com.simple.ecommerce.cache;


import com.simple.ecommerce.dto.GetProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisCache {

    private final Duration cache_ttl = Duration.ofMinutes(1);

    String KEY_SUMMARY = "product:summary:";
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public Optional<GetProductResponseDto> getSummary(Long id){
        String responseJson = stringRedisTemplate.opsForValue().get(KEY_SUMMARY + id);
        if(responseJson == null){
            log.warn("cache miss, no data found in cache.");
            return Optional.empty(); // its cache miss, data not present in cache
        }
        try {
            log.info("This is cache hit for key {}", KEY_SUMMARY + id);
            GetProductResponseDto getProductResponseDto = objectMapper.readValue(responseJson, GetProductResponseDto.class);
            return Optional.of(getProductResponseDto);
        } catch (Exception e) {
            log.error("Error parsing product summary {}",e.getMessage());
            stringRedisTemplate.delete(KEY_SUMMARY + id);
            return Optional.empty();
        }
    }

    public void putSummary(Long id, GetProductResponseDto getProductResponseDto){
        try{
            stringRedisTemplate.opsForValue().set(KEY_SUMMARY + id,objectMapper.writeValueAsString(getProductResponseDto), cache_ttl );
            log.info("Key is {}",  KEY_SUMMARY + id);
        } catch (Exception e) {
           throw  new RuntimeException("Error parsing product summary" + e.getMessage());
        }
    }

}
