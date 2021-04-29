package com.venom.backend.service;

import com.venom.backend.intfac.TestGadget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service(value="RateLimiting")
public class RateLimiting implements TestGadget {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public RateLimiting(){

    }

    @Override
    public String move() {
        return "hello, venom";
    }
}
