package com.example.demoadminpanel.transaction.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    SUCCESS("Success"),
    FAILED("Failed"),
    PENDING("Pending");

    private final String value;
}
