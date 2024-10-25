package HMSApp;
import view.*;
import java.util.Scanner;
public class HMSMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			printHMSWelcomeTitle();
			System.out.println("Please enter your role: ");
			System.out.println("1. Patient");
			System.out.println("2. Doctor");
			System.out.println("3. Pharmacist");
			System.out.println("4. Administrator");
			int role = sc.nextInt();
			switch(role) {
			case 1:
				LoginUI.patientLogin();
				break;
			case 2:
				LoginUI.doctorLogin();
				break;
			case 3:
				LoginUI.pharmacistLogin();
				break;
			case 4:
				LoginUI.administratorLogin();
				break;
			default:
				System.out.println("Invalid choice!");
				break;
			}
		}
	}
	
	private static void printHMSWelcomeTitle() {
	    System.out.println();
	    System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
	    System.out.println("║                                                                                                            ║");
	    System.out.println("║  ____   ____      ______  _______            ______              _____        _____        _____    ║");
	    System.out.println("║ |    | |    |    |      \\/       \\       ___|\\     \\         ___|\\    \\   ___|\\    \\   ___|\\    \\   ║");
	    System.out.println("║ |    | |    |   /          /\\     \\     |    |\\     \\       /    /\\    \\ |    |\\    \\ |    |\\    \\  ║");
	    System.out.println("║ |    |_|    |  /     /\\   / /\\     |    |    |/____/|      |    |  |    ||    | |    ||    | |    | ║");
	    System.out.println("║ |    .-.    | /     /\\ \\_/ / /    /| ___|    \\|   | |      |    |__|    ||    |/____/||    |/____/| ║");
	    System.out.println("║ |    | |    ||     |  \\|_|/ /    / ||    \\    \\___|/       |    .--.    ||    ||    |||    ||    || ║");
	    System.out.println("║ |    | |    ||     |       |    |  ||    |\\     \\          |    |  |    ||    ||____|/|    ||____|/ ║");
	    System.out.println("║ |____| |____||\\____\\       |____|  /|\\ ___\\|_____|         |____|  |____||____|       |____|        ║");
	    System.out.println("║ |    | |    || |    |      |    | / | |    |     |         |    |  |    ||    |       |    |        ║");
	    System.out.println("║ |____| |____| \\|____|      |____|/   \\|____|_____|         |____|  |____||____|       |____|        ║");
	    System.out.println("║   \\(     )/      \\(          )/         \\(    )/             \\(      )/    \\(           \\(          ║");
	    System.out.println("║    '     '        '          '           '    '               '      '      '            '          ║");
	    System.out.println("║                                                                                                     ║");
	    System.out.println("║                           Welcome to Hospital Management System                                     ║");
	    System.out.println("║                                                                                                     ║");
	    System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
	}

}
