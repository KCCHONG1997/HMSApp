package view;

import model.*;
import repository.*;
import controller.*;
import java.util.Scanner;

public class LoginUI extends MainUI {

    @Override
    protected void printChoice() {
        printBreadCrumbs("HMS App UI > Login Page");
        System.out.println("You like to login as?: ");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Pharmacist");
        System.out.println("4. Administrator");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printChoice();
            int role = -1;
            try {
                role = sc.nextInt();
                sc.nextLine();  // Clear the newline character
            } catch(Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Clear the scanner input buffer
                continue;
            }

            switch (role) {
                case 1:
                    patientLogin(sc);
                    break;
                case 2:
                    doctorLogin(sc);
                    break;
                case 3:
                    pharmacistLogin(sc);
                    break;
                case 4:
                    administratorLogin(sc);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    return; // Exit LoginUI and return to the main menu
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
                    break;
            }
        }
    }

    // Login for Patient
    public void patientLogin(Scanner sc) {
        String username = enterUsername(sc);
        String passwordHash = enterPassword(sc);

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.PATIENTS);

        if (personnel != null && personnel instanceof Patient) {
            if ("default".equals(passwordHash)) {
                changePassword(sc, personnel);
            }
            System.out.println("Login Successful!");
            // TODO
            // PatientUI.showPatientDashboard((Patient) personnel);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    // Login for Doctor
    public void doctorLogin(Scanner sc) {
        String username = enterUsername(sc);
        String passwordHash = enterPassword(sc);

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.DOCTORS);

        if (personnel != null && personnel instanceof Doctor) {
            if ("default".equals(passwordHash)) {
                changePassword(sc, personnel);
            }
            DoctorUI docUI = new DoctorUI((Doctor) personnel);
            docUI.start();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    // Login for Pharmacist
    public void pharmacistLogin(Scanner sc) {
        String username = enterUsername(sc);
        String passwordHash = enterPassword(sc);

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.PHARMACISTS);

        if (personnel != null && personnel instanceof Pharmacist) {
            if ("default".equals(passwordHash)) {
                changePassword(sc, personnel);
            }
            System.out.println("Login Successful!");
            // TODO
            // PharmacistUI.showPharmacistDashboard((Pharmacist) personnel);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    // Login for Administrator
    public void administratorLogin(Scanner sc) {
        String username = enterUsername(sc);
        String passwordHash = enterPassword(sc);

        HMSPersonnel personnel = AuthenticationController.login(username, passwordHash, PersonnelFileType.ADMINS);

        if (personnel != null && personnel instanceof Admin) {
            if ("default".equals(passwordHash)) {
                changePassword(sc, personnel);
            }
            AdminUI adminUI = new AdminUI((Admin) personnel);
            adminUI.start();
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    // Method to prompt the user to change their password
    private void changePassword(Scanner sc, HMSPersonnel personnel) {
        System.out.println("It appears that you are using the default password.");
        System.out.println("Please change your password for security reasons.");

        String newPassword;
        while (true) {
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine();
            System.out.print("Confirm new password: ");
            String confirmPassword = sc.nextLine();

            if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                AuthenticationController.updatePassword(personnel, newPassword); // Update password in the repository
                System.out.println("Password changed successfully!");
                break;
            } else {
                System.out.println("Passwords do not match or are empty. Please try again.");
            }
        }
    }

    // Utility methods for username and password input
    public static String enterUsername(Scanner sc) {
        System.out.print("Please enter your username: ");
        return sc.nextLine();
    }

    public static String enterPassword(Scanner sc) {
        System.out.print("Please enter your password: ");
        return sc.nextLine();
    }
}
