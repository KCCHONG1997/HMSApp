package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Prescription;
import model.PrescribedMedication;

public class PrescriptionRepository {
    private static final String folder = "data";

    // Static data collection for Prescription records  //key : prescriptionID
    public static HashMap<String, Prescription> prescriptionMap = new HashMap<>();

    /**
     * Save Prescription records map to a CSV file
     */
    public static void savePrescriptionsToCSV(String fileName, HashMap<String, Prescription> prescriptionMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String prescriptionID : prescriptionMap.keySet()) {
                Prescription prescription = prescriptionMap.get(prescriptionID);
                if (prescription != null) {
                    writer.write(prescriptionToCSV(prescriptionID, prescription));
                    writer.newLine();
                }
            }
            System.out.println("Prescriptions successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving prescriptions to CSV: " + e.getMessage());
        }
    }

    // Convert a Prescription object to a CSV line
    private static String prescriptionToCSV(String prescriptionID, Prescription prescription) {
        StringBuilder medicationsCSV = new StringBuilder();
        for (PrescribedMedication medication : prescription.getMedications()) {
            medicationsCSV.append(medication.toString()).append(";"); // Assuming PrescribedMedication has a meaningful toString
        }
        return String.join(",",
                prescriptionID,                                 // Prescription ID
                prescription.getPrescriptionDate().toString(),  // Prescription date
                medicationsCSV.toString()                      // Medications (semicolon-separated)
        );
    }

    /**
     * Load prescriptions from a CSV file or create an empty file if it doesn't exist
     */
    private static void loadPrescriptionsFromCSV(String fileName, HashMap<String, Prescription> prescriptionMap) {
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
                Prescription prescription = csvToPrescription(line);
                String prescriptionID = getPrescriptionIDFromCSV(line);
                if (prescription != null && prescriptionID != null) {
                    prescriptionMap.put(prescriptionID, prescription);
                }
            }
            System.out.println("Successfully loaded " + prescriptionMap.size() + " prescriptions from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading prescriptions: " + e.getMessage());
        }
    }

    // Helper to extract Prescription ID from CSV line
    private static String getPrescriptionIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    // Convert a CSV line to a Prescription object
    private static Prescription csvToPrescription(String csv) {
        String[] fields = csv.split(",");
        try {
            List<PrescribedMedication> medications = parseMedications(fields[2]);
            LocalDateTime prescriptionDate = LocalDateTime.parse(fields[1]);
            return new Prescription(medications, prescriptionDate);
        } catch (Exception e) {
            System.out.println("Error parsing prescription data: " + e.getMessage());
        }
        return null;
    }

    // Parse medications from CSV format (semicolon-separated list)
    private static List<PrescribedMedication> parseMedications(String medicationsCSV) {

    	
    	
    	//NOT IMPLEMENTED YET
    	
    	
    	
        return new ArrayList<>(); // Placeholder, modify as per PrescribedMedication details
    }

    /**
     * Clear all prescription data and save empty files
     */
    public static boolean clearPrescriptionDatabase() {
        prescriptionMap.clear();
        savePrescriptionsToCSV("prescriptions_records.csv", prescriptionMap);
        return true;
    }
}
