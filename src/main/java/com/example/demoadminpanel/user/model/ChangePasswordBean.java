package com.example.demoadminpanel.user.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordBean {
    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$", message = "Incorrect Format")
    @Size(min = 8, max = 20, message = "Password should be more than 8 and less than 20 characters")
    private String oldPassword;

    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$", message = "Incorrect Format")
    @Size(min = 8, max = 20, message = "Password should be more than 8 and less than 20 characters")
    private String newPassword;

    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$", message = "Incorrect Format")
    @Size(min = 8, max = 20, message = "Password should be more than 8 and less than 20 characters")
    private String newPasswordDub;
}
