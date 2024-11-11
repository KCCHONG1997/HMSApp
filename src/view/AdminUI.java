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
import helper.DateTimePicker;
import helper.Helper;

public class AdminUI extends MainUI {
    private Admin admin;

    public AdminUI(Admin admin) {
        this.admin = admin;
    }

    // For testing:
    // public static void main (String[]args) {
    // // Creating a dummy Admin with sample data
    // String fullName = "John Doe";
    // String idCard = "123456789";
    // String username = "adminUser";
    // String email = "admin@example.com";
    // String phoneNo = "123-456-7890";
    // String passwordHash = "adminPass123"; // This should ideally be hashed
    // LocalDateTime DoB = LocalDateTime.of(1980, 1, 1, 0, 0);
    // String gender = "Male";
    // String role = "Admins";
    // LocalDateTime dateOfCreation = LocalDateTime.now();
    //
    // // Initializing the Admin object
    // Admin admin = new Admin(fullName, idCard, username, email, phoneNo,
    // passwordHash, DoB, gender, role, dateOfCreation);
    //
    // // Creating and starting AdminUI with the dummy admin
    // AdminUI testing = new AdminUI(admin);
    // testing.start();
    // }

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
            choice = Helper.readInt("");
            switch (choice) {
                case 1:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > View and Manage Staff");
                    viewAndManageStaff();
                    break;
                case 2:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > View Appointments");
                    break;
                case 3:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > View And Manage Medication Inventory");
                    viewAndManageMedicationInventory();
                    break;
                case 4:
                    printBreadCrumbs("HMS App UI > Admin Dashboard > Approve Replenish Request");
                    approveReplenishRequest();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 8);

