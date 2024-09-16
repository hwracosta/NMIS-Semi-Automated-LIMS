package com.example.semiautomatedlims.Service;

import java.time.LocalDateTime;

public class PasswordResetData {

    private String resetCode;
    private LocalDateTime expirationTime;

    public PasswordResetData(String resetCode, LocalDateTime expirationTime) {
        this.resetCode = resetCode;
        this.expirationTime = expirationTime;
    }

    public String getResetCode() {
        return resetCode;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
