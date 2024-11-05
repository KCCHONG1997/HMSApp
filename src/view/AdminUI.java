package view;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import HMSApp.HMSMain;
import model.Admin;
import model.Doctor;
import model.HMSPersonnel;
import model.Medicine;
import model.Pharmacist;
import repository.MedicineRepository;
import repository.PersonnelFileType;
import repository.PersonnelRepository;
import repository.Repository;
import controller.*;
import enums.ReplenishStatus;
import helper.Helper;

public class AdminUI extends MainUI {
	private Admin admin;
    public AdminUI(Admin admin) {
        this.admin = admin;
    }
    
	@Override
    protected void printChoice() {
		System.out.printf("Welcome! Admin --- %s ---\n", admin.getFullName());
		printBreadCrumbs("HMS App UI > Admin Dashboard");
        System.out.println("Administrator Menu:");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory ");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
    
	public void start() {
		showAdminDashboard();
	}
    public void showAdminDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
        	printChoice();
            choice = Helper.readInt();
            switch(choice) {
                case 1: 
                	viewAndManageStaff();
                    break;
                case 2: 
                    // Code for updating patient medical records
                    break;
                case 3: 
                    // Code for viewing personal schedule
                    break;
                case 4: 
                    // Code for setting availability for appointments
                    break;
                case 5: 
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        } while(choice != 8);
        
        sc.close(); // Close the Scanner
    }
    
    public static void viewAndManageStaff() {
	    System.out.println("1. View Staff By Role");
	    System.out.println("2. View Staff By Gender");
	    System.out.println("3. View Staff By Age");
	    System.out.println("4. Add Doctor");
	    System.out.println("5. Add Pharmacist");
	    System.out.println("6. Update Doctor");
	    System.out.println("7. Update Pharmacist");
	    System.out.println("8. Remove Doctor");
	    System.out.println("9. Remove Pharmacist");
	    int choice = Helper.readInt();

	    switch (choice) {
	        case 1:
	            AdminController.listPersonnelByRole(PersonnelFileType.DOCTORS);
	            AdminController.listPersonnelByRole(PersonnelFileType.PHARMACISTS);
	            break;
	        case 2:
	            AdminController.listPersonnelByGender("Male");
	            AdminController.listPersonnelByGender("Female");
	            break;
	        case 3:
	            AdminController.listPersonnelByAge();
	            break;
	        case 4:
	        	addPersonnel("Doctor");
	            break;
	        case 5:
	        	addPersonnel("Pharmacist");
	            break;
	        case 6:
	            updatePersonnel("Doctor");
	            break;
	        case 7:
	            updatePersonnel("Pharmacist");
	            break;
	        case 8:
	        	removePersonnel("Doctor");
	            break;
	        case 9:
	        	removePersonnel("Pharmacist");
	            break;
	        default:
	            System.out.println("Error: Invalid choice. Please select a valid option.");
	            break;
	    }
	}
    
