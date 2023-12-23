package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Name should not be blank.")
    @Size(min = 2, max = 25, message = "Name should be {min}-{max} characters long.")
    private String name;

    @NotBlank(message = "Username should not be blank.")
    @Size(min = 2, max = 20, message = "Username should be {min}-{max} characters long.")
    private String username;

    @NotBlank(message = "Password should not be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$",
            message = "Incorrect Format for password."
    )
    @Size(min = 8, max = 20, message = "Password should be {min}-{max} characters long.")
    private String password;

    @NotNull(message = "IPAddress is mandatory.")
    @Pattern(
            regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "Incorrect Format for IP Address."
    )
    @Size(max = 25, message = "Max length for IP Address is {max} characters.")
    private String ipAddress;

    @NotNull(message = "ReturnURL should not be null")
    @Pattern(
            regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?",
            message = "Incorrect Format for Return URL."
    )
    @Size(max = 100, message = "Max length for the return URL is {max} characters.")
    private String returnUrl;

    @NotNull(message = "Role is mandatory.")
    private UserRole role;

    @NotNull(message = "Status is mandatory.")
    private UserStatus status;

}
