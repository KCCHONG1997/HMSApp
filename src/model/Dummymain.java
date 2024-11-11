package model;

import java.time.LocalDateTime;
import controller.MedicineController;
import controller.PharmacistController;
import enums.ReplenishStatus;
import repository.MedicineRepository;
import repository.PersonnelRepository;
import repository.Repository;

public class Dummymain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			// 1. Create and add new Medicine objects to the repository 
			        Medicine med1 = new Medicine("M001", "Paracetamol", "ABC Pharmaceuticals", 
			                LocalDateTime.of(2025, 6, 30, 0, 0), 100, 20, ReplenishStatus.NULL, LocalDateTime.of(2023, 12, 1, 0, 0), LocalDateTime.of(0000, 01, 01, 0, 0)); 
			         
			        Medicine med2 = new Medicine("M002", "Ibuprofen", "XYZ Pharma", 
			                LocalDateTime.of(2024, 8, 15, 0, 0), 50, 10, ReplenishStatus.NULL, LocalDateTime.of(2023, 11, 20, 0, 0), LocalDateTime.of(0000, 01, 01, 0, 0)); 
			         
			        // Adding these medicines to the repository's in-memory collection 
			        MedicineRepository.MEDICINES.put(med1.getMedicineID(), med1); 
			        MedicineRepository.MEDICINES.put(med2.getMedicineID(), med2); 
			 
			        // 2. Save all medicines to CSV 
			        System.out.println("Saving medicines to CSV..."); 
			        MedicineRepository.saveAllMedicinesToCSV(); 
			 
			        // 3. Clear in-memory data to simulate fresh load 
			        System.out.println("Clearing in-memory medicine data..."); 
			        MedicineRepository.MEDICINES.clear(); 
			 
			        // 4. Load medicines from CSV to verify persistence 
			        System.out.println("Loading medicines from CSV..."); 
			        Repository.loadRepository(new MedicineRepository()); 
			 
			        // 5. Display loaded medicines to verify correct loading 
			        System.out.println("\nLoaded Medicines:"); 
			        MedicineController.listAllMedicines();			 
			        System.out.println("---- Medicine Repository Test Complete ----");
			        
			        // 1. Create and add new Pharmacist objects to the repository 
			        Pharmacist pharm1 = new Pharmacist("P001", "Alice Tan", "S1234567A", "alice.tan", 
			                "alice@example.com", "12345678", "passwordHash1", LocalDateTime.of(1985, 5, 15, 0, 0), 
			                "Female", "Pharmacist", "PLN123", LocalDateTime.of(2010, 6, 1, 0, 0));

			        Pharmacist pharm2 = new Pharmacist("P002", "Bob Lim", "S2345678B", "bob.lim", 
			                "bob@example.com", "23456789", "passwordHash2", LocalDateTime.of(1982, 8, 20, 0, 0), 
			                "Male", "Pharmacist", "PLN456", LocalDateTime.of(2012, 3, 12, 0, 0));

			        Pharmacist pharm3 = new Pharmacist("P003", "Charlie Wong", "S3456789C", "charlie.wong", 
			                "charlie@example.com", "34567890", "passwordHash3", LocalDateTime.of(1990, 11, 25, 0, 0), 
			                "Male", "Pharmacist", "PLN789", LocalDateTime.of(2015, 11, 5, 0, 0));

			        // Add these pharmacists to the repository's in-memory collection
			        PersonnelRepository.PHARMACISTS.put(pharm1.getUID(), pharm1);
			        PersonnelRepository.PHARMACISTS.put(pharm2.getUID(), pharm2);
			        PersonnelRepository.PHARMACISTS.put(pharm3.getUID(), pharm3);

			        // Step 2: Save all personnel data, including pharmacists, to CSV
			        System.out.println("Saving pharmacists to CSV...");
			        PersonnelRepository.saveAllPersonnelFiles();

			        // Step 3: Clear in-memory pharmacist data to simulate a fresh load
			        System.out.println("Clearing in-memory pharmacist data...");
			        PersonnelRepository.PHARMACISTS.clear();

			        // Step 4: Load pharmacists from CSV to verify persistence
			        System.out.println("Loading pharmacists from CSV...");
			        PersonnelRepository personnelRepository = new PersonnelRepository();
					personnelRepository.loadFromCSV();
			        
			        System.out.println("---- Pharmacist Repository Test Complete ----"); 	       
	}

}
