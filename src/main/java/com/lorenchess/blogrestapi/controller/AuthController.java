package com.lorenchess.blogrestapi.controller;

import com.lorenchess.blogrestapi.entities.Role;
import com.lorenchess.blogrestapi.entities.UserEntity;
import com.lorenchess.blogrestapi.payloadDTO.LoginDto;
import com.lorenchess.blogrestapi.payloadDTO.SignUpDto;
import com.lorenchess.blogrestapi.repository.RoleRepo;
import com.lorenchess.blogrestapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        //check if the user exists in db
        if (userRepo.existsUserEntityByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        //check if email exists in db
        if (userRepo.existsUserEntityByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        //create user object
        UserEntity user = new UserEntity();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }



}
