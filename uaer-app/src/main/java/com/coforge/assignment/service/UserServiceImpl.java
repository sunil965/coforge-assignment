package com.coforge.assignment.service;

import com.coforge.assignment.dto.LoginDto;
import com.coforge.assignment.dto.ResetPasswordDto;
import com.coforge.assignment.dto.UserDto;
import com.coforge.assignment.entity.User;
import com.coforge.assignment.exception.ExistingEmailException;
import com.coforge.assignment.exception.PasswordMismatchException;
import com.coforge.assignment.exception.ResourceNotFoundException;
import com.coforge.assignment.kafka.KafKaProducerService;
import com.coforge.assignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafKaProducerService producerService;

    @Autowired
    private APIClientService apiClientService;

//    @Autowired
//    private RoleRepository roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(UserDto userDto) {
        log.info("Check if user with given email already exist: {}", userDto.getEmail());

        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent())
            throw new ExistingEmailException("User", userDto.getEmail());

        User savedUser = userRepository.save(getUserEntity(userDto));
        producerService.sendTextMessage("User registered successfully!");
        producerService.sendUserLogMessage(savedUser);
    }

    private static User getUserEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        // encrypt the password using spring security
      /*
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null)
            role = checkRoleExist();
        user.setRoles(Arrays.asList(role));*/
        return user;
    }
/*

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
*/

    @Override
    public void login(LoginDto loginDto) {
        log.info("Checking database if user with login credentials: {}", loginDto);
        User existingUser = userRepository.findByEmail(loginDto.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email id", loginDto.getUserEmail()));

        log.info("Comparing password");
        if (!existingUser.getPassword().equals(loginDto.getPassword()))
            throw new PasswordMismatchException("password");

        LoginDto loggedInUser = new LoginDto(existingUser.getEmail(), existingUser.getPassword());
        apiClientService.redirectToHome(loggedInUser);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        log.info("Check database if user exists : {}", resetPasswordDto.getEmail());
        User userByEmail = userRepository.findByEmail(resetPasswordDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email id", resetPasswordDto.getEmail()));

        log.info("Comparing old password.");
        if (!userByEmail.getPassword().equals(resetPasswordDto.getOldPassword()))
            throw new PasswordMismatchException("old password");

        userByEmail.setPassword(resetPasswordDto.getNewPassword());
        userRepository.save(userByEmail);
    }

    @Override
    @Cacheable(value = "users")
    public List<UserDto> findAllUsers() {
        log.info("Fetch all user list from client/database.");
//        List<User> users = userRepository.findAll();

        List<User> users = apiClientService.getUsers();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
