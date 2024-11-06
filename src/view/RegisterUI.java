package view;

import helper.Helper;
import controller.AuthenticationController;
import java.time.LocalDateTime;

public class RegisterUI extends MainUI {
	
  public static void main (String[]args) {
  // Creating and starting AdminUI with the dummy admin
	  RegisterUI testing = new RegisterUI();
	  testing.start();
}

    @Override
    protected void printChoice() {
        printBreadCrumbs("HMS App UI > Register Page");
        System.out.println("You would like to register as:");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Pharmacist");
        System.out.println("4. Administrator");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void start() {
        while (true) {
            printChoice();
            int role = Helper.readInt("",1, 5);

            switch (role) {
                case 1 -> patientRegister();
                case 2 -> doctorRegister();
                case 3 -> pharmacistRegister();
                case 4 -> adminRegister();
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Registration for Patient
    public void patientRegister() {
        String fullName = Helper.readString("Enter full name:");
        String idCard = Helper.readString("Enter ID card number:");
        String username = Helper.readString("Enter desired username:");
        String email = Helper.readString("Enter email:");
        String phoneNo = Helper.readString("Enter phone number:");
        String password = Helper.readString("Enter desired password:");
        LocalDateTime DoB = Helper.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Helper.readString("Enter gender:");
        String insuranceInfo = Helper.readString("Enter insurance information:");
        String allergies = Helper.readString("Enter allergies (if any):");
        LocalDateTime dateOfAdmission = Helper.readDate("Enter date of admission (yyyy-MM-dd):");

        boolean success = AuthenticationController.registerPatient(fullName, idCard, username, email, phoneNo, password, DoB, gender, insuranceInfo, allergies, dateOfAdmission);
        System.out.println(success ? "Patient registered successfully!" : "Registration failed. Username may already exist.");
    }

    // Registration for Doctor
    public void doctorRegister() {
        String fullName = Helper.readString("Enter full name:");
        String idCard = Helper.readString("Enter ID card number:");
        String username = Helper.readString("Enter desired username:");
        String email = Helper.readString("Enter email:");
        String phoneNo = Helper.readString("Enter phone number:");
        String password = Helper.readString("Enter desired password:");
        LocalDateTime DoB = Helper.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Helper.readString("Enter gender:");
        String specialty = Helper.readString("Enter specialty:");
        String medicalLicenseNumber = Helper.readString("Enter medical license number:");
        LocalDateTime dateJoin = Helper.readDate("Enter date of joining (yyyy-MM-dd):");
        int yearsOfExperience = Helper.readInt("Enter years of experience:", 1, 50);

        boolean success = AuthenticationController.registerDoctor(fullName, idCard, username, email, phoneNo, password, DoB, gender, specialty, medicalLicenseNumber, dateJoin, yearsOfExperience);
        System.out.println(success ? "Doctor registered successfully!" : "Registration failed. Username may already exist.");
    }

    // Registration for Pharmacist
    public void pharmacistRegister() {
        String fullName = Helper.readString("Enter full name:");
        String idCard = Helper.readString("Enter ID card number:");
        String username = Helper.readString("Enter desired username:");
        String email = Helper.readString("Enter email:");
        String phoneNo = Helper.readString("Enter phone number:");
        String password = Helper.readString("Enter desired password:");
        LocalDateTime DoB = Helper.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Helper.readString("Enter gender:");
        String pharmacistLicenseNumber = Helper.readString("Enter pharmacist license number:");
        LocalDateTime dateOfEmployment = Helper.readDate("Enter date of employment (yyyy-MM-dd):");

        boolean success = AuthenticationController.registerPharmacist(fullName, idCard, username, email, phoneNo, password, DoB, gender, pharmacistLicenseNumber, dateOfEmployment);
        System.out.println(success ? "Pharmacist registered successfully!" : "Registration failed. Username may already exist.");
    }

    // Registration for Admin
    public void adminRegister() {
        String fullName = Helper.readString("Enter full name:");
        String idCard = Helper.readString("Enter ID card number:");
        String username = Helper.readString("Enter desired username:");
        String email = Helper.readString("Enter email:");
        String phoneNo = Helper.readString("Enter phone number:");
        String password = Helper.readString("Enter desired password:");
        LocalDateTime DoB = Helper.readDate("Enter date of birth (yyyy-MM-dd):");
        String gender = Helper.readString("Enter gender:");
        LocalDateTime dateOfCreation = Helper.readDate("Enter date of creation (yyyy-MM-dd):");

        boolean success = AuthenticationController.registerAdmin(fullName, idCard, username, email, phoneNo, password, DoB, gender, dateOfCreation);
        System.out.println(success ? "Admin registered successfully!" : "Registration failed. Username may already exist.");
    }
}
