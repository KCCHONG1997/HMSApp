package model;

import java.time.LocalDateTime;

public class Admin extends HMSPersonnel {
    private LocalDateTime dateOfCreation;

    // Constructor
    public Admin(String UID, String fullName, String idCard, String username, String email, String phoneNo,
                 String passwordHash, LocalDateTime DoB, String gender, String role, LocalDateTime dateOfCreation) {
        super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
        this.dateOfCreation = dateOfCreation;
    }

    // Getter and Setter for dateOfCreation
    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}

