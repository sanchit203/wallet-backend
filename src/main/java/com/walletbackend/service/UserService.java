package com.walletbackend.service;

import com.walletbackend.constants.Constant;
import com.walletbackend.constants.ErrorMessage;
import com.walletbackend.constants.UniqueConstraintName;
import com.walletbackend.dto.UserDTO;
import com.walletbackend.entity.User;
import com.walletbackend.enums.UserRole;
import com.walletbackend.exception.UniqueKeyViolationException;
import com.walletbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User newUser = User
                .builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(getEncodedPassword(userDTO.getPassword()))
                .withdrawableAmount(Constant.ZERO_BALANCE)
                .bankAccountNumber(Constant.EMPTY_STRING)
                .nameOnBankAccount(Constant.EMPTY_STRING)
                .ifscCode(Constant.EMPTY_STRING)
                .userRole(UserRole.USER)
                .build();

        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException exception){
            if(exception.getMessage().contains(UniqueConstraintName.USERNAME_USER_UX)){
                throw new UniqueKeyViolationException(ErrorMessage.USERNAME_ALREADY_EXISTS);
            }
            throw new UniqueKeyViolationException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        }

        return newUser;
    }

    public String getEncodedPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public User getLoggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return findByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
