package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Period;
import helper.Helper; // Ensure this import exists
import model.*; // Make sure these models are correctly imported
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AdminController extends HMSPersonnelController {
    
    public static void viewAndManageStaff() {
        printBreadCrumbs("HMS App UI > Admin Dashboard > View and Manage Hospital Staff");
        System.out.println("1. View Staff");
        System.out.println("2. Manage Staff");
        int choice = Helper.readInt();
        
        if (choice == 1) {
            viewAllStaff(); // Call viewStaff if choice is 1
        } else if (choice == 2) {
            // Logic for managing staff can go here
        }
    }
    
    public static void viewAllStaff() {
        // Implement logic for viewing staff
        listAllPersonnel(PersonnelFileType.DOCTORS);
        listAllPersonnel(PersonnelFileType.PHARMACISTS);
    }
    
    public static void printDoctorDetails(Doctor doctor) {
        System.out.println("UID: " + doctor.getUID());
        System.out.println("Full Name: " + doctor.getFullName());
        System.out.println("ID Card: " + doctor.getIdCard());
        System.out.println("Username: " + doctor.getUsername());
        System.out.println("Email: " + doctor.getEmail());
        System.out.println("Phone No: " + doctor.getPhoneNo());
        System.out.println("Password: " + doctor.getPasswordHash());
        System.out.println("DOB: " + doctor.getDoB());
        System.out.println("Gender: " + doctor.getGender());
        System.out.println("Role: " + doctor.getRole());
        System.out.println("Speciality: " + doctor.getSpecialty());
        System.out.println("Medical License Number: " + doctor.getMedicalLicenseNumber());
        System.out.println("Date Join: " + doctor.getDateJoin());
        System.out.println("Years of Experiences: " + doctor.getYearsOfExperiences());
    }
    
    public static void printPharmacistDetails(Pharmacist pharmacist) {
        System.out.println("UID: " + pharmacist.getUID());
        System.out.println("Full Name: " + pharmacist.getFullName());
        System.out.println("ID Card: " + pharmacist.getIdCard());
        System.out.println("Username: " + pharmacist.getUsername());
        System.out.println("Email: " + pharmacist.getEmail());
        System.out.println("Phone No: " + pharmacist.getPhoneNo());
        System.out.println("Password: " + pharmacist.getPasswordHash());
        System.out.println("DOB: " + pharmacist.getDoB());
        System.out.println("Gender: " + pharmacist.getGender());
        System.out.println("Role: " + pharmacist.getRole());
        System.out.println("Pharmacist License Number: " + pharmacist.getPharmacistLicenseNumber());
        System.out.println("Date of Employment: " + pharmacist.getDateOfEmployment());
    }
    
    public static void listAllPersonnel(PersonnelFileType type) {
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
    
    private static void listAllPersonnelByGender(String gender) {
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
    
   

    public static void listAllPersonnelByAge() {
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

    // Method to calculate age from HMSPersonnel
    public static int calculateAge(HMSPersonnel personnel) {
        LocalDate birthDate = personnel.getDoB().toLocalDate(); // Ensure this returns LocalDate
        return Period.between(birthDate, LocalDate.now()).getYears();
    }



    
  
   

    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


    public static void printBreadCrumbs(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + breadcrumb + spaces + "║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    
    public static int getUserChoice(int maxChoice) {
        try {
            int choice = Integer.parseInt(System.console().readLine());
            if (choice < 1 || choice > maxChoice) {
                handleInvalidInput();
                return -1;
            }
            return choice;
        } catch (NumberFormatException e) {
            handleInvalidInput();
            return -1;
        }
    }
    
    public static void handleInvalidInput() {
        System.out.println("Invalid input. Please try again.");
    }
    
    public static void main(String[] args) {
        PersonnelRepository.loadAllPersonnelFiles();
        listAllPersonnelByAge();
       
    }
}

	    
	

	
	

	