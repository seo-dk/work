package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	    // MySQL 연결을 확인하는 커맨드라인 실행 코드 (테스트용)
    @Bean
    public CommandLineRunner run(MyEntityRepository repository) {
        return args -> {
            // 데이터베이스 연결 테스트 (MySQL에 데이터를 저장하거나 조회해볼 수 있음)
            System.out.println("MySQL 데이터베이스에 연결되었습니다!");
            // 예시로 데이터를 저장
            MyEntity entity = new MyEntity("team8-edu-Data");
            entity.setId(System.currentTimeMillis());
            repository.save(entity);
            System.out.println("저장된 데이터: " + entity);
        };
    }

}
