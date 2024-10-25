package view;

import java.util.Scanner;
import HMSApp.HMSMain;

public class AdministratorUI {
    public static void printMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
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
                    // Code for viewing and managing hospital staff
                    break;
                case 2: 
                    // Code for viewing appointment details
                    break;
                case 3: 
                    // Code for viewing and managing medication inventory
                    break;
                case 4: 
                    // Code for approving replenishment requests
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
