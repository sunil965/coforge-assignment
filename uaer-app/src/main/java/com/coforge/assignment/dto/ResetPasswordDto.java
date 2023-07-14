package com.coforge.assignment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
