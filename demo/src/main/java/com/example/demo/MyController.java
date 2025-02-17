package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private MyEntityRepository repository;

    @GetMapping("/data")
    public List<MyEntity> getData() {
        return repository.findAll();  // MySQL에서 모든 데이터를 가져와 반환
    }
}

