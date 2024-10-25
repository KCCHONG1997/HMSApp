package view;

import java.util.Scanner;
import HMSApp.HMSMain;

public class PharmacistUI {
    public static void printMenu() {
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            printMenu();
            choice = sc.nextInt();
            switch(choice) {
                case 1: 
                    // Code for viewing appointment outcome record
                    break;
                case 2: 
                    // Code for updating prescription status
                    break;
                case 3: 
                    // Code for viewing medication inventory
                    break;
                case 4: 
                    // Code for submitting replenishment request
                    break;
                case 5: 
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        } while(choice != 5);
        
        sc.close(); // Close the Scanner
    }
}
