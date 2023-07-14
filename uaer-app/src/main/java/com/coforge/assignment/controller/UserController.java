package com.coforge.assignment.controller;

import com.coforge.assignment.dto.LoginDto;
import com.coforge.assignment.dto.ResetPasswordDto;
import com.coforge.assignment.dto.UserDto;
import com.coforge.assignment.exception.ExceptionResponse;
import com.coforge.assignment.response.UserResponse;
import com.coforge.assignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// Registration, Login, Forget Password.
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register New User API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully.",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User with given email already exist.",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping(value = "/v1/register")
    public ResponseEntity<UserResponse> registration(@RequestBody UserDto userDto) {
        log.info("Request to register user {}", userDto);
        userService.saveUser(userDto);
        return new ResponseEntity<>(new UserResponse("User registered successfully!"), HttpStatus.CREATED);
    }

    @Operation(summary = "Login User API")
    @PostMapping(value = "/v1/login", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> login(@RequestBody LoginDto loginDto) {
        log.info("Request to login {}", loginDto);
        userService.login(loginDto);
        return new ResponseEntity<>(new UserResponse("User signed-in successfully!"), HttpStatus.OK);
    }

    @Operation(summary = "Reset Password API")
    @PostMapping(value = "/v1/reset/password", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        log.info("Request to reset password {}", resetPasswordDto);
        userService.resetPassword(resetPasswordDto);
        return new ResponseEntity<>(new UserResponse("Password reset successfully!"), HttpStatus.OK);
    }

    @Operation(summary = "Get all user API")
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Request to get all users.");
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

}
