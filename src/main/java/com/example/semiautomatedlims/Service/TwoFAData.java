package com.example.semiautomatedlims.Service;

import java.time.LocalDateTime;

public class TwoFAData {
    private String code;
    private LocalDateTime expirationTime;

    public TwoFAData(String code, LocalDateTime expirationTime) {
        this.code = code;
        this.expirationTime = expirationTime;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
