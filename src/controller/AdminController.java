package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helper.Helper; // Ensure this import exists
import model.*; // Make sure these models are correctly imported
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AdminController extends HMSPersonnelController {
    
    public static void viewAndManageStaff() {
        printBreadCrumbs("HMS App UI > Admin Dashboard > View and Manage Hospital Staff");
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
        
        if (choice == 1) {
        	listPersonnelByRole(PersonnelFileType.DOCTORS);
        	listPersonnelByRole(PersonnelFileType.PHARMACISTS);
        } else if (choice == 2) {
        	listPersonnelByGender("Male");
        	listPersonnelByGender("Female");
        } else if (choice == 3) {
        	listPersonnelByAge();
        } else if (choice == 4) {
        	HMSPersonnel personnel = addPersonnel("Doctor");
        	Doctor doctor = (Doctor) personnel;
        	HMSPersonnelController.addPersonnel(doctor);
        } else if (choice == 5) {
        	HMSPersonnel personnel = addPersonnel("Pharmacist");
        	Pharmacist pharmacist = (Pharmacist) personnel;
        	HMSPersonnelController.addPersonnel(pharmacist);
        } else if (choice == 6) {
        	HMSPersonnel personnel = updatePersonnel("Doctor");
        	Doctor doctor = (Doctor) personnel;
        	HMSPersonnelController.updatePersonnel(doctor.getUID(), doctor);
        } else if (choice == 7) {
        	HMSPersonnel personnel = addPersonnel("Pharmacist");
        	Pharmacist pharmacist = (Pharmacist) personnel;
        	HMSPersonnelController.updatePersonnel(pharmacist.getUID(),pharmacist);
        } else if (choice == 8) {
        	String uid = Helper.readString();
        	HMSPersonnelController.removePersonnel(uid, PersonnelFileType.DOCTORS);
        } else if (choice == 9) {
        	String uid = Helper.readString();
        	HMSPersonnelController.removePersonnel(uid, PersonnelFileType.PHARMACISTS);
        }     
    }
    
    public static HMSPersonnel updatePersonnel(String role) {
    	System.out.println("Enter UID: " );
    	String UID = Helper.readString();
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
             Doctor doctor = (Doctor) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.DOCTORS);
             if (doctor == null) {
            	 System.out.println("Doctor does not exist!");
            	 return doctor;
             }
             doctor.setUsername(username);
             doctor.setEmail(email);
             doctor.setPhoneNo(phoneNo);
             doctor.setPasswordHash(hashedPassword);
             doctor.setSpecialty(specialty);
             doctor.setMedicalLicenseNumber(medicalLicenseNumber);
             doctor.setYearsOfExperiences(yearsOfExperiences);
             return doctor;
        }      
        
        else {
            System.out.println("Enter New Pharmacist License Number: " );
            String pharmacistLicenseNumber = Helper.readString();
            Pharmacist pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.PHARMACISTS);
            if (pharmacist == null) {
           	 System.out.println("Pharmacist does not exist!");
           	 return pharmacist;
            }
            pharmacist.setUsername(username);
            pharmacist.setEmail(email);
            pharmacist.setPhoneNo(phoneNo);
            pharmacist.setPasswordHash(hashedPassword);
            pharmacist.setPharmacistLicenseNumber(pharmacistLicenseNumber);
            return pharmacist;
        }
    }
    public static HMSPersonnel addPersonnel(String role) {
    	System.out.println("Enter UID: " );
    	String UID = Helper.readString();
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
             Doctor doctor = new Doctor(UID,fullName,idCard, username, email, phoneNo, "defaultPassword", DoB, gender, "Doctor", specialty, medicalLicenseNumber, dateJoin, yearsOfExperiences);
             return doctor;
        }      
        
        else {
        	System.out.println("Enter Pharmacist License Number: " );
        	String pharmacistLicenseNumber = Helper.readString();
        	System.out.println("Enter Date Of Employment: " );
        	LocalDateTime dateOfEmployment = readDate();
        	Pharmacist pharmacist = new Pharmacist(UID,fullName,idCard, username, email, phoneNo, "defaultPassword", DoB, gender, "Pharmacist",pharmacistLicenseNumber , dateOfEmployment);
            return pharmacist;
        }
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
    
    private static void listPersonnelByGender(String gender) {
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


    public static void printBreadCrumbs(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + breadcrumb + spaces + "║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    
    public static void main(String[] args) {
        PersonnelRepository.loadAllPersonnelFiles();
        viewAndManageStaff();
    }
}

	    
	

	
	

	