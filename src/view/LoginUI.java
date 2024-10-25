package view;
import model.*;
import repository.*;
import controller.*;
import java.util.Scanner;
public class LoginUI {
	public static void patientLogin() {
		 String username = enterUsername();
		 String passwordhash = enterPassword();
		 Patient retrievedPatient = (Patient) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.PATIENTS);
		 if (retrievedPatient != null) {
			 if (retrievedPatient.getPasswordHash().equal(passwordHash){
				 System.out.println("Login Successfully!");
				 PatientUI.main(null);
			 }
		 }
	}
	
	public static void doctorLogin() {
		 String username = enterUsername();
		 String passwordhash = enterPassword();
		 Doctor retrievedDoctor = (Doctor) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.DOCTORS);
		 if (retrievedDoctor != null) {
			 if ((retrievedDoctor.getPasswordHash().equal(passwordHash){
				 System.out.println("Login Successfully!");
				 DoctorUI.main(null);
			 }
		 }
	}
	
	public static void pharmacistLogin() {
		 String username = enterUsername();
		 String passwordhash = enterPassword();
		 Pharmacist retrievedPharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.PHARMACISTS);
		 if ( retrievedPharmacist != null) {
			 if ( retrievedPharmacist.getPasswordHash().equal(passwordHash){
				 System.out.println("Login Successfully!");
				 PharmacistUI.main(null);
			 }
		 }
	}
	
	public static void administratorLogin() {
		 String username = enterUsername();
		 String passwordhash = enterPassword();
		 Administrator retrievedAdministrator = (Administrator) HMSPersonnelController.getPersonnelByusername(username, PersonnelFileType.ADMINISTRATORS);
		 if (retrievedAdministrator != null) {
			 if (retrievedAdministrator.getPasswordHash().equal(passwordHash){
				 System.out.println("Login Successfully!");
				 AdministratorUI.main(null);
			 }
		 }
	}
	
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
