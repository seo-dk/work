package com.mobigen.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("Account Service 서킷 브레이커가 OPEN 상태입니다. 요청을 처리할 수 없습니다.");
    }

}