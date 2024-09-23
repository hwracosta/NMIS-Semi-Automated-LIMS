package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT_register")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String representativeName;

    @Column(nullable = false, length = 20)
    private String contactNumber;

    @Column(nullable = false)
    private String companyName;

    @Column(length = 100)  // Optional field
    private String ltoNo;

    @Column(nullable = false, length = 100)
    private String clientClassif;

    @Column
    private String resetToken;

    @Column
    private LocalDateTime tokenExpiry;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLtoNo() {
        return ltoNo;
    }

    public void setLtoNo(String ltoNo) {
        this.ltoNo = ltoNo;
    }

    public String getClientClassif() {
        return clientClassif;
    }

    public void setClientClassif(String clientClassif) {
        this.clientClassif = clientClassif;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }
}
