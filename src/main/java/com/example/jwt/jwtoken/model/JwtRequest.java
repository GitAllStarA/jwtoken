package com.example.jwt.jwtoken.model;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    private String userName;
    private String password;


}
