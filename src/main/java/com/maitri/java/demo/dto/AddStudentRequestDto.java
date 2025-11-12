package com.maitri.java.demo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddStudentRequestDto {

    @NotBlank
    @Size(min = 2)
    private String name;
    @Email
    @NotBlank(message =  "Email can't be empty")
    private String email;
}
