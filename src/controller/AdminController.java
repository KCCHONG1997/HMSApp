package controller;

import java.util.Map;

import model.*;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AdminController extends HMSPersonnelController {
	
	public static void viewAndManageStaff() {
	    	printBreadCrumbs("HMS App UI > Admin Dashboard> View and Manage Hospital Staff");
	    	System.out.println("1. View Staff");
	    	System.out.println("2. Manage Staff");
	    	int choice = getUserChoice(2);
	    	if(choice == 1);
	    	else;
	    	return;
	    }
	    
	public void viewStaff() {
	    
	    }
	    
	    
	public static void listAllDoctors() {
		PersonnelRepository.loadAllPersonnelFiles();
        Map<String, Doctor> doctorsMap = PersonnelRepository.DOCTORS;

        // Check if there are any doctors in the map
        if (doctorsMap != null && !doctorsMap.isEmpty()) {
            System.out.println("Listing all doctors:");

            for (Doctor doctor : doctorsMap.values()) {
                System.out.println("UID: " + doctor.getUID());
                System.out.println("Name: " + doctor.getFullName());
                System.out.println("Specialty: " + doctor.getSpecialty());
                System.out.println("Medical License Number: " + doctor.getMedicalLicenseNumber());
                System.out.println("Years of Experience: " + doctor.getYearsOfExperiences());
                System.out.println("---------------------------------");
            }
        } else {
            System.out.println("No doctors found.");
        }
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
	        
	        listAllDoctors();
	    }
	    
	}

