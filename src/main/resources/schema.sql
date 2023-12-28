CREATE SCHEMA IF NOT EXISTS admin_panel;

CREATE TABLE IF NOT EXISTS admin_panel.admin_panel_users (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255),
     username VARCHAR(255) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     ip_address VARCHAR(255),
     return_url VARCHAR(255),
     role VARCHAR(50) NOT NULL,
     status VARCHAR(50) NOT NULL,
     registered_at TIMESTAMP,
     CONSTRAINT chk_user_role CHECK (role IN ('ADMIN', 'USER')),
     CONSTRAINT chk_user_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);