package com.prophiuslimited.ProphiusLimitedAssessment.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Schema(
        description = "User Signup details"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiResponse
public class SignupRequestDto implements Serializable {

    @NotBlank(message = "Username is mandatory")
    @Schema(
            description = "Username of user"
    )
    private String username;

    @Email(message = "Enter a valid email",
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Schema(
            description = "Email address of user"
    )
    private String email;

    @Schema(
            description = "Password of user"
    )
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max=25, message="Password must be equal to or greater than 8 character and less than 30 characters")
    private String password;

}
