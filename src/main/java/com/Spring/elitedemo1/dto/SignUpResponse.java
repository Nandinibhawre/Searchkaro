package com.Spring.elitedemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {

    private String Luserid;
    private String name;
    private String email;
    private String password;
}
