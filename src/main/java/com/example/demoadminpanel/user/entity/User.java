package com.example.demoadminpanel.user.entity;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String ipAddress;
    private String returnUrl;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean isActive;
}
