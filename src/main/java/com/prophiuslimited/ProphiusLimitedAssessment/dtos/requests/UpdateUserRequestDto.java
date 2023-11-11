package com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Enter a valid email")
    private String email;

}
