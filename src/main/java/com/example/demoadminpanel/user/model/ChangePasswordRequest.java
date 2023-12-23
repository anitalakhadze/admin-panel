package com.example.demoadminpanel.user.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "oldPassword-Old password should not be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$",
            message = "oldPassword-Incorrect Format for old password"
    )
    @Size(min = 8, max = 20, message = "oldPassword-Password should be between {min} and {max} characters long.")
    private String oldPassword;

    @NotBlank(message = "newPassword-New password should not be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$",
            message = "newPassword-Incorrect Format for new password."
    )
    @Size(min = 8, max = 20, message = "newPassword-Password should be between {min} and {max} characters long.")
    private String newPassword;

    @NotBlank(message = "newPasswordDub-Repeated password should not be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$",
            message = "newPasswordDub-Incorrect Format for the repeated password."
    )
    @Size(min = 8, max = 20, message = "newPasswordDub-Password should be between {min} {max} characters long.")
    private String newPasswordDub;

}
