package repository;

import model.Medicine;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;

public class MedicineRepository extends Repository {
    private static final String folder = "data";
    private static final String fileName = "medicines.csv";
    private static Boolean isRepoLoaded = true;
    // Static data collection for Medicine records (key: medicineID)
    public static HashMap<String, Medicine> MEDICINES = new HashMap<>();

    /**
     * Specific loading logic for Medicine records from CSV.
     *
     * @return boolean indicating success or failure of the load operation
     */
    @Override
	public boolean loadFromCSV() {
        try {
            loadMedicinesFromCSV(fileName, MEDICINES);
            isRepoLoaded = true;
            return true;
        } catch (Exception e) {
            System.out.println("Error loading medicines repository: " + e.getMessage());
            return false;
        }
    }

    /**
     * Save all Medicine records to the CSV file
     */
    public static void saveAllMedicinesToCSV() {
        saveMedicinesToCSV(fileName, MEDICINES);
    }

    /**
     * Save a specific map of Medicine records to a CSV file
     */
    private static void saveMedicinesToCSV(String fileName, HashMap<String, Medicine> medicinesMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine medicine : medicinesMap.values()) {
                writer.write(medicineToCSV(medicine));
                writer.newLine();
            }
            System.out.println("Medicines successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving medicine data: " + e.getMessage());
        }
    }

    // Convert a Medicine object to a CSV line
    private static String medicineToCSV(Medicine medicine) {
        return String.join(",",
                medicine.getMedicineID(),
                medicine.getMedicineName(),
                medicine.getManufacturer(),
                medicine.getExpiryDate().toString(),          // Assuming expiryDate is LocalDateTime
                String.valueOf(medicine.getInventoryStock()),  // inventoryStock as integer
                medicine.getReplenishDate().toString()
        );
    }

    /**
     * Load Medicine records from a CSV file or create an empty file if it doesn't exist
     */
    private static void loadMedicinesFromCSV(String fileName, HashMap<String, Medicine> medicinesMap) {
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
                Medicine medicine = csvToMedicine(line);
                if (medicine != null) {
                    medicinesMap.put(medicine.getMedicineID(), medicine);
                }
            }
            System.out.println("Successfully loaded " + medicinesMap.size() + " medicines from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading medicine data: " + e.getMessage());
        }
    }

    // Convert a CSV line to a Medicine object
    private static Medicine csvToMedicine(String csv) {
        String[] fields = csv.split(",");
        try {
            return new Medicine(
                    fields[0],                       // medicineID
                    fields[1],                       // medicineName
                    fields[2],                       // manufacturer
                    LocalDateTime.parse(fields[3]),  // expiryDate (LocalDateTime)
                    Integer.parseInt(fields[4]),     // inventoryStock as integer
                    LocalDateTime.parse(fields[5])   // replenishDate
            );
        } catch (Exception e) {
            System.out.println("Error parsing medicine data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clear all medicine data and save an empty file
     */
    public static boolean clearMedicineDatabase() {
        MEDICINES.clear();
        saveAllMedicinesToCSV();
        isRepoLoaded = false;
        return true;
    }
}
