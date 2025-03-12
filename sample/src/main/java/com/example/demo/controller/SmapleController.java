package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SmapleController {

    private final RestTemplate restTemplate;
 
    public SmapleController(RestTemplate restTemplate, Tracer tracer) {
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/hello")
    public String  getData() {
        return "Hello, I am samsple!";
    }

    @GetMapping("/call")
    public String callDummy() {
        String response = restTemplate.getForObject("http://192.168.100.221:30800/api/hello", String.class);
        return "Called Dummy: " + response;
    }
}




