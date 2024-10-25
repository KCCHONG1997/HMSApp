package view;
import java.util.Scanner;

import HMSApp.HMSMain;

public class PatientUI {
    public static void printMenu() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            printMenu();
            choice = sc.nextInt();
            switch(choice) {
                case 1: 
                    // Code for viewing medical record
                    break;
                case 2: 
                    // Code for updating personal information
                    break;
                case 3: 
                    // Code for viewing available appointment slots
                    break;
                case 4: 
                    // Code for scheduling an appointment
                    break;
                case 5: 
                    // Code for rescheduling an appointment
                    break;
                case 6: 
                    // Code for canceling an appointment
                    break;
                case 7: 
                    // Code for viewing scheduled appointments
                    break;
                case 8: 
                    // Code for viewing past appointment outcomes
                    break;
                case 9: 
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        } while(choice != 9);
        
        sc.close(); // Close the Scanner
    }
}