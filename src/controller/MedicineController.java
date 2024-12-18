package controller;

import java.time.LocalDateTime;
import model.Medicine;
import repository.MedicineRepository;
import enums.ReplenishStatus;

public class MedicineController {

	/**
	 * Adds a new medicine to the repository.
	 * Validates the medicine data before adding it to the repository.
	 * Saves the updated repository to a CSV file after a successful addition.
	 *
	 * @param medicine The {@link Medicine} object to be added.
	 * @return {@code true} if the medicine is successfully added; {@code false} otherwise.
	 */
    public static boolean addMedicine(Medicine medicine) {
        if (medicine == null || medicine.getMedicineID() == null) {
            System.out.println("Error: Invalid medicine data.");
            return false;
        }

        MedicineRepository.MEDICINES.put(medicine.getMedicineID(), medicine);
        MedicineRepository.saveAllMedicinesToCSV();
        System.out.println("Medicine added: " + medicine.getName());
        return true;
    }

    /**
     * Updates an existing medicine in the repository.
     * Validates the provided medicine ID and updated medicine data before updating.
     * Saves the updated repository to a CSV file after a successful update.
     *
     * @param medicineID      The ID of the medicine to update.
     * @param updatedMedicine The {@link Medicine} object with updated data.
     * @return {@code true} if the medicine is successfully updated; {@code false} otherwise.
     */
    public static boolean updateMedicine(String medicineID, Medicine updatedMedicine) {
        if (medicineID == null || medicineID.isEmpty() || updatedMedicine == null) {
            System.out.println("Error: Invalid update request.");
            return false;
        }

        if (MedicineRepository.MEDICINES.containsKey(medicineID)) {
            MedicineRepository.MEDICINES.put(medicineID, updatedMedicine);
            MedicineRepository.saveAllMedicinesToCSV();
            System.out.println("Medicine updated: " + updatedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found for update.");
            return false;
        }
    }

    /**
     * Removes a medicine from the repository by its ID.
     * Validates the medicine ID before attempting the removal.
     * Saves the updated repository to a CSV file after a successful removal.
     *
     * @param medicineID The ID of the medicine to remove.
     * @return {@code true} if the medicine is successfully removed; {@code false} otherwise.
     */
    public static boolean removeMedicine(String medicineID) {
        if (medicineID == null || medicineID.isEmpty()) {
            System.out.println("Error: Invalid medicine ID.");
            return false;
        }

        Medicine removedMedicine = MedicineRepository.MEDICINES.remove(medicineID);
        if (removedMedicine != null) {
            MedicineRepository.saveAllMedicinesToCSV();
            System.out.println("Medicine removed: " + removedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found with ID: " + medicineID);
            return false;
        }
    }

    /**
     * Retrieves a medicine from the repository by its ID.
     * Validates the medicine ID before attempting the retrieval.
     *
     * @param medicineID The unique ID of the medicine to retrieve.
     * @return The {@link Medicine} object if found; {@code null} otherwise.
     */
    public static Medicine getMedicineByUID(String medicineID) {
        if (medicineID == null || medicineID.isEmpty()) {
            System.out.println("Error: Invalid medicine ID.");
            return null;
        }

        Medicine medicine = MedicineRepository.MEDICINES.get(medicineID);
        if (medicine != null) {
            System.out.println("Medicine found: " + medicine.getName());
        } else {
            System.out.println("Error: Medicine not found with ID: " + medicineID);
        }
        return medicine;
    }

    /**
     * List all medicines in the repository.
     */
    public static void listAllMedicines() {
        if (MedicineRepository.MEDICINES.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }

        System.out.println("Listing all medicines:");
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            System.out.println("UID: " + medicine.getMedicineID());
            System.out.println("Name: " + medicine.getName());
            System.out.println("Manufacturer: " + medicine.getManufacturer());
            System.out.println("Expiry Date: " + medicine.getExpiryDate());
            System.out.println("Inventory Stock: " + medicine.getInventoryStock());
            System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
            System.out.println("Replenish Status: " + medicine.getReplenishStatus());
            System.out.println("Replenishment Request Date: " + medicine.getReplenishRequestDate());
            System.out.println("Approved Date: " + medicine.getApprovedDate());
            System.out.println();
        }
    }
    /**
     * Retrieves a medicine from the repository by its name.
     * Validates the medicine name before attempting the retrieval.
     *
     * @param medicineName The name of the medicine to retrieve.
     * @return The {@link Medicine} object if found; {@code null} otherwise.
     */
    public static Medicine getMedicineByName(String medicineName) {
        if (medicineName == null || medicineName.isEmpty()) {
            System.out.println("Error: Invalid medicine name.");
            return null;
        }

        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicineName.equalsIgnoreCase(medicine.getName())) {
                System.out.println("Medicine found: " + medicine.getName());
                return medicine;
            }
        }

        System.out.println("Error: Medicine not found with name: " + medicineName);
        return null;
    }

}
