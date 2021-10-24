package com.example.demoadminpanel.email;

public interface EmailSender {
    void send(String to, String subject, String email);
}
