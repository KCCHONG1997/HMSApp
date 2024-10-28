package controller;

import java.util.Map;
import helper.*;
import model.*;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AdminController extends HMSPersonnelController {
	
	public static void viewAndManageStaff() {
	    	printBreadCrumbs("HMS App UI > Admin Dashboard> View and Manage Hospital Staff");
	    	System.out.println("1. View Staff");
	    	System.out.println("2. Manage Staff");
	    	int choice = Helper.readInt();
	    	if(choice == 1);
	    	else;
	    	return;
	    }
	    
	public void viewStaff() {
	    
	    }
	    
	public static void listAllPersonnel(PersonnelFileType type) {Map<String, ? extends HMSPersonnel> personnelMap = null;

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

    if (personnelMap != null && !personnelMap.isEmpty() && type == PersonnelFileType.DOCTORS) {
        System.out.println("Listing all personnel of type: " + type);
        for (HMSPersonnel personnel : personnelMap.values()) {
        	Doctor d = (Doctor) personnel;
        	Doctor doctor = d;
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
            System.out.println("Date Join: " + doctor.getDateJoin() );
            System.out.println("Years of Experiences: " + doctor.getYearsOfExperiences());
            
        }
    }
    else if (personnelMap != null && !personnelMap.isEmpty() && type == PersonnelFileType.PHARMACISTS) {
        System.out.println("Listing all personnel of type: " + type);
        for (HMSPersonnel personnel : personnelMap.values()) {
        	Pharmacist p = (Pharmacist) personnel;
        	Pharmacist pharmacist = p;
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
            System.out.println("Speciality: " + pharmacist.getPharmacistLicenseNumber());
            System.out.println("Medical License Number: " + pharmacist.getDateOfEmployment());
         
        }
    }
    else {
        System.out.println("No personnel found for type: " + type);
    }}

	public static void printBreadCrumbs(String breadcrumb) {
	        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
	        System.out.println(
	                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
	        System.out.println("║ " + breadcrumb + spaces + "║");
	        System.out.println(
	                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
	    
	   
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
	        listAllPersonnel(PersonnelFileType.DOCTORS);
	    }
	    
	}

	
	

	   /*public static void viewAndManageStaff() {
	        printBreadCrumbs("HMS App UI > Admin Dashboard > View and Manage Hospital Staff");
	        System.out.println("1. View Staff");
	        System.out.println("2. Manage Staff");
	        System.out.println("3. Filter Staff");
	        int choice = getUserChoice(3);
	        
	        switch (choice) {
	            case 1:
	                listAllPersonnel(PersonnelFileType.DOCTORS);
	                listAllPersonnel(PersonnelFileType.PHARMACISTS);
	                break;
	            case 2:
	                manageStaff();
	                break;
	            case 3:
	                filterStaff();
	                break;
	            default:
	                System.out.println("Invalid choice.");
	                break;
	        }
	    }

	    private static void manageStaff() {
	        printBreadCrumbs("Manage Staff");
	        System.out.println("1. Add Staff");
	        System.out.println("2. Update Staff");
	        System.out.println("3. Remove Staff");
	        int choice = getUserChoice(3);

	        switch (choice) {
	            case 1:
	                addStaff();
	                break;
	            case 2:
	                updateStaff();
	                break;
	            case 3:
	                removeStaff();
	                break;
	            default:
	                System.out.println("Invalid choice.");
	                break;
	        }
	    }

	    private static void addStaff() {
	      
	        Doctor newDoctor = new Doctor();
	        if (addPersonnel(newDoctor)) {
	            System.out.println("Doctor added successfully.");
	        } else {
	            System.out.println("Failed to add Doctor.");
	        }
	    }

	    private static void updateStaff() {
	        
	        String UID ;
	        Doctor updatedDoctor = new Doctor();
	        if (updatePersonnel(UID, updatedDoctor)) {
	            System.out.println("Doctor updated successfully.");
	        } else {
	            System.out.println("Failed to update Doctor.");
	        }
	    }

	    private static void removeStaff() {
	        String UID ;
	        if (removePersonnel(UID, PersonnelFileType.DOCTORS)) {
	            System.out.println("Doctor removed successfully.");
	        } else {
	            System.out.println("Failed to remove Doctor.");
	        }
	    }

	    private static void filterStaff() {
	        
	        String gender ;
	        listFilteredPersonnel(gender);
	    }

	    private static void listFilteredPersonnel(String gender) {
	        System.out.println("Listing staff filtered by gender: " + gender);
	        for (Doctor doctor : PersonnelRepository.DOCTORS.values()) {
	            if (doctor.getGender().equalsIgnoreCase(gender)) {
	                System.out.println("UID: " + doctor.getUID() + ", Name: " + doctor.getFullName());
	            }
	        }
	        
	    }

	    public static void main(String[] args) {
	        PersonnelRepository.loadAllPersonnelFiles();
	        viewAndManageStaff();
	    }
	}
*/
