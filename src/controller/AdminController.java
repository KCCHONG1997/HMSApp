package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import enums.ReplenishStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helper.Helper; // Ensure this import exists
import model.*; // Make sure these models are correctly imported
import repository.MedicineRepository;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AdminController extends HMSPersonnelController {
//	public static void listAllMedicine() {
//		MedicineController.listAllMedicines();
//	}
//	public static void addMedicine(Medicine medicine) {
//		
//		 MedicineController.addMedicine(medicine);
//	}
//	
//	public static void updateMedicine(String medicineID, Medicine medicine) {
//		 MedicineController.updateMedicine(medicineID, medicine);		
//	}
//	
//	public static void removeMedicine(String medicineID) {
//		 MedicineController.removeMedicine(medicineID);
//	}
	
	public static void approveReplenishRequest(String medicineID, Medicine medicine ) {
		
		 MedicineController.updateMedicine(medicineID, medicine);	
	}
    
    public static LocalDateTime readDate() {
    	LocalDateTime date = null;
        boolean valid = false;
        while (!valid) {
            String input = Helper.readString("");

            try {
                // Parse the input into LocalDateTime
                date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                valid = true; // Input is valid, exit the loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD HH:MM.");
            }
        }
        return date;
    }
    
    public static void printPersonnelDetails(HMSPersonnel personnel) {
        System.out.println("--------------------------------------------------");
        System.out.println("Personnel Details:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s: %s%n", "UID", personnel.getUID());
        System.out.printf("%-20s: %s%n", "Full Name", personnel.getFullName());
        System.out.printf("%-20s: %s%n", "ID Card", personnel.getIdCard());
        System.out.printf("%-20s: %s%n", "Username", personnel.getUsername());
        System.out.printf("%-20s: %s%n", "Email", personnel.getEmail());
        System.out.printf("%-20s: %s%n", "Phone No", personnel.getPhoneNo());
        System.out.printf("%-20s: %s%n", "Password", personnel.getPasswordHash());
        System.out.printf("%-20s: %s%n", "DOB", personnel.getDoB());
        System.out.printf("%-20s: %s%n", "Gender", personnel.getGender());
        System.out.printf("%-20s: %s%n", "Role", personnel.getRole());
        System.out.println("--------------------------------------------------\n");
    }

    public static void printDoctorDetails(Doctor doctor) {
        System.out.println("============== Doctor Details ==============");
        printPersonnelDetails(doctor);
        System.out.printf("%-20s: %s%n", "Specialty", doctor.getSpecialty());
        System.out.printf("%-20s: %s%n", "Medical License", doctor.getMedicalLicenseNumber());
        System.out.printf("%-20s: %s%n", "Date Joined", doctor.getDateJoin());
        System.out.printf("%-20s: %s years%n", "Experience", doctor.getYearsOfExperiences());
        System.out.println("===========================================\n");
        System.out.println();
    }

    public static void printPharmacistDetails(Pharmacist pharmacist) {
        System.out.println("========== Pharmacist Details ===========");
        printPersonnelDetails(pharmacist);
        System.out.printf("%-20s: %s%n", "License Number", pharmacist.getPharmacistLicenseNumber());
        System.out.printf("%-20s: %s%n", "Date of Employment", pharmacist.getDateOfEmployment());
        System.out.println("=========================================\n");
        System.out.println();
    }

    public static void listPersonnelByRole(PersonnelFileType type) {
        Map<String, ? extends HMSPersonnel> personnelMap = null;

        switch (type) {
            case DOCTORS:
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case PHARMACISTS:
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            default:
                System.out.println("Error: Unsupported personnel type.");
                return;
        }

        if (personnelMap != null && !personnelMap.isEmpty()) {
            System.out.println("\nListing all personnel of type: " + type);
            System.out.println("===========================================");
            for (HMSPersonnel personnel : personnelMap.values()) {
                if (type == PersonnelFileType.DOCTORS) {
                    printDoctorDetails((Doctor) personnel);
                } else if (type == PersonnelFileType.PHARMACISTS) {
                    printPharmacistDetails((Pharmacist) personnel);
                }
            }
            System.out.println("===========================================\n");
        } else {
            System.out.println("No personnel found for type: " + type);
        }
    }
    
    public static void listPersonnelByGender(String gender) {
        System.out.println("\nListing staff filtered by gender: " + gender);
        System.out.println("===========================================");
        
        for (Doctor doctor : PersonnelRepository.DOCTORS.values()) {
            if (doctor.getGender().equalsIgnoreCase(gender)) {
                printDoctorDetails(doctor);
            }
        }
        
        for (Pharmacist pharmacist : PersonnelRepository.PHARMACISTS.values()) {
            if (pharmacist.getGender().equalsIgnoreCase(gender)) {
                printPharmacistDetails(pharmacist);
            }
        }
        System.out.println("===========================================\n");
    }
    
   

    public static void listPersonnelByAge() {
        List<HMSPersonnel> combinedList = new ArrayList<>();
        
        combinedList.addAll(PersonnelRepository.DOCTORS.values());
        combinedList.addAll(PersonnelRepository.PHARMACISTS.values());
        
        // Sort the combined list by age from oldest to youngest
        combinedList.sort(Comparator.comparingInt(personnel -> calculateAge((HMSPersonnel) personnel)).reversed());

        System.out.println("\nListing all personnel sorted by age (oldest to youngest):");
        System.out.println("===========================================");
        for (HMSPersonnel personnel : combinedList) {
            if (personnel instanceof Doctor) {
                printDoctorDetails((Doctor) personnel);
            } else if (personnel instanceof Pharmacist) {
                printPharmacistDetails((Pharmacist) personnel);
            }
        }
        System.out.println("===========================================\n");
    }

   
    public static int calculateAge(HMSPersonnel personnel) {
        LocalDate birthDate = personnel.getDoB().toLocalDate(); // Ensure this returns LocalDate
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


    
}

	    
	

	
	

	