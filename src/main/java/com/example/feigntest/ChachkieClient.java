package com.example.feigntest;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "chachkie-client")
public interface ChachkieClient {
    @GetMapping("chachkies/{id}")
    @Headers("Content-Type: application/json")
    Chachkie findMyChachkie(@PathVariable("id") String id);
}
