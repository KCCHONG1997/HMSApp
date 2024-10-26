package view;

import java.util.Scanner;

public class HMSAppUI extends MainUI{
	
	// The centralized UI
	public HMSAppUI() {
		
	}
	
	@Override
	protected void printChoice(){
		printBreadCrumbs("HMS App UI");
        System.out.println("Would you like to? :");
        System.out.println("1. Login");
        System.out.println("2. Close App");
	}
	
	@Override
	public void start() {
		Scanner sc = new Scanner(System.in);
		
        while (true) {
        	printChoice();

            int role = sc.nextInt();
            sc.nextLine();  // Clear the newline character

            switch (role) {
                case 1:
                	LoginUI loginUI = new LoginUI();
                	loginUI.start();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
                    break;
            }	
        }
	}	
}
