DELETE FROM admin_panel.admin_panel_users;

INSERT INTO admin_panel.admin_panel_users (name, username, password, ip_address, return_url, role, status, registered_at)
VALUES ('admin', 'admin', '$2a$10$Kp8vTSIzOUEpppuP6.nM4O/VNcxKBWPtVx6GjZt4aswdDEDsUg/.q', '127.0.0.1', 'https://example.com', 'ROLE_ADMIN', 'ACTIVE', CURRENT_TIMESTAMP);