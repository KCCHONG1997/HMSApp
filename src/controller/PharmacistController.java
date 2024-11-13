package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import enums.PrescriptionStatus;
import enums.ReplenishStatus;
import helper.Helper;
import model.AppointmentOutcomeRecord;
import model.Prescription;
import model.PrescribedMedication;
import model.Medicine;
import repository.AppointmentOutcomeRecordRepository;
import repository.MedicineRepository;
import repository.PrescribedMedicationRepository;

public class PharmacistController{

    /**
     * View Appointment Outcome Records to fulfill prescription orders from doctors.
     * This method retrieves and displays appointment outcome details for a given record ID.
     */
    public static void viewAppointmentOutcomeRecords() {
        System.out.print("Enter Patient ID to view appointment outcome records: ");
        String patientID = Helper.readString();

        if (AppointmentOutcomeRecordRepository.patientOutcomeRecords.containsKey(patientID)) {
            for (AppointmentOutcomeRecord record : AppointmentOutcomeRecordRepository.patientOutcomeRecords.get(patientID)) {
                System.out.println("Appointment Outcome for Record ID: " + record.getAppointmentOutcomeRecordID());
                System.out.println("Patient ID: " + record.getPatientID());
                System.out.println("Doctor ID: " + record.getDoctorID());
                System.out.println("Diagnosis ID: " + record.getDiagnosisID());
                System.out.println("Appointment Time: " + record.getAppointmentTime());
                System.out.println("Type of Service: " + record.getTypeOfService());
                System.out.println("Consultation Notes: " + record.getConsultationNotes());
                System.out.println("Appointment Outcome Status: " + record.getAppointmentOutcomeStatus());

                Prescription prescription = record.getPrescription();
                if (prescription != null && prescription.getMedications() != null) {
                    System.out.println("Prescription Details:");
                    for (PrescribedMedication medication : prescription.getMedications()) {
                        System.out.println("Medicine ID: " + medication.getMedicineID());
                        System.out.println("Quantity: " + medication.getMedicineQuantity());
                        System.out.println("Dosage: " + medication.getDosage());
                        System.out.println("Period (days): " + medication.getPeriodDays());
                        System.out.println("Prescription Status: " + medication.getPrescriptionStatus());
                        System.out.println();
                    }
                } else {
                    System.out.println("No prescription details available for this appointment outcome record.");
                }
                System.out.println("------------------------------------------------------");
            }
        } else {
            System.out.println("No appointment outcome records found for patient ID: " + patientID);
        }
    }

    /**
     * Update the status of a specific prescription in an appointment outcome record.
     * This method allows pharmacists to update the status of a prescribed medicine in the record.
     */
    public static void updatePrescriptionStatus() {
        System.out.print("Enter Patient ID for appointment outcome record: ");
        String patientID = Helper.readString();
        System.out.print("Enter Appointment Outcome Record ID to update: ");
        String appointmentOutcomeRecordID = Helper.readString();
        System.out.print("Enter Medicine ID to update: ");
        String medicineID = Helper.readString();
        System.out.print("Enter New Status (e.g., DISPENSED): ");
        PrescriptionStatus newStatus = PrescriptionStatus.valueOf(Helper.readString().toUpperCase());

        ArrayList<AppointmentOutcomeRecord> records = AppointmentOutcomeRecordRepository.patientOutcomeRecords.get(patientID);
        if (records != null) {
            boolean recordFound = false;
            for (AppointmentOutcomeRecord record : records) {
                if (record.getAppointmentOutcomeRecordID().equals(appointmentOutcomeRecordID)) {
                    recordFound = true;
                    boolean medicationFound = false;

                    for (PrescribedMedication medication : record.getPrescription().getMedications()) {
                        if (medication.getMedicineID().equals(medicineID)) {
                            medication.setPrescriptionStatus(newStatus);  // Update status
                            System.out.println("Updated status for medicine " + medicineID + " to " + newStatus);
                            medicationFound = true;
                            break;
                        }
                    }

                    if (!medicationFound) {
                        System.out.println("Error: Prescription for medicine ID " + medicineID + " not found in appointment record.");
                    }
                    // Save changes to repository after updating
                    PrescribedMedicationRepository.saveAlltoCSV();
                    AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
                    break;
                }
            }
            if (!recordFound) {
                System.out.println("Error: Appointment Outcome Record ID " + appointmentOutcomeRecordID + " not found for patient ID " + patientID);
            }
        } else {
            System.out.println("Error: No appointment outcome records found for patient ID " + patientID);
        }
    }

