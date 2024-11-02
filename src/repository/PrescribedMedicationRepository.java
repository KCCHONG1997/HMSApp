package repository;

import enums.PrescriptionStatus;
import model.PrescribedMedication;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PrescribedMedicationRepository {
    private static final String folder = "data";

    // Static data collection for prescribed medications per diagnosis (key = diagnosisID)
    public static HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap = new HashMap<>();
    private boolean checkDuplicateID(String id) {
        return diagnosisToMedicationsMap.get(id) != null;
    }

    public static void saveMedicationsToCSV(String fileName, HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String diagnosisID : diagnosisToMedicationsMap.keySet()) {
                for (PrescribedMedication medication : diagnosisToMedicationsMap.get(diagnosisID)) {
                    writer.write(medicationToCSV(diagnosisID, medication));
                    writer.newLine();
                }
            }
            System.out.println("Medications successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving medications to CSV: " + e.getMessage());
        }
    }


    private static String medicationToCSV(String diagnosisID, PrescribedMedication medication) {
        return String.join(",",
                medication.getDiagnosisID(),                       // Diagnosis ID
                medication.getMedicineID(),                                 // Medication ID
                medication.getMedicineQuantity(),                           // Medicine quantity
                String.valueOf(medication.getPeriodDays()),                 // Period in days converted to String
                medication.getPrescriptionStatus().toString(),              // enums.PrescriptionStatus PrescriptionStatus;
                "\"" + medication.getDosage()+ "\""                         // dosage  Instructions (quotes fr soafety)

        );
    }
    public static void loadMedicationsFromCSV(String fileName, HashMap<String, ArrayList<PrescribedMedication>> diagnosisToMedicationsMap) {
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
                PrescribedMedication medication = csvToMedication(line);
                String diagnosisID = getDiagnosisIDFromCSV(line);
                if (medication != null && diagnosisID != null) {
                    addMedication(diagnosisID, medication);
                }
            }
            System.out.println("Successfully loaded medications for " + diagnosisToMedicationsMap.size() + " diagnoses from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medications: " + e.getMessage());
        }
    }
    public static void addMedication(String diagnosisID, PrescribedMedication medication) {
        ArrayList<PrescribedMedication> medications = diagnosisToMedicationsMap.getOrDefault(diagnosisID, new ArrayList<>());
        medications.add(medication);
        diagnosisToMedicationsMap.put(diagnosisID, medications);
    }
    private static String getDiagnosisIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[0];
    }

    private static PrescribedMedication csvToMedication(String csv) {
        String[] fields = csv.split(",");
        try {
            String diagnosisID = fields[0];
            String medicineID = fields[1];
            String medicineQuantity = fields[2];
            int periodDays = Integer.parseInt(fields[3]);  // Convert periodDays from String to int
            PrescriptionStatus prescriptionStatus = PrescriptionStatus.valueOf(fields[4]);  // Convert enum from String
            String dosage = fields[5].replace("\"", "");  // Remove quotes from instructions

            return new PrescribedMedication(diagnosisID, medicineID, medicineQuantity, periodDays, prescriptionStatus, dosage);
        } catch (Exception e) {
            System.out.println("Error parsing medication data: " + e.getMessage());
        }
        return null;
    }




}