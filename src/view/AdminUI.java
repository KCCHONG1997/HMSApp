package view;
import java.util.Scanner;
import HMSApp.HMSMain;
import model.Admin;
import controller.HMSPersonnelController;

public class AdminUI extends MainUI {
	private Admin admin;
    public AdminUI(Admin admin) {
        this.admin = admin;
    }
    
	@Override
    protected void printChoice() {
		System.out.printf("Welcome! Admin --- %s ---\n", admin.getFullName());
		printBreadCrumbs("HMS App UI > Admin Dashboard");
        System.out.println("Administrator Menu:");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory ");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
    
	public void start() {
		showAdminDashboard();
	}
    public void showAdminDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            
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
   
