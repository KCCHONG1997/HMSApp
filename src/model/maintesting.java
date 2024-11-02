package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import repository.RecordsRepository;

public class maintesting {
    public static void main(String[] args) {
        // Step 1: Create Medicines
        Medicine medicine1 = new Medicine("Aspirin", "M001", "PharmaCorp", LocalDateTime.of(2025, 1, 1, 0, 0), 100);
        Medicine medicine2 = new Medicine("Metformin", "M002", "HealthInc", LocalDateTime.of(2025, 6, 1, 0, 0), 200);

        // Step 2: Create PrescribedMedications
        PrescribedMedication prescribedMedication1 = new PrescribedMedication(
            medicine1, 30, LocalDateTime.of(2024, 4, 1, 0, 0),
            LocalDateTime.of(2024, 4, 15, 0, 0), PrescriptionStatus.PENDING,
            "Take one pill daily after meals"
        );

        PrescribedMedication prescribedMedication2 = new PrescribedMedication(
            medicine2, 60, LocalDateTime.of(2024, 4, 1, 0, 0),
            LocalDateTime.of(2024, 5, 1, 0, 0), PrescriptionStatus.PENDING,
            "Take one pill in the morning and one in the evening"
        );

        // Step 3: Create a Prescription with the list of PrescribedMedications
        List<PrescribedMedication> medicationsForPrescription1 = new ArrayList<>();
        medicationsForPrescription1.add(prescribedMedication1);
        medicationsForPrescription1.add(prescribedMedication2);
        
        Prescription prescription1 = new Prescription(medicationsForPrescription1, LocalDateTime.of(2024, 2, 1, 0, 0));

        // Step 4: Create Diagnoses with the Prescription list
        List<Prescription> prescriptionsForDiagnosis1 = new ArrayList<>();
        prescriptionsForDiagnosis1.add(prescription1);
        
        Diagnosis diagnosis1 = new Diagnosis(
            "P001", "Hypertension and Type 2 Diabetes", 
            LocalDateTime.of(2024, 4, 1, 10, 0), prescriptionsForDiagnosis1
        );

        Diagnosis diagnosis2 = new Diagnosis(
            "P001", "High Cholesterol", LocalDateTime.of(2024, 5, 1, 12, 0), 
            new ArrayList<>()
        );

        // Step 5: Add Diagnoses to MedicalRecord
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisList.add(diagnosis1);
        diagnosisList.add(diagnosis2);

        MedicalRecord medicalRecord = new MedicalRecord(
            "MR001", LocalDateTime.now(), LocalDateTime.now(), 
            RecordStatusType.ACTIVE, "P001", "O+", diagnosisList
        );

        // Step 6: Save the MedicalRecord to repository and CSV
        RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
        RecordsRepository.saveAllRecordFiles();

        System.out.println("Medical record created and saved successfully.");
        
        // Step 7: Load and Verify Records
        RecordsRepository.loadAllRecordFiles();

        // Retrieve and print the loaded record
        MedicalRecord loadedRecord = RecordsRepository.MEDICAL_RECORDS.get("MR001");
        if (loadedRecord != null) {
            System.out.println("Loaded Medical Record:");
            System.out.println("Patient ID: " + loadedRecord.getPatientID());
            System.out.println("Record ID: " + loadedRecord.getRecordID());
            System.out.println("Created Date: " + loadedRecord.getCreatedDate());
            System.out.println("Diagnoses:");
            for (Diagnosis diag : loadedRecord.getDiagnosis()) {
                System.out.println(" - Description: " + diag.getDiagnosisDescription());
                System.out.println(" - Date: " + diag.getDiagnosisDate());
                if (!diag.getPrescriptions().isEmpty()) {
                    for (Prescription pres : diag.getPrescriptions()) {
                        System.out.println("   Prescribed Date: " + pres.getPrescriptionDate());
                        for (PrescribedMedication med : pres.getMedications()) {
                            System.out.println("     Medication: " + med.getMedication().getMedicineName());
                            System.out.println("     Dosage: " + med.getDosage());
                        }
                    }
                }
            }
        } else {
            System.out.println("Error: Record not loaded correctly.");
        }
    }
}
