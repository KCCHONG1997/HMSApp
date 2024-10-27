package model;

import java.time.LocalDateTime;

public class Patient extends HMSPersonnel {

    private String bloodType;
    private String insuranceInfo;
    private String allergies;
    private LocalDateTime dateOfAdmission;

    // Constructor
    public Patient(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                   String passwordHash, LocalDateTime DoB, String gender, String role,
                   String bloodType, String insuranceInfo, String allergies, LocalDateTime dateOfAdmission) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);

        this.bloodType = bloodType;
        this.insuranceInfo = insuranceInfo;
        this.allergies = allergies;
        this.dateOfAdmission = dateOfAdmission;
    }

    // Getters and Setters
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public LocalDateTime getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDateTime dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
}
