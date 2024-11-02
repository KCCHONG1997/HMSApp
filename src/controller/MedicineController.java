package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import enums.ReplenishStatus;
import model.HMSPersonnel;
import model.Medicine;
import repository.MedicineRepository;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class MedicineController {

    // Add a new medicine
    public static boolean addMedicine(Medicine medicine) {
        if (medicine == null || medicine.getMedicineID() == null) {
            System.out.println("Error: Invalid medicine data.");
            return false;
        }

        MedicineRepository.MEDICINES.put(medicine.getMedicineID(), medicine);
        MedicineRepository.saveAllMedicines();
        System.out.println("Medicine added: " + medicine.getName());
        return true;
    }

    // Update existing medicine details
    public static boolean updateMedicine(String medicineID, Medicine updatedMedicine) {
        if (medicineID == null || medicineID.isEmpty() || updatedMedicine == null) {
            System.out.println("Error: Invalid update request.");
            return false;
        }

        if (MedicineRepository.MEDICINES.containsKey(medicineID)) {
            MedicineRepository.MEDICINES.put(medicineID, updatedMedicine);
            MedicineRepository.saveAllMedicines();
            System.out.println("Medicine updated: " + updatedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found for update.");
            return false;
        }
    }

    // Remove medicine by ID
    public static boolean removeMedicine(String medicineID) {
        if (medicineID == null || medicineID.isEmpty()) {
            System.out.println("Error: Invalid medicine ID.");
            return false;
        }

        Medicine removedMedicine = MedicineRepository.MEDICINES.remove(medicineID);
        if (removedMedicine != null) {
            MedicineRepository.saveAllMedicines();
            System.out.println("Medicine removed: " + removedMedicine.getName());
            return true;
        } else {
            System.out.println("Error: Medicine not found with ID: " + medicineID);
            return false;
        }
    }

    // Retrieve medicine by ID
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
    
    //String medicineID, String name, String manufacturer, LocalDateTime expiryDate, int inventoryStock, int lowStockLevel, ReplenishStatus status, LocalDateTime approvedDate
    public static void listAllMedicine() {
        // Ensure medicines are loaded before listing
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
            System.out.println("Approved Date: " + medicine.getApprovedDate());
        }
    }

    
    
    public static void main(String[] args) {
    	MedicineRepository.loadAllMedicines(); 
    	listAllMedicine();
    	
    }
}
