package com.msa.edu.controller;

import com.msa.edu.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "사용자 관리 API")
public class UserController {
    private final List<User> userList = new ArrayList<>();

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId((long) (userList.size() + 1));
        userList.add(user);
        return user;
    }

    @Operation(summary = "사용자 목록 조회", description = "모든 사용자 정보를 조회합니다.")
    @GetMapping
    public List<User> getUsers() {
        return userList;
    }

    @Operation(summary = "사용자 조회", description = "ID를 기준으로 사용자 정보를 조회합니다.")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Operation(summary = "사용자 삭제", description = "ID를 기반으로 특정 사용자를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "사용자 없음")
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userToDelete = userList.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst();

        if (userToDelete.isPresent()) {
            userList.remove(userToDelete.get());
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
