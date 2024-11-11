package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.MedicalRecord;
import model.Diagnosis;
import model.Prescription;
import model.PrescribedMedication;

public class MedicalRecordUI extends MainUI {

    private MedicalRecord medicalRecord;

    // Constructor to initialize MedicalRecordUI with the specified medical record
    public MedicalRecordUI(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    // Method to display a formatted medical record in a box
    public void displayMedicalRecordInBox() {
        String border = "+------------------------------------------+";

        System.out.println(border);
        System.out.printf("| %-38s |\n", "Medical Record");
        System.out.println(border);
        System.out.printf("| %-20s: %-20s |\n", "Doctor ID", medicalRecord.getDoctorID());
        System.out.printf("| %-20s: %-20s |\n", "Patient ID", medicalRecord.getPatientID());
        System.out.printf("| %-20s: %-20s |\n", "Blood Type", medicalRecord.getBloodType());
        System.out.println(border);

        System.out.println("| Diagnoses:");
        for (Diagnosis diagnosis : medicalRecord.getDiagnosis()) {
            System.out.println(border);
            System.out.printf("| %-20s: %-20s |\n", "Diagnosis ID", diagnosis.getDiagnosisID());
            System.out.printf("| %-20s: %-20s |\n", "Description", diagnosis.getDiagnosisDescription());

            Prescription prescription = diagnosis.getPrescription();
            System.out.printf("| %-20s: %-20s |\n", "Prescription Date", prescription.getPrescriptionDate());

            System.out.println("| Medications:");
            for (PrescribedMedication medication : prescription.getMedications()) {
                System.out.printf("| %-20s: %-20s |\n", "Medicine ID", medication.getMedicineID());
                System.out.printf("| %-20s: %-20s |\n", "Quantity", medication.getMedicineQuantity());
                System.out.printf("| %-20s: %-20s |\n", "Dosage", medication.getDosage());
                System.out.printf("| %-20s: %-20s |\n", "Period (days)", medication.getPeriodDays());
                System.out.printf("| %-20s: %-20s |\n", "Status", medication.getPrescriptionStatus());
                System.out.println(border);
            }
            System.out.println(border);
        }

        System.out.println();
    }

    // Method to print options for this UI
    @Override
    protected void printChoice() {
        System.out.println("Options:");
        System.out.println("1. Back to Previous Menu");
        System.out.println("Enter your choice: ");
    }

    // Start method to display the medical record and provide a back option
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        displayMedicalRecordInBox();  // Display the medical record
        
        int choice = 0;
        do {
            printChoice();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Returning to previous menu...");
                    return;  // Exits this UI and goes back to the previous one
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 1);
    }
}
