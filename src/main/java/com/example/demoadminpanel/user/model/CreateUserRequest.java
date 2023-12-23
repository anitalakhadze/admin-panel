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

    @NotBlank(message = "name-Name should not be blank.")
    @Size(min = 2, max = 25, message = "name-Name should be between {min} {max} characters long.")
    private String name;

    @NotBlank(message = "username-Username should not be blank.")
    @Size(min = 2, max = 20, message = "username-Username should be between {min} and {max} characters long.")
    private String username;

    @NotBlank(message = "password-Password should not be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$",
            message = "password-Incorrect Format for password."
    )
    @Size(min = 8, max = 20, message = "password-Password should be between {min}{max} characters long.")
    private String password;

    @NotBlank(message = "ipAddress-IPAddress should not be blank.")
    @Pattern(
            regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "ipAddress-Incorrect Format for IP Address."
    )
    @Size(max = 25, message = "ipAddress-Max length for IP Address is {max} characters.")
    private String ipAddress;

    @NotBlank(message = "returnUrl-ReturnURL should not be blank.")
    @Pattern(
            regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?",
            message = "returnUrl-Incorrect Format for Return URL."
    )
    @Size(max = 100, message = "returnUrl-Max length for the return URL is {max} characters.")
    private String returnUrl;

    @NotNull(message = "role-Role is mandatory.")
    private UserRole role;

    @NotNull(message = "status-Status is mandatory.")
    private UserStatus status;

}
