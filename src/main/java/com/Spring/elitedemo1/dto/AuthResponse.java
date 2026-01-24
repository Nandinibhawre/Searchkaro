package com.Spring.elitedemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse
{
    private  String userid;
    private String token;
    private String name;
    private String email;
}
