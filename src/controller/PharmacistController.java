package controller;

import java.time.LocalDateTime;
import enums.ReplenishStatus;
import helper.Helper;
//import model.AppointmentOutcomeRecord;
import model.Medicine;
//import model.PrescriptionItem;
//import repository.AppointmentRepository;
import repository.MedicineRepository;

public class PharmacistController extends HMSPersonnelController{

//    /**
//     * View Appointment Outcome Records to fulfill prescription orders from doctors.
//     */
//    public static void viewAppointmentOutcomeRecords() {
//        System.out.print("Enter Appointment ID: ");
//        String appointmentID = Helper.readString();
//
//        AppointmentOutcomeRecord outcomeRecord = AppointmentRepository.getAppointmentOutcomeRecord(appointmentID);
//        if (outcomeRecord != null) {
//            System.out.println("Appointment Outcome for ID: " + appointmentID);
//            for (PrescriptionItem prescription : outcomeRecord.getPrescriptions()) {
//                System.out.println("Medicine: " + prescription.getMedicine().getName());
//                System.out.println("Quantity: " + prescription.getMedicineQuantity());
//                System.out.println("Status: " + prescription.getStatus());
//                System.out.println();
//            }
//        } else {
//            System.out.println("Error: Appointment Outcome Record not found.");
//        }
//    }

//    /**
//     * Update the status of a specific prescription in an appointment outcome record.
//     */
//    public static void updatePrescriptionStatus() {
//        System.out.print("Enter Appointment ID: ");
//        String appointmentID = Helper.readString();
//        System.out.print("Enter Medicine ID to update: ");
//        String medicineID = Helper.readString();
//        System.out.print("Enter New Status (e.g., DISPENSED): ");
//        String newStatus = Helper.readString().toUpperCase();
//
//        AppointmentOutcomeRecord outcomeRecord = AppointmentRepository.getAppointmentOutcomeRecord(appointmentID);
//        if (outcomeRecord != null) {
//            boolean found = false;
//            for (PrescriptionItem prescription : outcomeRecord.getPrescriptions()) {
//                if (prescription.getMedicine().getMedicineID().equals(medicineID)) {
//                    prescription.setStatus(newStatus);  // Update status
//                    System.out.println("Updated status for medicine " + medicineID + " to " + newStatus);
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                System.out.println("Error: Prescription for medicine ID " + medicineID + " not found in appointment record.");
//            }
//        } else {
//            System.out.println("Error: Appointment Outcome Record not found.");
//        }
//    }

    /**
     * Monitor inventory levels of all medicines to check for low stock.
     */
    public static void monitorInventory() {
        if (MedicineRepository.MEDICINES.isEmpty()) {
            System.out.println("No medicines available in the inventory.");
            return;
        }

        System.out.println("Full Inventory - Monitoring stock levels:");
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            System.out.println("Medicine ID: " + medicine.getMedicineID());
            System.out.println("Name: " + medicine.getName());
            System.out.println("Stock Level: " + medicine.getInventoryStock());
            System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
            System.out.println("Expiry Date: " + medicine.getExpiryDate());
            System.out.println();
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Medicines Below Low Stock Level:");
        boolean lowStockFound = false;

        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            if (medicine.getInventoryStock() < medicine.getLowStockLevel()) {
                lowStockFound = true;
                System.out.println("Medicine ID: " + medicine.getMedicineID());
                System.out.println("Name: " + medicine.getName());
                System.out.println("Stock Level: " + medicine.getInventoryStock() + " (Below threshold)");
                System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
                System.out.println("Expiry Date: " + medicine.getExpiryDate());
                System.out.println("Replenishment Status: " + medicine.getReplenishStatus());
                System.out.println("Replenishment Request Date: " + medicine.getReplenishRequestDate());
                System.out.println();
            }
        }

        if (!lowStockFound) {
            System.out.println("All medicines are above the low stock level.");
        }
        
        System.out.println("------------------------------------------------------");

        // List medicines nearing expiration
        System.out.println("Medicines Near Expiration (within 1 month):");
        boolean nearingExpiryFound = false;
        LocalDateTime now = LocalDateTime.now();
        for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
            // Check if the expiration date is within the next month
            if (medicine.getExpiryDate().isBefore(now.plusMonths(1))) {
                nearingExpiryFound = true;
                System.out.println("Medicine ID: " + medicine.getMedicineID());
                System.out.println("Name: " + medicine.getName());
                System.out.println("Stock Level: " + medicine.getInventoryStock());
                System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
                System.out.println("Expiry Date: " + medicine.getExpiryDate() + " (Expiring Soon)");
                System.out.println("Replenishment Status: " + medicine.getReplenishStatus());
                System.out.println("Replenishment Request Date: " + medicine.getReplenishRequestDate());
                System.out.println();
            }
        }
        if (!nearingExpiryFound) {
            System.out.println("No medicines are nearing expiration.");
        }
    }

    /**
     * Submit a replenishment request for a specific medicine when stock is low.
     */
    public static void submitReplenishmentRequests() {
    	int choice = 0;
    	do{
            System.out.println("Submit Replenishment Request:");
            System.out.println("1. Enter a Medicine ID to submit a replenishment request.");
            System.out.println("2. Exit to Main Menu.");
            System.out.print("Choose an option: ");
            
            choice = Helper.readInt();

            switch (choice) {
                case 1:
                    Medicine medicine = null;

                    // Keep asking for a valid Medicine ID until found
                    while (medicine == null) {
                        System.out.print("Enter Medicine ID for replenishment request: ");
                        String medicineID = Helper.readString();

                        medicine = MedicineRepository.MEDICINES.get(medicineID);
                        if (medicine == null) {
                            System.out.println("Error: Medicine with ID " + medicineID + " not found. Please enter a valid ID.");
                        }
                    }

                    // Check if the medicine needs replenishment based on stock level and expiration date
                    LocalDateTime now = LocalDateTime.now();
                    if (medicine.getInventoryStock() >= medicine.getLowStockLevel() && medicine.getExpiryDate().isAfter(now.plusMonths(1))) {
                        System.out.println("No replenishment needed for " + medicine.getName() + ". Stock is sufficient and expiration date is not near.");
                        return;
                    }

                    // Proceed with replenishment request
                    System.out.print("Enter Requested Quantity: ");
                    int requestedQuantity = Helper.readInt();

                    medicine.setReplenishStatus(ReplenishStatus.REQUESTED);
                    medicine.setReplenishRequestDate(LocalDateTime.now());
                    MedicineRepository.saveAllMedicines();  // Save changes

                    System.out.println("Replenishment request submitted for " + medicine.getName() + " with quantity " + requestedQuantity);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }while(choice != 2);
    }

    /**
     * Main method to test the PharmacistController functionality.
     */
    public static void main(String[] args) {
        MedicineRepository.loadAllMedicines();
        // AppointmentRepository.loadAllAppointments();
        
        int choice = -1;
        
        while (choice != 0) { // 0 to exit
            System.out.println("Pharmacist Controller - Select an option:");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. Monitor Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("0. Exit");
            
            choice = Helper.readInt();
            
            switch (choice) {
                case 1:
                    //viewAppointmentOutcomeRecords();
                    //break;
                case 2:
                    //updatePrescriptionStatus();
                    //break;
                case 3:
                    monitorInventory();
                    break;
                case 4:
                	submitReplenishmentRequests();
                    break;
                case 0:
                    System.out.println("Exiting Pharmacist Controller.");
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
