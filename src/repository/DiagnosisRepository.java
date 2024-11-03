package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import model.Diagnosis;
import model.Prescription;
import model.TreatmentPlans;



public class DiagnosisRepository {
    private static final String folder = "data";

    // Static data collection for Diagnosis records only  (key = patientID)
    public static HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords = new HashMap<>();
    /**
     * Save Diagnosis records map to a CSV file
     */
    
    private boolean checkDuplicateID(String id) {
    	return patientDiagnosisRecords.get(id) != null;
    	
    }
    public static void saveDiagnosisRecordsToCSV(String fileName, HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String patientID : patientDiagnosisRecords.keySet()) {
                for (Diagnosis record : patientDiagnosisRecords.get(patientID)) {
                    writer.write(diagnosisToCSV(record));
                    writer.newLine();
                }
            }
            System.out.println("Diagnosis records successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving diagnosis records to CSV: " + e.getMessage());
        }
    }

    // Convert a DiagnosisRecord object to a CSV line
    private static String diagnosisToCSV(Diagnosis record) {
        return String.join(",",
                record.getPatientID(),                    // Patient ID
                record.getDiagnosisID(),                  // Diagnosis ID
                record.getDiagnosisDate().toString(),     // Diagnosis date
                record.getTreatmentPlans().toString(),    // Treatment plans
                "\"" + record.getDiagnosisDescription() + "\""         // Diagnosis description
        );
    }

    /**
     * Load Diagnosis records from a CSV file, or create an empty file if it doesn't exist
     */
    public static void loadDiagnosisRecordsFromCSV(String fileName, HashMap<String, ArrayList<Diagnosis>> patientDiagnosisRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;
        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();  // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;  // No data to load, as the file was just created
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Diagnosis record = csvToDiagnosisRecord(line);
                if (record != null) {
                    addDiagnosis(record.getPatientID(), record);
                }
            }
            System.out.println("Successfully loaded " + patientDiagnosisRecords.size() + " diagnosis records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading diagnosis records: " + e.getMessage());
        }
    }

    public static void addDiagnosis(String patientID, Diagnosis diagnosis) {
        ArrayList<Diagnosis> diagnoses = patientDiagnosisRecords.getOrDefault(patientID, new ArrayList<>());
        diagnoses.add(diagnosis);
        patientDiagnosisRecords.put(patientID, diagnoses);
    }

    // Convert a CSV line to a DiagnosisRecord object
    private static Diagnosis csvToDiagnosisRecord(String csv) {
        // Split by comma, ignoring commas within quotes
        String[] fields = csv.split(",");
        try {
            String patientID = fields[0];
            String diagnosisID = fields[1];
            LocalDateTime diagnosisDate = LocalDateTime.parse(fields[2]);
            TreatmentPlans treatmentPlan = TreatmentPlansRepository.diagnosisToTreatmentPlansMap.get(fields[1]);
            
            // Remove leading and trailing quotes from Diagnosis Description if present
            String diagnosisDescription = fields[4].replace("\"", "");

            Prescription prescription = PrescriptionRepository.prescriptionMap.get(fields[1]);

            return new Diagnosis(patientID, diagnosisID, diagnosisDate, treatmentPlan, diagnosisDescription,prescription);
        } catch (Exception e) {
            System.out.println("Error parsing diagnosis record data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clear all diagnosis record data and save empty files
     */
    public static boolean clearDiagnosisRecordDatabase() {
        patientDiagnosisRecords.clear();
        saveDiagnosisRecordsToCSV("diagnosis_records.csv", patientDiagnosisRecords);
        return true;
    }
}
