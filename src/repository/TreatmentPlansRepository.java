package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import model.DiagnosisRecord;
import model.Patient;
import model.TreatmentPlans;

public class TreatmentPlansRepository {
    private static final String folder = "data";

    // Static data collection for Treatment Plan records  //key : diagnosisID
    public static HashMap<String,TreatmentPlans> diagnosisToTreatmentPlansMap = new HashMap<>();

    /**
     * Save TreatmentPlans records map to a CSV file
     */
    public static void saveTreatmentPlansToCSV(String fileName, HashMap<String, TreatmentPlans> diagnosisTreatmentPlansMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : diagnosisTreatmentPlansMap.keySet()) {
                TreatmentPlans treatmentPlan = diagnosisTreatmentPlansMap.get(diagnosisID);
                if (treatmentPlan != null) {
                    writer.write(treatmentPlanToCSV(diagnosisID, treatmentPlan));
                    writer.newLine();
                }
            }
            System.out.println("Treatment plans successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving treatment plans to CSV: " + e.getMessage());
        }
    }

    // Convert a TreatmentPlan object to a CSV line
    private static String treatmentPlanToCSV(String diagnosisID, TreatmentPlans treatmentPlan) {
        return String.join(",",
                treatmentPlan.getDiagnosisID(),                           // Diagnosis ID
                treatmentPlan.getTreatmentDate().toString(),  // Treatment date
                treatmentPlan.getTreatmentDescription()       // Treatment description
        );
    }
    // Convert a TreatmentPlan object to a CSV line (using only LocalDate)


    /**
     * Load plans from a CSV file or create an empty file if it doesn't exist
     */
    private static void loadTreatmentPlansFromCSV(String fileName, HashMap<String, TreatmentPlans> diagnosisTreatmentPlansMap) {
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
                TreatmentPlans treatmentPlan = csvToTreatmentPlan(line);
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (treatmentPlan != null && diagnosisID != null) {
                    diagnosisTreatmentPlansMap.put(diagnosisID, treatmentPlan);
                }
            }
            System.out.println("Successfully loaded " + diagnosisTreatmentPlansMap.size() + " treatment plans from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading treatment plans: " + e.getMessage());
        }
    }

    // Helper to extract Diagnosis ID from CSV line
    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }
    // Convert a CSV line to a TreatmentPlan object (using LocalDate)
    private static TreatmentPlans csvToTreatmentPlan(String csv) {
        String[] fields = csv.split(",");
        try {
            return new TreatmentPlans(
                    fields[1],                                // diagnosisID
                    LocalDateTime.parse(fields[2]),           // Treatment date
                    fields[3]                                 //treatment Description
            );
        } catch (Exception e) {
            System.out.println("Error parsing treatment plan data: " + e.getMessage());
        }
        return null;
    }
    /**
     * Clear all treatment plan data and save empty files
     */
    public static boolean clearTreatmentPlanDatabase() {
        diagnosisToTreatmentPlansMap.clear();
        saveTreatmentPlansToCSV("treatments_plans_records.csv", diagnosisToTreatmentPlansMap);
        return true;
    }
}