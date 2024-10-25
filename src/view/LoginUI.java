package view;
import model.*;
import repository.*;
import controller.*;
import java.util.Scanner;
public class LoginUI {
	
	
	protected void loginUI() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Please enter your role: ");
			System.out.println("1. Patient");
			System.out.println("2. Doctor");
			System.out.println("3. Pharmacist");
			System.out.println("4. Administrator");
			int role = sc.nextInt();
			switch(role) {
			case 1:
//				LoginUI.patientLogin();
				System.out.println("PATIENT");
				break;
			case 2:
//				LoginUI.doctorLogin();
				break;
			case 3:
//				LoginUI.pharmacistLogin();
				break;
			case 4:
//				LoginUI.administratorLogin();
				break;
			default:
				System.out.println("Invalid choice!");
				break;
			}
		}
	}
//	public static void patientLogin() {
//		 String username = enterUsername();
//		 String passwordhash = enterPassword();
//		 Patient retrievedPatient = (Patient) AuthenticationController.(username, PersonnelFileType.PATIENTS);
//		 if (retrievedPatient != null) {
//			 if (retrievedPatient.getPasswordHash().equal(passwordHash){
//				 System.out.println("Login Successfully!");
//				 PatientUI.main(null);
//			 }
//		 }
//	}
//	
//	public static void doctorLogin() {
//		 String username = enterUsername();
//		 String passwordhash = enterPassword();
//		 Doctor retrievedDoctor = (Doctor) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.DOCTORS);
//		 if (retrievedDoctor != null) {
//			 if ((retrievedDoctor.getPasswordHash().equal(passwordHash){
//				 System.out.println("Login Successfully!");
//				 DoctorUI.main(null);
//			 }
//		 }
//	}
//	
//	public static void pharmacistLogin() {
//		 String username = enterUsername();
//		 String passwordhash = enterPassword();
//		 Pharmacist retrievedPharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.PHARMACISTS);
//		 if ( retrievedPharmacist != null) {
//			 if ( retrievedPharmacist.getPasswordHash().equal(passwordHash){
//				 System.out.println("Login Successfully!");
//				 PharmacistUI.main(null);
//			 }
//		 }
//	}
//	
//	public static void administratorLogin() {
//		 String username = enterUsername();
//		 String passwordhash = enterPassword();
//		 Administrator retrievedAdministrator = (Administrator) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.ADMINISTRATORS);
//		 if (retrievedAdministrator != null) {
//			 if (retrievedAdministrator.getPasswordHash().equal(passwordHash){
//				 System.out.println("Login Successfully!");
//				 AdministratorUI.main(null);
//			 }
//		 }
//	}
	
	public static String enterUsername() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your username: ");
		String username = sc.nextLine();
		sc.close();
		return username;
	}
	
	public static String enterPassword() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your password: ");
		String password = sc.nextLine();
		sc.close();
		return password;
	}
}
