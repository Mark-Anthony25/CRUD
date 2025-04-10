package com.mycoolestapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email;


    @NotBlank(message = "Password is required")
    //@Size(min = 8, message = "Password 8 characters at least")
    private String password;

}
