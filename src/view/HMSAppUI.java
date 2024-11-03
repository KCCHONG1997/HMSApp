package view;

import java.util.Scanner;

import repository.DiagnosisRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.PrescriptionRepository;
import repository.RecordsRepository;
import repository.Repository;
import repository.TreatmentPlansRepository;

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
		loadHMSRepository();
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
	
	public void startTesting() {
		loadHMSRepository();
	}	
	
	public void loadHMSRepository() {
		PersonnelRepository.loadAllPersonnelFiles();
        Repository.loadRepository(new RecordsRepository());
        Repository.loadRepository(new PrescribedMedicationRepository());
        Repository.loadRepository(new TreatmentPlansRepository());
        Repository.loadRepository(new PrescriptionRepository());
        Repository.loadRepository(new DiagnosisRepository());
	}
}
