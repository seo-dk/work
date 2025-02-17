package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//import jakarta.persistence.Table;


@Entity
public class MyEntity {

    @Id
    private Long id;
    private String name;

    // 기본 생성자
    public MyEntity() {
        
    }

    // 파라미터가 있는 생성자
    public MyEntity(String name) {
        this.name = name;
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyEntity{id=" + id + ", name='" + name + "'}";
    }
}
