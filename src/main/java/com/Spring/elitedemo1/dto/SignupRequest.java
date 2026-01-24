package com.Spring.elitedemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    @Id
    private String Luserid;
    private String name;
    private String email;
    private String password;
}