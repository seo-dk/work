package com.example.demo;

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
public class MyController {

    @Autowired
    private MyEntityRepository repository;
    private final RestTemplate restTemplate;
 
     public MyController(RestTemplate restTemplate) {
         this.restTemplate = restTemplate;
    }

    @GetMapping("/fetch")
    public List<MyEntity> getData() {
        return repository.findAll();  // MySQL에서 모든 데이터를 가져와 반환
    }

    @PostMapping("/add")
    public MyEntity addData() {
        // 임의의 MyEntity 객체 생성
        MyEntity entity = new MyEntity("team8-edu-Data");
        entity.setId(System.currentTimeMillis());  // ID를 현재 시간으로 설정
        return repository.save(entity);  // 데이터 저장 후 반환
    }

    @DeleteMapping("/delete")
    public String deleteAllData() {
        repository.deleteAll();  // 모든 데이터 삭제
        return "모든 데이터가 삭제되었습니다.";
    }

    @GetMapping("/call")
    public String callDummy() {
        String response = restTemplate.getForObject("http://192.168.100.221:30805/api/call", String.class);
        return "Called Demo -> " + response;
    }

    @GetMapping("/hello")
    public String  helloData() {
        return "Hello, I am Demo!";
    }
}

