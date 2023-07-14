package com.coforge.assignment.service;

import com.coforge.assignment.dto.LoginDto;
import com.coforge.assignment.dto.ResetPasswordDto;
import com.coforge.assignment.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    void login(LoginDto loginDto);

    List<UserDto> findAllUsers();

    void resetPassword(ResetPasswordDto resetPasswordDto);
}
