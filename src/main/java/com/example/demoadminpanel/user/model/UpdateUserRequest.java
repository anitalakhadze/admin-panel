package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {

    @Size(min = 2, max = 25, message = "Name should be {min}-{max} characters long.")
    @NotBlank(message = "Name should not be blank.")
    private String name;

    @Size(max = 25, message = "Max length for IP Address is {max} characters.")
    @Pattern(
            regexp = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)",
            message = "Incorrect format for IP Address."
    )
    @NotBlank(message = "IPAddress should not be blank.")
    private String ipAddress;

    @Size(max = 100, message = "Max length for the return URL is {max} characters.")
    @Pattern(
            regexp = "(https?://)?([\\\\da-z.-]+)\\.([a-z.]{2,6})[/\\\\w .-]*/?",
            message = "Incorrect Format for Return URL."
    )
    @NotBlank(message = "ReturnURL should not be blank.")
    private String returnUrl;

    @NotNull(message = "Role is mandatory.")
    private UserRole role;

}
