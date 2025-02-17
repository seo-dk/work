package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    // 기본적인 CRUD 기능을 JpaRepository에서 제공하므로 추가적인 메서드는 필요 없음
}