        sc.close(); // Close the Scanner
    }

    public static void viewAndManageStaff() {
        System.out.println("Enter your choice");
        System.out.println("1. View Staff By Role");
        System.out.println("2. View Staff By Gender");
        System.out.println("3. View Staff By Age");
        System.out.println("4. Add Doctor");
        System.out.println("5. Add Pharmacist");
        System.out.println("6. Update Doctor");
        System.out.println("7. Update Pharmacist");
        System.out.println("8. Remove Doctor");
        System.out.println("9. Remove Pharmacist");
        int choice = Helper.readInt("");

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
        String fullName = Helper.readString("Enter Full Name: ");
        String idCard = Helper.readString("Enter ID Card: ");
        String username = Helper.readString("Enter Username: ");
        String email = Helper.readString("Enter Email: ");
        String phoneNo = Helper.readString("Enter Phone No: ");
        System.out.println("Enter DOB (YYYY-MM-DD HH:MM): ");
        LocalDateTime DoB = Helper.readDate();
        String gender = Helper.readString("Enter Gender: ");

        if (role.equals("Doctor")) {
            String specialty = Helper.readString("Enter Specialty: ");
            String medicalLicenseNumber = Helper.readString("Enter Medical License Number: ");
            System.out.println("Enter Date Join (YYYY-MM-DD HH:MM): ");
            LocalDateTime dateJoin = Helper.readDate();
            int yearsOfExperiences = Helper.readInt("Enter Years of Experience: ");

            Doctor doctor = new Doctor(fullName, idCard, username, email, phoneNo, "defaultPassword",
                    DoB, gender, specialty, medicalLicenseNumber, dateJoin, yearsOfExperiences);
            HMSPersonnel personnel = doctor;
            AdminController.addPersonnel(personnel);
        } else {
            String pharmacistLicenseNumber = Helper.readString("Enter Pharmacist License Number: ");
            System.out.println("Enter Date Of Employment (YYYY-MM-DD HH:MM): ");
            LocalDateTime dateOfEmployment = Helper.readDate();

            Pharmacist pharmacist = new Pharmacist(fullName, idCard, username, email, phoneNo, "defaultPassword",
                    DoB, gender, pharmacistLicenseNumber, dateOfEmployment);
            HMSPersonnel personnel = pharmacist;
            AdminController.addPersonnel(personnel);
        }
    }

    public static void updatePersonnel(String role) {
        String UID = Helper.readString("Enter UID: ");
        Doctor doctor = (Doctor) HMSPersonnelController.getPersonnelByUID(UID, PersonnelFileType.DOCTORS);
        Pharmacist pharmacist = (Pharmacist) HMSPersonnelController.getPersonnelByUID(UID,
                PersonnelFileType.PHARMACISTS);

        if (doctor == null && pharmacist == null) {
            System.out.println("Personnel does not exist!");
            return;
        }

        String username = Helper.readString("Enter New Username: ");
        String email = Helper.readString("Enter New Email: ");
        String phoneNo = Helper.readString("Enter New Phone No: ");
        String hashedPassword = Helper.readString("Enter New Password: ");

        if (role.equals("Doctor")) {
            String specialty = Helper.readString("Enter New Specialty: ");
            String medicalLicenseNumber = Helper.readString("Enter New Medical License Number: ");
            int yearsOfExperiences = Helper.readInt("Enter New Years of Experience: ");

            doctor.setUsername(username);
            doctor.setEmail(email);
            doctor.setPhoneNo(phoneNo);
            doctor.setPasswordHash(hashedPassword);
            doctor.setSpecialty(specialty);
            doctor.setMedicalLicenseNumber(medicalLicenseNumber);
            doctor.setYearsOfExperiences(yearsOfExperiences);

            AdminController.updatePersonnel(doctor.getUID(), doctor);
        } else {
            String pharmacistLicenseNumber = Helper.readString("Enter New Pharmacist License Number: ");

            pharmacist.setUsername(username);
            pharmacist.setEmail(email);
            pharmacist.setPhoneNo(phoneNo);
            pharmacist.setPasswordHash(hashedPassword);
            pharmacist.setPharmacistLicenseNumber(pharmacistLicenseNumber);

            AdminController.updatePersonnel(pharmacist.getUID(), pharmacist);
        }
    }

    public static void removePersonnel(String role) {
        System.out.println("Enter Staff UID");
        if (role == "Doctor") {
            String uidDoctor = Helper.readString();
            AdminController.removePersonnel(uidDoctor, PersonnelFileType.DOCTORS);
        } else {
            String uidPharmacist = Helper.readString();
            AdminController.removePersonnel(uidPharmacist, PersonnelFileType.PHARMACISTS);
        }
    }

    public static void viewAndManageMedicationInventory() {
        System.out.println("Enter your choice");
        System.out.println("1. View All Medicine");
        System.out.println("2. Add Medicine");
        System.out.println("3. Update Medicine");
        System.out.println("4. Remove Medicine");
        int choice = Helper.readInt("");
        switch (choice) {
            case 1:
                AdminController.listAllMedicine();
                break;
            case 2:
                addMedicine();
                break;
            case 3:
                updateMedicine();
                break;
            case 4:
                removeMedicine();
                break;
            default:
                System.out.println("Error: Invalid choice. Please select a valid option.");
                break;
        }
    }

    public static void addMedicine() {
        String medicineID = Helper.readString("Enter Medicine ID: ");
        String name = Helper.readString("Enter Medicine Name: ");
        String manufacturer = Helper.readString("Enter Manufacturer: ");
        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter Expiry Date (YYYY-MM-DDTHH:MM:): ");
        int inventoryStock = Helper.readInt("Enter Inventory Stock: ");
        int lowStockLevel = Helper.readInt("Enter Low Stock Level: ");
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
        String medicineID = Helper.readString("Enter Medicine ID: ");
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);
        String manufacturer = Helper.readString("Enter New Manufacturer: ");
        medicine.setManufacturer(manufacturer);
        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);
        int inventoryStock = Helper.readInt("Enter New Inventory Stock: ");
        medicine.setInventoryStock(inventoryStock);
        int lowStockLevel = Helper.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);
        AdminController.updateMedicine(medicineID, medicine);
    }

    public static void removeMedicine() {
        String medicineID = Helper.readString("Enter Medicine ID: ");
        AdminController.removeMedicine(medicineID);
    }

    public static void approveReplenishRequest() {

        String medicineID = Helper.readString("Enter Medicine ID: ");
        Medicine medicine = MedicineController.getMedicineByUID(medicineID);

        String manufacturer = Helper.readString("Enter New Manufacturer: ");
        medicine.setManufacturer(manufacturer);

        LocalDateTime expiryDate = DateTimePicker.pickDateTime("Enter New Expiry Date (YYYY-MM-DD HH:MM:): ");
        medicine.setExpiryDate(expiryDate);

        int inventoryStock = Helper.readInt("Enter New Inventory Stock: ");
        medicine.setInventoryStock(inventoryStock);

        int lowStockLevel = Helper.readInt("Enter New Low Stock Level: ");
        medicine.setLowStockLevel(lowStockLevel);

        medicine.setReplenishStatus(ReplenishStatus.APPROVED);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime approvedDate = LocalDateTime.parse(formattedDateTime, formatter);
        medicine.setApprovedDate(approvedDate);
        AdminController.approveReplenishRequest(medicineID, medicine);
    }

}
