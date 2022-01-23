package com.example.jwt.jwtoken.controller;

import com.example.jwt.jwtoken.model.JwtRequest;
import com.example.jwt.jwtoken.model.JwtResponse;
import com.example.jwt.jwtoken.service.UserService;
import com.example.jwt.jwtoken.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "hello...";
    }


    @PostMapping("/auth")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        // performing the Authentication
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUserName(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
                throw  new Exception("Invalid Credentails",ex);
        }

        // creating JSON Web Token

        // user detail object creation
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());

        // creating token, it takes user details as input parameter
        final String token = jwtUtil.generateToken(userDetails);

        // sending jwt response by sending token
        return new JwtResponse(token);

    }
}
