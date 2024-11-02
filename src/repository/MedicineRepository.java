package repository;

import java.io.*;
import java.util.HashMap;
import model.Medicine;
import enums.ReplenishStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicineRepository {

    private static final String folder = "data";
    private static final String fileName = "medicines.csv";

    // Static data collection for medicines
    public static HashMap<String, Medicine> MEDICINES = new HashMap<>();

    // Save all medicines to CSV file
    public static void saveAllMedicines() {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine medicine : MEDICINES.values()) {
                writer.write(medicineToCSV(medicine));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medicines: " + e.getMessage());
        }
    }

    // Convert medicine object to a CSV line
    private static String medicineToCSV(Medicine medicine) {
        return String.join(",",
            medicine.getMedicineID(),
            medicine.getName(),
            medicine.getManufacturer(),
            medicine.getExpiryDate().toString(),
            String.valueOf(medicine.getInventoryStock()),
            String.valueOf(medicine.getLowStockLevel()),
            medicine.getReplenishStatus().toString(),
            medicine.getApprovedDate().toString()
        );
    }

    // Load medicines from a CSV file
    public static void loadAllMedicines() {
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
            return;  // No data to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;  // To track which line might cause an issue
            while ((line = reader.readLine()) != null) {
                Medicine medicine = csvToMedicine(line);
                if (medicine != null) {
                    MEDICINES.put(medicine.getMedicineID(), medicine);
                } else {
                    System.out.println("Warning: Failed to parse medicine at line " + lineNumber + " in " + fileName);
                }
                lineNumber++;
            }
            System.out.println("Successfully loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medicines: " + e.getMessage());
        }
    }

    // Convert a CSV line to a Medicine object
    private static Medicine csvToMedicine(String csv) {
        String[] fields = csv.split(","); // Change to "\t" if your CSV uses tabs
        if (fields.length < 8) {
            System.out.println("Warning: Not enough fields in CSV line: " + csv);
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; // Use ISO format to handle nanoseconds
            return new Medicine(
                fields[0],   // medicineID
                fields[1],   // name
                fields[2],   // manufacturer
                LocalDateTime.parse(fields[3], formatter),  // expiryDate
                Integer.parseInt(fields[4]),  // inventoryStock
                Integer.parseInt(fields[5]),  // lowStockLevel
                safeValueOf(fields[6]), // replenish status
                LocalDateTime.parse(fields[7], formatter)  // approvedDate
            );
        } catch (Exception e) {
            System.out.println("Error parsing medicine data: " + e.getMessage());
            return null;
        }
    }


    // Safe parsing for ReplenishStatus
    private static ReplenishStatus safeValueOf(String status) {
        try {
            return ReplenishStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid replenish status: " + status);
            return null; // or handle it as needed
        }
    }

    // Clear all medicine data
    public static boolean clearMedicineDatabase() {
        MEDICINES.clear();
        saveAllMedicines();
        return true;
    }
}
