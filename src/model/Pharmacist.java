package model;

import java.time.LocalDateTime;

public class Pharmacist extends HMSPersonnel {
    private String pharmacistLicenseNumber;
    private LocalDateTime dateOfEmployment;

    // Constructor
    public Pharmacist(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                      String passwordHash, LocalDateTime DoB, String gender, String role,
                      String pharmacistLicenseNumber, LocalDateTime dateOfEmployment) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.pharmacistLicenseNumber = pharmacistLicenseNumber;
        this.dateOfEmployment = dateOfEmployment;
    }

    // Getters and Setters
    public String getPharmacistLicenseNumber() {
        return pharmacistLicenseNumber;
    }

    public void setPharmacistLicenseNumber(String pharmacistLicenseNumber) {
        this.pharmacistLicenseNumber = pharmacistLicenseNumber;
    }

    public LocalDateTime getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDateTime dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}

