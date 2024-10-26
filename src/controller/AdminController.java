package controller;

import java.util.Map;

import model.HMSPersonnel;
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
	    
	    
	@Override
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
                System.out.println("UID: " + personnel.getUID() + ", Name: " + personnel.getFullName());
                System.out.println("UID: " + personnel.getUID() + ", Name: " + personnel.getFullName());
            }
        } else {
            System.out.println("No personnel found for type: " + type);
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
	    
	}

