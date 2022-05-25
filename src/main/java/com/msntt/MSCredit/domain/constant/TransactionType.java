package com.msntt.MSCredit.domain.constant;

public enum TransactionType {
    CREDIT_PAYMENT("01"),
    CREDIT_CARD_PAYMENT("02"),
    CREDIT_CARD_CONSUMPTION("03"),
    CREDIT_CONSUMPTION("04");
    public final String type;

    TransactionType(String type) {
        this.type = type;
    }
}
