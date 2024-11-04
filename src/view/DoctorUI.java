package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.AuthenticationController;
import controller.RecordsController;
import HMSApp.HMSMain;
import model.Diagnosis;
import model.Doctor;
import model.MedicalRecord;
import model.PrescribedMedication;
import model.Prescription;
import model.SessionCookie;

public class DoctorUI extends MainUI {
	private Doctor doctor;
    public DoctorUI(Doctor doctor) {
        this.doctor = doctor;
    }
    
	@Override
    protected void printChoice() {
		System.out.printf("Welcome! Dr. --- %s ---\n", doctor.getFullName());
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
		showDoctorDashboard();
	}
    public void showDoctorDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            choice = sc.nextInt();
            switch(choice) {
                case 1: 
                    // Code for viewing patient medical records
                	viewPatientRecords();
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

    private void viewPatientRecords() {
        printBreadCrumbs("HMS App UI > Doctor Dashboard > View Patient Records");
        RecordsController rc = new RecordsController();
        ArrayList<MedicalRecord> records = rc.getMedicalRecordsByDoctorID(AuthenticationController.cookie.getUid());

        if (records.isEmpty()) {
            System.out.println("No medical records found for this doctor.");
            return;
        }

        for (MedicalRecord record : records) {
        	displayMedicalRecordInBox(record);
        }
    }
    
    private static void displayMedicalRecordInBox(MedicalRecord medicalRecord) {
        String border = "+----------------------------------------+";
        
        System.out.println(border);
        System.out.printf("| %-58s |\n", "Medical Record");
        System.out.println(border);
        System.out.printf("| %-25s: %-35s |\n", "Doctor ID", medicalRecord.getDoctorID());
        System.out.printf("| %-25s: %-35s |\n", "Patient ID", medicalRecord.getPatientID());
        System.out.printf("| %-25s: %-35s |\n", "Blood Type", medicalRecord.getBloodType());
        System.out.println(border);

        System.out.println("| Diagnoses:");
        for (Diagnosis diagnosis : medicalRecord.getDiagnosis()) {
            System.out.println(border);
            System.out.printf("| %-25s: %-35s |\n", "Diagnosis ID", diagnosis.getDiagnosisID());
            System.out.printf("| %-25s: %-35s |\n", "Description", diagnosis.getDiagnosisDescription());

            Prescription prescription = diagnosis.getPrescription();
            System.out.printf("| %-25s: %-35s |\n", "Prescription Date", prescription.getPrescriptionDate());
            
            System.out.println("| Medications--------------------------");
            for (PrescribedMedication medication : prescription.getMedications()) {
                System.out.printf("| %-25s: %-35s |\n", "Medicine ID", medication.getMedicineID());
                System.out.printf("| %-25s: %-35s |\n", "Quantity", medication.getMedicineQuantity());
                System.out.printf("| %-25s: %-35s |\n", "Dosage", medication.getDosage());
                System.out.printf("| %-25s: %-35s |\n", "Period (days)", medication.getPeriodDays());
                System.out.printf("| %-25s: %-35s |\n", "Status", medication.getPrescriptionStatus());
                System.out.println(border);
            }
            
            System.out.println(border);
        }
        
        System.out.println();
    }

}
