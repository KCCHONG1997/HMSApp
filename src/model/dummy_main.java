package model;

import repository.*;
import view.HMSAppUI;
import model.*;
import enums.PrescriptionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class dummy_main {
    public static void main(String[] args) {
    	
    	HMSAppUI hms = new HMSAppUI();
    	hms.startTesting();
    	
        System.out.println("---- Starting Medical Record Repository Test ----\n");

        // 1. Initialize related entities for a MedicalRecord
        System.out.println("Creating related entities for MedicalRecord...");

        // Create a sample Treatment Plan
        TreatmentPlans treatmentPlan = new TreatmentPlans("D1", LocalDateTime.now(), "Rest and medication");
        TreatmentPlansRepository.diagnosisToTreatmentPlansMap.put("D1", treatmentPlan);
        TreatmentPlansRepository.saveTreatmentPlansToCSV("treatment_plans_records.csv", TreatmentPlansRepository.diagnosisToTreatmentPlansMap);

        // Create a sample Prescribed Medication
        PrescribedMedication medication1 = new PrescribedMedication("D1", "M1", "100mg", 10, PrescriptionStatus.DISPENSED, "Take once daily");
        ArrayList<PrescribedMedication> medicationsList = new ArrayList<>();
        medicationsList.add(medication1);
        PrescribedMedicationRepository.diagnosisToMedicationsMap.put("D1", medicationsList);
        PrescribedMedicationRepository.saveMedicationsToCSV("prescribed_medications.csv", PrescribedMedicationRepository.diagnosisToMedicationsMap);

        // Create a sample Prescription using the medications
        Prescription prescription = new Prescription("D1", LocalDateTime.now(), medicationsList);
        PrescriptionRepository.prescriptionMap.put("D1", prescription);
        PrescriptionRepository.savePrescriptionsToCSV("prescriptions_records.csv", PrescriptionRepository.prescriptionMap);

        // Create a Diagnosis with the Prescription and Treatment Plan
        Diagnosis diagnosis = new Diagnosis("P1", "D1", "DOCTOR1", LocalDateTime.now(), treatmentPlan, "Flu symptoms", prescription);
        ArrayList<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisList.add(diagnosis);
        DiagnosisRepository.patientDiagnosisRecords.put("P1", diagnosisList);
        DiagnosisRepository.saveDiagnosisRecordsToCSV("diagnosis_records.csv", DiagnosisRepository.patientDiagnosisRecords);

        System.out.println("Related entities created and saved.\n");

        // 2. Create a MedicalRecord and save it to RecordsRepository
        System.out.println("Creating and saving MedicalRecord...");
        MedicalRecord medicalRecord = new MedicalRecord(
            "MR1", 
            LocalDateTime.now(), 
            LocalDateTime.now(), 
            RecordStatusType.ACTIVE, 
            "P1", 
            "D1", 
            "O+", 
            diagnosisList
        );
        RecordsRepository.MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
        RecordsRepository.saveAllRecordFiles();

        System.out.println("MedicalRecord created and saved.\n");

        // 3. Clear data from repositories to simulate a fresh load
        System.out.println("Clearing repository data to test loading...");
        RecordsRepository.MEDICAL_RECORDS.clear();
        DiagnosisRepository.patientDiagnosisRecords.clear();
        PrescriptionRepository.prescriptionMap.clear();
        PrescribedMedicationRepository.diagnosisToMedicationsMap.clear();
        TreatmentPlansRepository.diagnosisToTreatmentPlansMap.clear();

        // 4. Reload data from CSV files to verify persistence
        System.out.println("Loading data from CSV files...");
        Repository.loadRepository(new RecordsRepository());
        Repository.loadRepository(new DiagnosisRepository());
        Repository.loadRepository(new PrescriptionRepository());
        Repository.loadRepository(new PrescribedMedicationRepository());
        Repository.loadRepository(new TreatmentPlansRepository());

        // 5. Display loaded MedicalRecord to verify
        System.out.println("\nLoaded Medical Records:");
        for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
            System.out.println(" - " + record);
        }

        System.out.println("\n---- Medical Record Repository Test Complete ----");
    }
}
