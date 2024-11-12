package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;
import repository.*;
import controller.AppointmentController;
import controller.MedicineController;

public class UpdateMedicalRecordUI {

    private Doctor doctor;
    private MedicalRecord medicalRecord;
    private Scanner sc;

    // Constructor to initialize with the doctor and medical record to be updated
    public UpdateMedicalRecordUI(Doctor doctor, MedicalRecord medicalRecord) {
        this.doctor = doctor;
        this.medicalRecord = medicalRecord;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        // Add a new diagnosis
        System.out.println("Adding New Diagnosis");
        System.out.println("Enter Diagnosis Description:");
        String diagnosisDescription = sc.nextLine();
        Diagnosis newDiagnosis = addNewDiagnosis(medicalRecord.getPatientID(), diagnosisDescription);

        System.out.println("New diagnosis added successfully with autogenerated ID: " + newDiagnosis.getDiagnosisID());

        // Prompt to add treatment plan and prescription
        System.out.println("\nChoose an option to add for the new diagnosis:");
        System.out.println("1. Add Treatment Plan");
        System.out.println("2. Add Prescription");
        System.out.println("3. Add Both Treatment Plan and Prescription");

        int updateChoice = sc.nextInt();
        sc.nextLine(); // Consume newline left-over

        switch (updateChoice) {
            case 1 -> addTreatmentPlan(newDiagnosis);
            case 2 -> addPrescriptions(newDiagnosis);
            case 3 -> {
                addTreatmentPlan(newDiagnosis);
                addPrescriptions(newDiagnosis);
            }
            default -> System.out.println("Invalid choice.");
        }

        // Save updated medical record back to repository
        RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
        RecordsRepository.saveAllRecordFiles();
    }

    private Diagnosis addNewDiagnosis(String patientId, String diagnosisDescription) {
        String diagnosisID = AppointmentController.generateRecordID(RecordFileType.DIAGNOSIS_RECORDS);
        Diagnosis diagnosis = new Diagnosis(patientId, diagnosisID, doctor.getUID(),
                medicalRecord.getRecordID(), LocalDateTime.now(), null,
                diagnosisDescription, null);
        medicalRecord.addDiagnosis(diagnosis); // Add the diagnosis to the current medical record
        DiagnosisRepository.addDiagnosis(patientId, diagnosis); // Add to the Diagnosis Repository
        DiagnosisRepository.saveAlltoCSV();
        return diagnosis;
    }

    private void addTreatmentPlan(Diagnosis diagnosis) {
        System.out.println("Enter Treatment Description:");
        String treatmentDescription = sc.nextLine();
        TreatmentPlans treatmentPlan = new TreatmentPlans(diagnosis.getDiagnosisID(), LocalDateTime.now(),
                treatmentDescription);
        diagnosis.setTreatmentPlans(treatmentPlan);
        TreatmentPlansRepository.diagnosisToTreatmentPlansMap.put(diagnosis.getDiagnosisID(), treatmentPlan);
        TreatmentPlansRepository.saveAlltoCSV();
        RecordsRepository.saveAllRecordFiles(); // Save changes
        System.out.println("Treatment plan added successfully for Diagnosis ID: " + diagnosis.getDiagnosisID());
    }

    private void addPrescriptions(Diagnosis newDiagnosis) {
        boolean addMore = true;
        while (addMore) {
            System.out.println("\n--- Add Prescribed Medication ---");
            System.out.print("Enter Medication Name: ");
            String medicationName = sc.nextLine();
            Medicine medicine = MedicineController.getMedicineByName(medicationName);

            if (medicine == null) {
                System.out.println("\n--- Invalid Prescribed Medication ---");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();
            System.out.print("Enter Period (Days): ");
            int periodDays = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter Dosage: ");
            String dosage = sc.nextLine();

            // Add the prescribed medication
            String medicineID = medicine.getMedicineID();
            PrescribedMedication prescribedMedication = new PrescribedMedication(newDiagnosis.getDiagnosisID(), medicineID,
                    quantity, periodDays, null, dosage);
            Prescription prescription = addPrescription(newDiagnosis, prescribedMedication);
            PrescriptionRepository.PRESCRIPTION_MAP.put(newDiagnosis.getDiagnosisID(), prescription);

            System.out.println("Medication prescribed successfully for Diagnosis ID: " + newDiagnosis.getDiagnosisID());

            // Prompt to add more medication or finish
            System.out.print("Would you like to add another prescribed medication? (yes/no): ");
            addMore = sc.nextLine().trim().equalsIgnoreCase("yes");
        }

        System.out.println("Finished adding prescribed medications for Diagnosis ID: " + newDiagnosis.getDiagnosisID());
    }

    private Prescription addPrescription(Diagnosis diagnosis, PrescribedMedication prescribedMedication) {
        Prescription prescription = diagnosis.getPrescription();
        if (prescription == null) {
            prescription = new Prescription(diagnosis.getDiagnosisID(), LocalDateTime.now(), new ArrayList<>());
            diagnosis.setPrescription(prescription);
        }
        prescription.addPrescribedMedication(prescribedMedication);
        PrescribedMedicationRepository.saveAlltoCSV();
        RecordsRepository.saveAllRecordFiles();
        return prescription;
    }
}
