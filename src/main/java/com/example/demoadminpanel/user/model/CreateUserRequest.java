package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {
    @NotNull(message = "Name should not be null")
    @Size(min = 2, max = 25, message = "Name should be more than 2 and less than 25 characters")
    private String name;

    @NotNull(message = "Username should not be null")
    @Size(min = 2, max = 20, message = "Username should be more than 2 and less than 20 characters")
    private String username;

    @NotNull(message = "Password should not be null")
    @Pattern(regexp = "^[A-Za-z0-9\\s!@#$%^&*()_+=-`~\\\\\\]\\[{}|';:/.,?><]*$", message = "Incorrect Format")
    @Size(min = 8, max = 20, message = "Password should be more than 8 and less than 20 characters")
    private String password;

    @NotNull(message = "IPAddress should not be null")
    @Pattern(regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "Incorrect Format")
    @Size(max = 25, message = "IPAddress should be less than 20 characters")
    private String ipAddress;

    @NotNull(message = "ReturnURL should not be null")
    @Pattern(regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?", message = "Incorrect Format")
    @Size(max = 100, message = "ReturnURL should be less than 20 characters")
    private String returnUrl;

    @NotNull(message = "Role should not be null")
    private UserRole role;

    @NotNull(message = "Status should not be null")
    private UserStatus status;
}
