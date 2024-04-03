package com.walletbackend.service;

import com.walletbackend.constants.Constant;
import com.walletbackend.constants.ErrorMessage;
import com.walletbackend.dto.AuthenticationResponse;
import com.walletbackend.dto.AuthenticationRequest;
import com.walletbackend.dto.UserDTO;
import com.walletbackend.entity.User;
import com.walletbackend.exception.AuthenticationException;
import com.walletbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService implements UserDetailsService {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, UserService userService, @Lazy AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Set getAuthorities(User user) {
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(Constant.ROLE + user.getUserRole().name()));
        return authorities;
    }

    public AuthenticationResponse createJwtToken(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUserName();
        String password = authenticationRequest.getUserPassword();
        authenticate(username, password);
        final UserDetails userDetails = loadUserByUsername(username);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(newGeneratedToken);
    }

    public AuthenticationResponse createUser(UserDTO userDTO) {
        User newUser = userService.createUser(userDTO);
        final UserDetails userDetails = new  org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(), getAuthorities(newUser));
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(newGeneratedToken);
    }

    private void authenticate(String userName, String userPassword) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new AuthenticationException(ErrorMessage.TOKEN_EXPIRED);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD);
        }
    }
}
