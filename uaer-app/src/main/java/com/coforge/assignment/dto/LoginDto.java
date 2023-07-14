package com.coforge.assignment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String userEmail;
    private String password;
}