    public static void addPersonnel(String role) {
        System.out.println("Enter Full Name: " );
        String fullName = Helper.readString();
        System.out.println("Enter ID Card: " );
        String idCard = Helper.readString();
        System.out.println("Enter Username: " );
        String username = Helper.readString();
        System.out.println("Enter Email: "  );
        String email = Helper.readString();
        System.out.println("Enter Phone No: " );
        String phoneNo = Helper.readString();
        System.out.println("Enter DOB (YYYY-MM-DD HH:MM): : " );
        LocalDateTime DoB = readDate();
        System.out.println("Enter Gender: " );
        String gender = Helper.readString();
        if (role == "Doctor" ) {
        	 System.out.println("Enter Speciality: " );
             String specialty = Helper.readString();
             System.out.println("Enter Medical License Number: " );
             String medicalLicenseNumber = Helper.readString();
             System.out.println("Enter Date Join: (YYYY-MM-DD HH:MM): : " );
             LocalDateTime dateJoin = readDate();
             System.out.println("Enter Years of Experiences: " );
             int yearsOfExperiences = Helper.readInt();
             Doctor doctor = new Doctor(fullName,idCard, username, email, phoneNo, "defaultPassword", 
            		 DoB, gender, specialty, medicalLicenseNumber, dateJoin, yearsOfExperiences);
             HMSPersonnel personnel = (HMSPersonnel) doctor;
             AdminController.addPersonnel(personnel);
        }      
        
        else {
        	System.out.println("Enter Pharmacist License Number: " );
        	String pharmacistLicenseNumber = Helper.readString();
        	System.out.println("Enter Date Of Employment: " );
        	LocalDateTime dateOfEmployment = readDate();
        	Pharmacist pharmacist = new Pharmacist(fullName,idCard, username, email, phoneNo, "defaultPassword", DoB, gender,pharmacistLicenseNumber , dateOfEmployment);
        	HMSPersonnel personnel = (HMSPersonnel) pharmacist;
        	AdminController.addPersonnel(personnel);
        }
    }
    public static void updatePersonnel(String role) {
    	System.out.println("Enter UID: " );
    	String UID = Helper.readString();
    	Doctor doctor = (Doctor) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.DOCTORS);
    	Pharmacist pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.PHARMACISTS);
        if (doctor == null && pharmacist == null) {
       	System.out.println("Doctor does not exist!");
       	return ;
        }
        System.out.println("Enter New Username: " );
        String username = Helper.readString();
        System.out.println("Enter New Email: "  );
        String email = Helper.readString();
        System.out.println("Enter New Phone No: " );
        String phoneNo = Helper.readString();
        System.out.println("Enter New Password " );
        String hashedPassword = Helper.readString();
        if (role == "Doctor" ) {
        	
        	 System.out.println("Enter New Speciality: " );
             String specialty = Helper.readString();
             System.out.println("Enter New Medical License Number: " );
             String medicalLicenseNumber = Helper.readString();
             System.out.println("Enter New Years of Experiences: " );
             int yearsOfExperiences = Helper.readInt();
             
             doctor.setUsername(username);
             doctor.setEmail(email);
             doctor.setPhoneNo(phoneNo);
             doctor.setPasswordHash(hashedPassword);
             doctor.setSpecialty(specialty);
             doctor.setMedicalLicenseNumber(medicalLicenseNumber);
             doctor.setYearsOfExperiences(yearsOfExperiences);
             AdminController.updatePersonnel(doctor.getUID(), doctor);
        }      
        
        else {
            System.out.println("Enter New Pharmacist License Number: " );
            String pharmacistLicenseNumber = Helper.readString();
            pharmacist.setUsername(username);
            pharmacist.setEmail(email);
            pharmacist.setPhoneNo(phoneNo);
            pharmacist.setPasswordHash(hashedPassword);
            pharmacist.setPharmacistLicenseNumber(pharmacistLicenseNumber);
            AdminController.updatePersonnel(pharmacist.getUID(), pharmacist);
        }
    }
    public static void removePersonnel(String role) {
    	if(role == "Doctor") {
    		String uidDoctor = Helper.readString();
            AdminController.removePersonnel(uidDoctor, PersonnelFileType.DOCTORS);
    	}
    	else {
    		String uidPharmacist = Helper.readString();
            AdminController.removePersonnel(uidPharmacist, PersonnelFileType.PHARMACISTS);
    	}
    }  
    
    public static void viewAndManageMedicationInventory() {
		System.out.println("1. View All Medicine");
	    System.out.println("2. Add Medicine");
	    System.out.println("3. Update Medicine");
	    System.out.println("4. Remove Medicine");
	    int choice = Helper.readInt();
	    switch(choice) {
	    	case 1: AdminController.listAllMedicine();
	    	break;
	    	case 2: addMedicine();
	    	break;
	    	case 3: updateMedicine();
	    	break;
	    	case 4: removeMedicine();
	    	break;
	    	default: System.out.println("Error: Invalid choice. Please select a valid option.");
	    	break;
	    }
	}
    public static void addMedicine() {
    	 System.out.print("Enter Medicine ID: ");
		 String medicineID = Helper.readString();	    
		 System.out.print("Enter Medicine Name: ");
		 String name = Helper.readString();		    
		 System.out.print("Enter Manufacturer: ");
		 String manufacturer = Helper.readString();
		 System.out.print("Enter Expiry Date (YYYY-MM-DDTHH:MM:): ");
		 LocalDateTime expiryDate = readDate();
		 System.out.print("Enter Inventory Stock: ");
		 int inventoryStock = Helper.readInt();		    
		 System.out.print("Enter Low Stock Level: ");
		 int lowStockLevel = Helper.readInt();  
		 System.out.print("Enter Replenish Status: ");
		 ReplenishStatus status = ReplenishStatus.NULL;
		 String dateTimeString = "0001-01-01 00:00";
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	     LocalDateTime date = LocalDateTime.parse(dateTimeString, formatter);
		 Medicine medicine = new Medicine(medicineID, name, manufacturer, expiryDate, 
		                                      inventoryStock, lowStockLevel, status, date, date);
		 AdminController.addMedicine(medicine);
    }
    public static void updateMedicine() {
		 System.out.print("Enter Medicine ID: ");
		 String medicineID = Helper.readString();
		 Medicine medicine = MedicineController.getMedicineByUID(medicineID);	    
		 System.out.print("Enter New Manufacturer: ");
		 String manufacturer = Helper.readString();
		 medicine.setManufacturer(manufacturer);
		 System.out.print("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
		 LocalDateTime expiryDate = readDate();
		 medicine.setExpiryDate(expiryDate);
		 System.out.print("Enter New Inventory Stock: ");
		 int inventoryStock = Helper.readInt();
		 medicine.setInventoryStock(inventoryStock);
		 System.out.print("Enter New Low Stock Level: ");
		 int lowStockLevel = Helper.readInt();
		 medicine.setLowStockLevel(lowStockLevel);
		 AdminController.updateMedicine(medicineID, medicine);		
	}
    public static void removeMedicine() {
		 System.out.print("Enter Medicine ID: ");
		 String medicineID = Helper.readString();	
		 AdminController.removeMedicine(medicineID);
	}
	
    public static void approveReplenishRequest() {
    	 System.out.print("Enter Medicine ID: ");
		 String medicineID = Helper.readString();
		 Medicine medicine = MedicineController.getMedicineByUID(medicineID);	    
		 System.out.print("Enter New Manufacturer: ");
		 String manufacturer = Helper.readString();
		 medicine.setManufacturer(manufacturer);
		 System.out.print("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
		 LocalDateTime expiryDate = readDate();
		 medicine.setExpiryDate(expiryDate);
		 System.out.print("Enter New Inventory Stock: ");
		 int inventoryStock = Helper.readInt();
		 medicine.setInventoryStock(inventoryStock);
		 System.out.print("Enter New Low Stock Level: ");
		 int lowStockLevel = Helper.readInt();
		 medicine.setLowStockLevel(lowStockLevel);
		 medicine.setReplenishStatus(ReplenishStatus.APPROVED);
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime currentDateTime = LocalDateTime.now();
		 String formattedDateTime = currentDateTime.format(formatter);
		 LocalDateTime approvedDate = LocalDateTime.parse(formattedDateTime, formatter);
		 medicine.setApprovedDate(approvedDate);
		 AdminController.approveReplenishRequest(medicineID, medicine);
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
    
    public static void main(String[] args) {
    	Repository.loadRepository(new PersonnelRepository());
		Repository.loadRepository(new MedicineRepository());
		approveReplenishRequest();
    	
    }
    
}
   
