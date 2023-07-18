package com.coforge.dummy.dto;

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
