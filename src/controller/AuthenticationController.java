package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import enums.PersonnelFileType;
import enums.RecordFileType;
import helper.Helper;
import model.*;
import repository.PersonnelRepository;

public class AuthenticationController {
    public static SessionCookie cookie = new SessionCookie(null, null);
    /**
     * Authenticates a user based on username, password, and role.
     * @param username the username of the personnel
     * @param password the password of the personnel
     * @param role the role of the personnel (admin, doctor, pharmacist, or patient)
     * @return the authenticated HMSPersonnel object if successful, otherwise null
     */
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
    /**
     * Verifies the password by comparing the entered password with the stored password hash.
     * @param personnel the personnel whose password is being verified
     * @param password the password entered by the user
     * @return true if the password matches the stored password hash, false otherwise
     */
    // Method to verify if the password matches the stored password hash
    private static boolean verifyPassword(HMSPersonnel personnel, String password) {
        return personnel.getPasswordHash().equals(password);
    }
    /**
     * Updates the password for a given personnel.
     * @param personnel the personnel whose password is to be updated
     * @param newPassword the new password to set
     * @return true if the password is updated successfully, false otherwise
     */
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
    /**
     * Registers a new patient.
     * @param fullName the full name of the patient
     * @param idCard the ID card of the patient
     * @param username the username for the patient
     * @param email the email address of the patient
     * @param phoneNo the phone number of the patient
     * @param passwordHash the hashed password for the patient
     * @param DoB the date of birth of the patient
     * @param gender the gender of the patient
     * @param insuranceInfo the insurance information of the patient
     * @param allergies the allergies of the patient
     * @param dateOfAdmission the date the patient was admitted
     * @return the UID of the newly registered patient
     */
    // Register Patient
    public static String registerPatient(String fullName, String idCard, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, String insuranceInfo, String allergies,
            LocalDateTime dateOfAdmission) {

        // Register patient
        Patient patient = new Patient(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender,
                insuranceInfo, allergies, dateOfAdmission);
        PersonnelRepository.PATIENTS.put(patient.getUID(), patient);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Patient registered successfully with username: " + username);
        return patient.getUID();
    }
    /**
     * Registers a new doctor.
     * @param fullName the full name of the doctor
     * @param idCard the ID card of the doctor
     * @param username the username for the doctor
     * @param email the email address of the doctor
     * @param phoneNo the phone number of the doctor
     * @param passwordHash the hashed password for the doctor
     * @param DoB the date of birth of the doctor
     * @param gender the gender of the doctor
     * @param specialty the specialty of the doctor
     * @param medicalLicenseNumber the medical license number of the doctor
     * @param dateJoin the date the doctor joined
     * @param yearsOfExperience the years of experience the doctor has
     * @return true if the doctor is registered successfully, false otherwise
     */
    // Register Doctor
    public static boolean registerDoctor(String fullName, String idCard, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, String specialty, String medicalLicenseNumber,
            LocalDateTime dateJoin, int yearsOfExperience) {

        // Register doctor
        Doctor doctor = new Doctor(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, specialty,
                medicalLicenseNumber, dateJoin, yearsOfExperience);
        PersonnelRepository.DOCTORS.put(doctor.getUID(), doctor);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Doctor registered successfully with username: " + username);
        return true;
    }
    /**
     * Registers a new pharmacist.
     * @param fullName the full name of the pharmacist
     * @param idCard the ID card of the pharmacist
     * @param username the username for the pharmacist
     * @param email the email address of the pharmacist
     * @param phoneNo the phone number of the pharmacist
     * @param passwordHash the hashed password for the pharmacist
     * @param DoB the date of birth of the pharmacist
     * @param gender the gender of the pharmacist
     * @param pharmacistLicenseNumber the license number of the pharmacist
     * @param dateOfEmployment the date the pharmacist was employed
     * @return true if the pharmacist is registered successfully, false otherwise
     */
    // Register Pharmacist
    public static boolean registerPharmacist(String fullName, String idCard, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, String pharmacistLicenseNumber,
            LocalDateTime dateOfEmployment) {

        // Register pharmacist
        Pharmacist pharmacist = new Pharmacist(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender,
                pharmacistLicenseNumber, dateOfEmployment);
        PersonnelRepository.PHARMACISTS.put(pharmacist.getUID(), pharmacist);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Pharmacist registered successfully with username: " + username);
        return true;
    }
    /**
     * Registers a new admin.
     * @param fullName the full name of the admin
     * @param idCard the ID card of the admin
     * @param username the username for the admin
     * @param email the email address of the admin
     * @param phoneNo the phone number of the admin
     * @param passwordHash the hashed password for the admin
     * @param DoB the date of birth of the admin
     * @param gender the gender of the admin
     * @param dateOfCreation the date the admin account was created
     * @return true if the admin is registered successfully, false otherwise
     */
    // Register Admin
    public static boolean registerAdmin(String fullName, String idCard, String username, String email,
            String phoneNo, String passwordHash, LocalDateTime DoB,
            String gender, LocalDateTime dateOfCreation) {

        // Register admin
        Admin admin = new Admin(fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, "Admins",
                dateOfCreation);
        PersonnelRepository.ADMINS.put(admin.getUID(), admin);
        PersonnelRepository.saveAllPersonnelFiles();
        System.out.println("Admin registered successfully with username: " + username);
        return true;
    }
    /**
     * Logs out the currently logged in personnel.
     * @param personnel the personnel to log out
     */
    // Optional: Implement a logout method if needed
    public static void logout(HMSPersonnel personnel) {
        System.out.println(personnel.getFullName() + " has been logged out.");
    }
    /**
     * Checks if a username is already taken in the given personnel map.
     * @param username the username to check
     * @param personnelMap the map of personnel to check the username against
     * @return true if the username is taken, false otherwise
     */
    public static boolean isUsernameTaken(String username, Map<String, ? extends HMSPersonnel> personnelMap) {
        return personnelMap.values().stream().anyMatch(personnel -> personnel.getUsername().equals(username));
    }

}
