package com.example.hellowebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @GetMapping("/sample/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello World with Spring Webflux");
    }

}
