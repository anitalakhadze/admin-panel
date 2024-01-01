package com.example.demoadminpanel.user.entity;

import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="admin_panel_users", schema = "admin_panel")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "return_url")
    private String returnUrl;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

}
