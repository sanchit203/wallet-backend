package com.walletbackend.service;

import com.walletbackend.constants.Constant;
import com.walletbackend.constants.ErrorMessage;
import com.walletbackend.constants.UniqueConstraintName;
import com.walletbackend.dto.UpdateWithdrawlBalanceDTO;
import com.walletbackend.dto.UserBankDetailResponseDTO;
import com.walletbackend.dto.UserBankDetailsRequestDTO;
import com.walletbackend.dto.UserDTO;
import com.walletbackend.dto.UserProfileResponseDTO;
import com.walletbackend.entity.User;
import com.walletbackend.enums.UserRole;
import com.walletbackend.exception.InvalidInputDataException;
import com.walletbackend.exception.UniqueKeyViolationException;
import com.walletbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public UserProfileResponseDTO getUserProfileResponseDTO() {
        User loggedInUser = getLoggedInUser();
        return UserProfileResponseDTO
                .builder()
                .email(loggedInUser.getEmail())
                .name(loggedInUser.getName())
                .phoneNumber(loggedInUser.getPhoneNumber())
                .username(loggedInUser.getUsername())
                .build();
    }

    public UserBankDetailResponseDTO getUserBankDetailResponseDTO() {
        User loggedInUser = getLoggedInUser();
        return UserBankDetailResponseDTO
                .builder()
                .bankAccountNumber(loggedInUser.getBankAccountNumber())
                .nameOnBankAccount(loggedInUser.getNameOnBankAccount())
                .ifscCode(loggedInUser.getIfscCode())
                .build();
    }

    public UserBankDetailResponseDTO updateUserBankDetails(UserBankDetailsRequestDTO userBankDetailsRequestDTO) {
        User loggedInUser = getLoggedInUser();

        if(userBankDetailsRequestDTO.getNameOnBankAccount().isEmpty()) {
            throw new InvalidInputDataException(ErrorMessage.NAME_CANNOT_BE_EMPTY);
        }

        if(userBankDetailsRequestDTO.getBankAccountNumber().isEmpty()) {
            throw new InvalidInputDataException(ErrorMessage.ACCOUNT_NUMBER_CANNOT_BE_EMPTY);
        }

        for (int i = 0; i < userBankDetailsRequestDTO.getBankAccountNumber().length(); i++) {
            if (!Character.isDigit(userBankDetailsRequestDTO.getBankAccountNumber().charAt(i))) {
                throw new InvalidInputDataException(ErrorMessage.INVALID_ACCOUNT_NUMBER);
            }
        }

        if(userBankDetailsRequestDTO.getIfscCode().isEmpty()) {
            throw new InvalidInputDataException(ErrorMessage.IFSC_CANNOT_BE_EMPTY);
        }

        loggedInUser.setBankAccountNumber(userBankDetailsRequestDTO.getBankAccountNumber());
        loggedInUser.setIfscCode(userBankDetailsRequestDTO.getIfscCode());
        loggedInUser.setNameOnBankAccount(userBankDetailsRequestDTO.getNameOnBankAccount());

        userRepository.save(loggedInUser);

        return UserBankDetailResponseDTO
                .builder()
                .bankAccountNumber(loggedInUser.getBankAccountNumber())
                .nameOnBankAccount(loggedInUser.getNameOnBankAccount())
                .ifscCode(loggedInUser.getIfscCode())
                .build();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void updateWithDrawlBalance(UpdateWithdrawlBalanceDTO updateWithdrawlBalanceDTO) {
        User user = findById(updateWithdrawlBalanceDTO.id);

        user.setWithdrawableAmount(updateWithdrawlBalanceDTO.getAmount());

        userRepository.save(user);
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new InvalidInputDataException(ErrorMessage.USER_NOT_FOUND);
        }

        return user;
    }
}
