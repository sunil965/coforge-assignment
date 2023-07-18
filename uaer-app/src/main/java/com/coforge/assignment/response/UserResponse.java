package com.coforge.assignment.response;

import com.coforge.assignment.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    public int code;
    public String message;
    public List<UserDto> usersList;
}
