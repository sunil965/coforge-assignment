package com.coforge.dummy.controller;

import com.coforge.dummy.dto.LoginDto;
import com.coforge.dummy.entity.User;
import com.coforge.dummy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository repository;

    @PostMapping ("/profile")
    public ResponseEntity<String> profilePage(@RequestBody LoginDto loginDto) {
        log.info("Landing user with credentials {}, to profile after login.", loginDto);
        String loginUserUUID = UUID.randomUUID().toString();
        log.info("Returning UUID {} for user {}", loginUserUUID, loginDto.getUserEmail());
        return new ResponseEntity<>(loginUserUUID, HttpStatus.OK);

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> homePage() {
      log.info("Fetching User for client service.");
        List<User> allUsers = repository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);

    }
}