	/**
	 * Monitor inventory levels of all medicines, check for expired medicines, and allow removal.
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

	    // Step 1: Check for expired medicines
	    System.out.println("Expired Medicines:");
	    boolean expired = false;
	    LocalDateTime now = LocalDateTime.now();
	    for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
	        if (medicine.getExpiryDate().isBefore(now)) {
	            expired = true;
	            System.out.println("Medicine ID: " + medicine.getMedicineID());
	            System.out.println("Name: " + medicine.getName());
	            System.out.println("Stock Level: " + medicine.getInventoryStock());
	            System.out.println("Low Stock Level: " + medicine.getLowStockLevel());
	            System.out.println("Expiry Date: " + medicine.getExpiryDate() + " (Expired)");
	            System.out.println("Replenishment Status: " + medicine.getReplenishStatus());
	            System.out.println();
	        }
	    }

	    if (!expired) {
	        System.out.println("No medicines are expired.");
	    } else {
	        // Step 2: Prompt to remove expired medicines
	        int response;
	        do {
	            System.out.println("Do you want to remove expired medicines from stock?");
	            System.out.println("1. Yes");
	            System.out.println("2. No");
	            System.out.print("Enter your choice: ");
	            response = Helper.readInt("");
	            
	            switch (response) {
	                case 1:
	                    // Step 3: Remove expired medicines
	                    for (Medicine medicine : MedicineRepository.MEDICINES.values()) {
	                        if (medicine.getExpiryDate().isBefore(now)) {
	                            medicine.setInventoryStock(0);  // Set stock to zero for expired medicines
	                            System.out.println("Removed " + medicine.getName() + " from stock due to expiration.");
	                        }
	                    }
	                    MedicineRepository.saveAllMedicinesToCSV();
	                    break;
	                case 2:
	                    System.out.println("No expired medicines were removed.");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please enter 1 or 2.");
	            }
	        } while (response != 1 && response != 2);  // Continue until a valid choice is made
	    }

	    System.out.println("------------------------------------------------------");

	    // Step 4: Check for medicines below the low stock level
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
	}

    /**
     * Submit a replenishment request for a specific medicine when stock is low.
     */
    public static void submitReplenishmentRequests() {
    	int choice = 0;
    	do{
            System.out.println("Submit Replenishment Request:");
            System.out.println("1. Enter a Medicine ID to submit a replenishment request.");
            System.out.println("2. Check Replenishment Requests and Status.");
            System.out.println("3. Exit to Main Menu.");
            System.out.print("Choose an option: ");
            
            choice = Helper.readInt("");

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

                    // Proceed with replenishment request
                    System.out.print("Enter Requested Quantity: ");
                    int requestedQuantity = Helper.readInt("");

                    medicine.setReplenishStatus(ReplenishStatus.REQUESTED);
                    medicine.setReplenishRequestDate(LocalDateTime.now());
                    MedicineRepository.saveAllMedicinesToCSV();  // Save changes

                    System.out.println("Replenishment request submitted for " + medicine.getName() + " with quantity " + requestedQuantity);
                    break;
                    
                case 2:
                    System.out.println("Replenishment Requests and Status:");
                    boolean requestFound = false;

                    for (Medicine med : MedicineRepository.MEDICINES.values()) {
                        if (med.getReplenishStatus() != ReplenishStatus.NULL) {
                            requestFound = true;
                            System.out.println("Medicine ID: " + med.getMedicineID());
                            System.out.println("Name: " + med.getName());
                            System.out.println("Replenish Status: " + med.getReplenishStatus());
                            System.out.println("Replenishment Request Date: " + med.getReplenishRequestDate());
                            System.out.println();
                        }
                    }

                    if (!requestFound) {
                        System.out.println("No replenishment requests found.");
                    }
                    break;
                    
                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }while(choice != 3);
    }

    /**
     * Main method to test the PharmacistController functionality.
     */
    public static void main(String[] args) {
        MedicineRepository medicineRepository = new MedicineRepository();
		medicineRepository.loadFromCSV();
        // AppointmentRepository.loadAllAppointments();
        
        int choice = -1;
        
        while (choice != 0) { // 0 to exit
            System.out.println("Pharmacist Controller - Select an option:");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. Monitor Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("0. Exit");
            
            choice = Helper.readInt("");
            
            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
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
