package com.example.demoadminpanel.transaction.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CARD("Card"),
    SAVED_CARD("Saved Card"),
    WALLET("Wallet"),
    PIGGY_BANK("Piggy Bank"),
    NO_METHOD_FOUND("No method found");

    private final String value;
}
