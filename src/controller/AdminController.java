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
	public static void listAllMedicine() {
		MedicineController.listAllMedicines();
	}
	public static void addMedicine(Medicine medicine) {
		
		 MedicineController.addMedicine(medicine);
	}
	
	public static void updateMedicine(String medicineID, Medicine medicine) {
		 MedicineController.updateMedicine(medicineID, medicine);		
	}
	
	public static void removeMedicine(String medicineID) {
		 MedicineController.removeMedicine(medicineID);
	}
	
	public static void approveReplenishRequest(String medicineID, Medicine medicine ) {
		
		 MedicineController.updateMedicine(medicineID, medicine);	
	}
    
    public static LocalDateTime readDate() {
    	LocalDateTime date = null;
        boolean valid = false;
        while (!valid) {
            String input = Helper.readString();

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
    	System.out.println("UID: " + personnel.getUID());
        System.out.println("Full Name: " + personnel.getFullName());
        System.out.println("ID Card: " + personnel.getIdCard());
        System.out.println("Username: " + personnel.getUsername());
        System.out.println("Email: " + personnel.getEmail());
        System.out.println("Phone No: " + personnel.getPhoneNo());
        System.out.println("Password: " + personnel.getPasswordHash());
        System.out.println("DOB: " + personnel.getDoB());
        System.out.println("Gender: " + personnel.getGender());
        System.out.println("Role: " + personnel.getRole());
    }
    
    public static void printDoctorDetails(Doctor doctor) {
    	printPersonnelDetails(doctor);
        System.out.println("Speciality: " + doctor.getSpecialty());
        System.out.println("Medical License Number: " + doctor.getMedicalLicenseNumber());
        System.out.println("Date Join: " + doctor.getDateJoin());
        System.out.println("Years of Experiences: " + doctor.getYearsOfExperiences());
    }
    
    public static void printPharmacistDetails(Pharmacist pharmacist) {
    	printPersonnelDetails(pharmacist);
        System.out.println("Pharmacist License Number: " + pharmacist.getPharmacistLicenseNumber());
        System.out.println("Date of Employment: " + pharmacist.getDateOfEmployment());
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
            System.out.println("Listing all personnel of type: " + type);
            for (HMSPersonnel personnel : personnelMap.values()) {
                if (type == PersonnelFileType.DOCTORS) {
                    printDoctorDetails((Doctor) personnel);
                } else if (type == PersonnelFileType.PHARMACISTS) {
                    printPharmacistDetails((Pharmacist) personnel);
                }
            }
        } else {
            System.out.println("No personnel found for type: " + type);
        }
    }
    
    public static void listPersonnelByGender(String gender) {
        System.out.println("Listing staff filtered by gender: " + gender);
        
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
    }
    
   

    public static void listPersonnelByAge() {
        List<HMSPersonnel> combinedList = new ArrayList<>();
        
        combinedList.addAll(PersonnelRepository.DOCTORS.values());
        combinedList.addAll(PersonnelRepository.PHARMACISTS.values());
        
        // Sort the combined list by age from oldest to youngest
        combinedList.sort(Comparator.comparingInt(personnel -> calculateAge((HMSPersonnel) personnel)).reversed());

        System.out.println("Listing all personnel sorted by age (oldest to youngest):");
        for (HMSPersonnel personnel : combinedList) {
            if (personnel instanceof Doctor) {
                printDoctorDetails((Doctor) personnel);
            } else if (personnel instanceof Pharmacist) {
                printPharmacistDetails((Pharmacist) personnel);
            }
        }
    }

   
    public static int calculateAge(HMSPersonnel personnel) {
        LocalDate birthDate = personnel.getDoB().toLocalDate(); // Ensure this returns LocalDate
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


}

	    
	

	
	

	