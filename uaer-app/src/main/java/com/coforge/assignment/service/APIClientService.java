package com.coforge.assignment.service;

import com.coforge.assignment.dto.LoginDto;
import com.coforge.assignment.entity.User;
import com.coforge.assignment.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Configuration
public class APIClientService {

    public static final String USERS_PROFILE = "http://localhost:8084/home/profile";
    public static final String USERS = "http://localhost:8084/home/users";

    @Autowired
    private UserRepository userRepository;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }


    @CircuitBreaker(name = "${spring.application.name}")
    public void redirectToHome(LoginDto loggedInUser) {
        log.info("External Service call to redirect user profile page.");
        String uuid = webClient()
                .post()
                .uri(USERS_PROFILE)
                .body(Mono.just(loggedInUser), LoginDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response from External Service {}", uuid);
    }

    @CircuitBreaker(name = "users", fallbackMethod = "defaultUsers")
    public List<User> getUsers() {
        log.info("External Service call to fetch users.");
        List<User> userList = Objects.requireNonNull(webClient().get()
                .uri(USERS).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {
                })
                .block()).stream().toList();
        log.info("Users from external service {}",userList);
        return userList;
    }

//    @Cacheable("fallbackMethod-users")
    public List<User> defaultUsers(Throwable throwable) {
        log.info("User list from fallback method.");
        return userRepository.findAll();
    }
}
