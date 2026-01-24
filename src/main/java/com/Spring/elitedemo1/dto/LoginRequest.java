package com.Spring.elitedemo1.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class  LoginRequest
{

    private  String Luserid;
    private String email;
    private String password;
}
