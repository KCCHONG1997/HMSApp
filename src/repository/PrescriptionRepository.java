package repository;

import model.Medicine;
import model.PrescribedMedication;
import model.Prescription;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.PrescriptionStatus;

public class PrescriptionRepository {
    private static final String folder = "data";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Static data collection for Prescription records, where key is the prescription ID
    public static HashMap<String, Prescription> prescriptionMap = new HashMap<>();

    /**
     * Save Prescription records to a CSV file
     */
    public static void savePrescriptionsToCSV(String fileName, HashMap<String, Prescription> prescriptionMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();
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
        prescription.getMedications().forEach(med -> 
            medicationsCSV.append(med.getMedication().getMedicineName())
                          .append("#")
                          .append(med.getMedicineQuantity())
                          .append("#")
                          .append(med.getPrescriptionStartDate().format(DATE_TIME_FORMATTER))
                          .append("#")
                          .append(med.getPrescriptionEndDate().format(DATE_TIME_FORMATTER))
                          .append("#")
                          .append(med.getPrescriptionStatus())
                          .append("#")
                          .append(med.getDosage())
                          .append("&")
        );

        if (medicationsCSV.length() > 0) {
            medicationsCSV.setLength(medicationsCSV.length() - 1); // Remove trailing "&"
        }

        return String.join(",",
                prescriptionID,
                prescription.getPrescriptionDate().format(DATE_TIME_FORMATTER),
                medicationsCSV.toString()
        );
    }

    /**
     * Load prescriptions from a CSV file or create an empty file if it doesn't exist
     */
    public static void loadPrescriptionsFromCSV(String fileName) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();  // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;
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
            String prescriptionID = fields[0];
            LocalDateTime prescriptionDate = LocalDateTime.parse(fields[1], DATE_TIME_FORMATTER);

            String[] medicationsData = fields[2].split("&");
            List<PrescribedMedication> medications = new ArrayList<>();
            for (String medData : medicationsData) {
                String[] medFields = medData.split("#");
                if (medFields.length == 6) {
                    String medicineName = medFields[0];
                    int quantity = Integer.parseInt(medFields[1]);
                    LocalDateTime startDate = LocalDateTime.parse(medFields[2], DATE_TIME_FORMATTER);
                    LocalDateTime endDate = LocalDateTime.parse(medFields[3], DATE_TIME_FORMATTER);
                    PrescriptionStatus status = PrescriptionStatus.valueOf(medFields[4]);
                    String dosage = medFields[5];

                    Medicine medicine = new Medicine(medicineName, "UnknownID", "UnknownManufacturer", LocalDateTime.now().plusYears(1), 0);
                    medications.add(new PrescribedMedication(medicine, quantity, startDate, endDate, status, dosage));
                }
            }
            return new Prescription(medications, prescriptionDate);
        } catch (Exception e) {
            System.out.println("Error parsing prescription data: " + e.getMessage());
        }
        return null;
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
