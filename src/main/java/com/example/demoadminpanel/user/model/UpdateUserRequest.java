package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {

    @Size(min = 2, max = 25, message = "name-Name should be between {min} {max} characters long.")
    @NotBlank(message = "name-Name should not be blank.")
    private String name;

    @Size(max = 25, message = "ipAddress-Max length for IP Address is {max} characters.")
    @Pattern(
            regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "ipAddress-Incorrect format for IP Address."
    )
    @NotBlank(message = "ipAddress-IPAddress should not be blank.")
    private String ipAddress;

    @Size(max = 100, message = "returnUrl-Max length for the return URL is {max} characters.")
    @Pattern(
            regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?",
            message = "returnUrl-Incorrect Format for Return URL."
    )
    @NotBlank(message = "returnUrl-ReturnURL should not be blank.")
    private String returnUrl;

    @NotNull(message = "role-Role is mandatory.")
    private UserRole role;

}
