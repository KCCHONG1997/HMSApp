package view;

import java.util.Scanner;
import HMSApp.HMSMain;
import model.Doctor;

public class DoctorUI extends MainUI {
	private Doctor doctor;
    public DoctorUI(Doctor doctor) {
        this.doctor = doctor;
    }
    
	@Override
    protected void printChoice() {
		printBreadCrumbs("HMS App UI > Doctor Dashboard");
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }
    
	public void start() {
		printChoice();
		showDoctorDashboard();
	}
    public void showDoctorDashboard() {
    	
    	System.out.printf("Welcome! Dr. --- %s ---", doctor.getFullName());
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            choice = sc.nextInt();
            switch(choice) {
                case 1: 
                    // Code for viewing patient medical records
                    break;
                case 2: 
                    // Code for updating patient medical records
                    break;
                case 3: 
                    // Code for viewing personal schedule
                    break;
                case 4: 
                    // Code for setting availability for appointments
                    break;
                case 5: 
                    // Code for accepting or declining appointment requests
                    break;
                case 6: 
                    // Code for viewing upcoming appointments
                    break;
                case 7: 
                    // Code for recording appointment outcome
                    break;
                case 8: 
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        } while(choice != 8);
        
        sc.close(); // Close the Scanner
    }
}
