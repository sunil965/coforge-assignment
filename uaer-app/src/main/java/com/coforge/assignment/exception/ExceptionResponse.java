package com.coforge.assignment.exception;

import com.coforge.assignment.response.UserResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse extends UserResponse {
    private LocalDateTime timestamp;
    private String path;
}
