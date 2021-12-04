package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {
    @Size(min = 2, max = 25, message = "Name should be more than 2 and less than 25 characters")
    @NotNull(message = "Name should not be null")
    private String name;

    @Size(max = 25, message = "IPAddress should be less than 20 characters")
    @Pattern(regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "Incorrect Format")
    @NotNull(message = "IPAddress should not be null")
    private String ipAddress;

    @Size(max = 100, message = "ReturnURL should be less than 20 characters")
    @Pattern(regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?", message = "Incorrect Format")
    @NotNull(message = "ReturnURL should not be null")
    private String returnUrl;

    @NotNull(message = "Role should not be null")
    private UserRole role;
}
