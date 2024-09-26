package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT_register")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "representative_name", nullable = false)
    private String representativeName;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(name = "address", nullable = false, length = 20)
    private String address;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "lto_no", length = 100)  // Optional field
    private String ltoNo;

    @Column(name = "client_classif", nullable = false, length = 100)
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

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
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
