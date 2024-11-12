package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import controller.HMSPersonnelController;
import helper.Helper;
import model.MedicalRecord;
import model.Patient;
import model.Diagnosis;
import model.Prescription;
import model.PrescribedMedication;

public class MedicalRecordUI extends MainUI {

    private MedicalRecord medicalRecord;
    private Patient patient;

    // Constructor to initialize MedicalRecordUI with the specified medical record
    public MedicalRecordUI(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
        this.patient = HMSPersonnelController.getPatientById(medicalRecord.getPatientID());
    }

    // Method to display a formatted medical record in a box
    public void displayMedicalRecordInBox() {
        String border = "+------------------------------------------+";

        System.out.println(border);
        System.out.printf("| %-38s |\n", "Medical Record");
        System.out.println(border);
        System.out.printf("| %-20s: %-20s |\n", "Doctor ID", medicalRecord.getDoctorID());
        System.out.printf("| %-20s: %-20s |\n", "Patient ID", medicalRecord.getPatientID());
        System.out.printf("| %-20s: %-20s |\n", "Patient Name", (patient != null ? patient.getFullName() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Patient DOB", (patient != null ? patient.getDoB() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Patient Gender", (patient != null ? patient.getGender() : "Unknown"));
        System.out.printf("| %-20s: %-20s |\n", "Blood Type", medicalRecord.getBloodType());
        System.out.println(border);

        System.out.println("| Diagnoses:");
        if (medicalRecord.getDiagnosis() != null && !medicalRecord.getDiagnosis().isEmpty()) {
            Set<String> printedDiagnosisIDs = new HashSet<>();
            for (Diagnosis diagnosis : medicalRecord.getDiagnosis()) {
                String diagnosisID = diagnosis.getDiagnosisID();
                if (!printedDiagnosisIDs.contains(diagnosisID)) {
                    System.out.println(border);
                    System.out.printf("| %-20s: %-20s |\n", "Diagnosis ID", diagnosisID);
                    System.out.printf("| %-20s: %-20s |\n", "Description", diagnosis.getDiagnosisDescription());

                    // Mark this diagnosisID as printed
                    printedDiagnosisIDs.add(diagnosisID);
                }
                Prescription prescription = diagnosis.getPrescription();
                if (prescription != null) {
                    System.out.printf("| %-20s: %-20s |\n", "Prescription Date", prescription.getPrescriptionDate());

                    System.out.println("| Medications:");
                    if (prescription.getMedications() != null && !prescription.getMedications().isEmpty()) {
                        for (PrescribedMedication medication : prescription.getMedications()) {
                            System.out.printf("| %-20s: %-20s |\n", "Medicine ID", medication.getMedicineID());
                            System.out.printf("| %-20s: %-20s |\n", "Quantity", medication.getMedicineQuantity());
                            System.out.printf("| %-20s: %-20s |\n", "Dosage", medication.getDosage());
                            System.out.printf("| %-20s: %-20s |\n", "Period (days)", medication.getPeriodDays());
                            System.out.printf("| %-20s: %-20s |\n", "Status", medication.getPrescriptionStatus());
                            System.out.println(border);
                        }
                    } else {
                        System.out.println("| No prescribed medications found |");
                    }
                } else {
                    System.out.println("| No prescription found |");
                }
                System.out.println(border);
            }
        } else {
            System.out.println("| No diagnosis records found |");
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
        displayMedicalRecordInBox();  // Display the medical record

        int choice = 0;
        do {
            printChoice();
            choice = Helper.readInt("");
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
