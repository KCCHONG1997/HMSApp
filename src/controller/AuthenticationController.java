package controller;

import java.time.LocalDateTime;
import java.util.Map;
import model.*;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AuthenticationController {
    public static SessionCookie cookie = new SessionCookie(null, null);

    // Method to authenticate a user based on username and password
    public static HMSPersonnel login(String username, String password, PersonnelFileType role) {
        Map<String, ? extends HMSPersonnel> personnelMap = null;

        switch (role.toString().toLowerCase()) {
            case "admins":
                personnelMap = PersonnelRepository.ADMINS;
                break;
            case "doctors":
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case "pharmacists":
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            case "patients":
                personnelMap = PersonnelRepository.PATIENTS;
                break;
            default:
                System.out.println("Login failed: Invalid role provided.");
                return null;
        }

        for (HMSPersonnel personnel : personnelMap.values()) {
            if (personnel.getUsername().equals(username) && verifyPassword(personnel, password)) {
                System.out.println(role + " " + personnel.getFullName() + " logged in successfully.");
                cookie.setRole(PersonnelFileType.toEnum(personnel.getRole()));
                cookie.setUid(personnel.getUID());
                return personnel;
            }
        }

        System.out.println("Login failed: Invalid username or password.");
        return null;
    }

    // Method to verify if the password matches the stored password hash
    private static boolean verifyPassword(HMSPersonnel personnel, String password) {
        return personnel.getPasswordHash().equals(password);
    }

    // Method to update the password for a given personnel
    public static boolean updatePassword(HMSPersonnel personnel, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("Password update failed: New password cannot be empty.");
            return false;
        }

        personnel.setPasswordHash(newPassword);

        Map<String, ? extends HMSPersonnel> personnelMap = null;
        String uid = personnel.getUID();

        switch (PersonnelFileType.toEnum(personnel.getRole())) {
            case ADMINS:
                personnelMap = PersonnelRepository.ADMINS;
                break;
            case DOCTORS:
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            case PATIENTS:
                personnelMap = PersonnelRepository.PATIENTS;
                break;
            default:
                System.out.println("Password update failed: Invalid role provided.");
                return false;
        }

        if (personnelMap != null && personnelMap.containsKey(uid)) {
            ((Map<String, HMSPersonnel>) personnelMap).put(uid, personnel);
            PersonnelRepository.saveAllPersonnelFiles();
            System.out.println("Password updated successfully for " + personnel.getFullName());
            return true;
        } else {
            System.out.println("Password update failed: Personnel not found in repository.");
            return false;
        }
    }

    // Register Patient
    public static boolean registerPatient(String fullName, String idCard, String username, String email, 
                                          String phoneNo, String passwordHash, LocalDateTime DoB, 
                                          String gender, String insuranceInfo, String allergies, 
                                          LocalDateTime dateOfAdmission) {
        if (PersonnelRepository.PATIENTS.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }
        Patient patient = new Patient(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, insuranceInfo, allergies, dateOfAdmission);
        PersonnelRepository.PATIENTS.put(username, patient);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Patient registered successfully.");
        return true;
    }

    // Register Doctor
    public static boolean registerDoctor(String fullName, String idCard, String username, String email, 
                                         String phoneNo, String passwordHash, LocalDateTime DoB, 
                                         String gender, String specialty, String medicalLicenseNumber, 
                                         LocalDateTime dateJoin, int yearsOfExperience) {
        if (PersonnelRepository.DOCTORS.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }
        Doctor doctor = new Doctor(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, specialty, medicalLicenseNumber, dateJoin, yearsOfExperience);
        PersonnelRepository.DOCTORS.put(username, doctor);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Doctor registered successfully.");
        return true;
    }

    // Register Pharmacist
    public static boolean registerPharmacist(String fullName, String idCard, String username, String email, 
                                             String phoneNo, String passwordHash, LocalDateTime DoB, 
                                             String gender, String pharmacistLicenseNumber, 
                                             LocalDateTime dateOfEmployment) {
        if (PersonnelRepository.PHARMACISTS.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }
        Pharmacist pharmacist = new Pharmacist(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, pharmacistLicenseNumber, dateOfEmployment);
        PersonnelRepository.PHARMACISTS.put(username, pharmacist);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Pharmacist registered successfully.");
        return true;
    }

    // Register Admin
    public static boolean registerAdmin(String fullName, String idCard, String username, String email, 
                                        String phoneNo, String passwordHash, LocalDateTime DoB, 
                                        String gender, LocalDateTime dateOfCreation) {
        if (PersonnelRepository.ADMINS.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }
        Admin admin = new Admin(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "Admins", dateOfCreation);
        PersonnelRepository.ADMINS.put(username, admin);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Admin registered successfully.");
        return true;
    }

    // Optional: Implement a logout method if needed
    public static void logout(HMSPersonnel personnel) {
        System.out.println(personnel.getFullName() + " has been logged out.");
    }
}
