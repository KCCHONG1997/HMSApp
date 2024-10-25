package repository;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import model.*;

public class PersonnelRepository {
    private static final String folder = "data";

    // Static data collections for personnel
    public static HashMap<String, Doctor> DOCTORS = new HashMap<>();
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Pharmacist> PHARMACISTS = new HashMap<>();
    public static HashMap<String, Admin> ADMINS = new HashMap<>();

    // Save all personnel files to CSV files
    public static void saveAllPersonnelFiles() {
        savePersonnelToCSV("doctors.csv", DOCTORS);
        savePersonnelToCSV("patients.csv", PATIENTS);
        savePersonnelToCSV("pharmacists.csv", PHARMACISTS);
        savePersonnelToCSV("admins.csv", ADMINS);
    }

    // Save a specific personnel map to a CSV file
    private static <T extends HMSPersonnel> void savePersonnelToCSV(String fileName, HashMap<String, T> personnelMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T personnel : personnelMap.values()) {
                writer.write(personnelToCSV(personnel));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving personnel data: " + e.getMessage());
        }
    }

    // Convert personnel object to a CSV line
    private static String personnelToCSV(HMSPersonnel personnel) {
        return String.join(",",
            personnel.getUID(),
            personnel.getFullName(),
            personnel.getIdCard(),
            personnel.getUsername(),
            personnel.getEmail(),
            personnel.getPhoneNo(),
            personnel.getPasswordHash(),
            personnel.getDoB().toString(),
            personnel.getGender(),
            personnel.getRole(),
            // Additional fields for specific personnel types (like Doctor)
            personnel instanceof Doctor ? ((Doctor) personnel).getSpecialty() : "",
            personnel instanceof Doctor ? ((Doctor) personnel).getMedicalLicenseNumber() : "",
            personnel instanceof Doctor ? ((Doctor) personnel).getDateJoin().toString() : "",
            personnel instanceof Doctor ? String.valueOf(((Doctor) personnel).getYearsOfExperiences()) : ""
        );
    }

    // Load personnel data from CSV files
    public static void loadAllPersonnelFiles() {
        loadPersonnelFromCSV("doctors.csv", DOCTORS, Doctor.class);
        loadPersonnelFromCSV("patients.csv", PATIENTS, Patient.class);
        loadPersonnelFromCSV("pharmacists.csv", PHARMACISTS, Pharmacist.class);
        loadPersonnelFromCSV("admins.csv", ADMINS, Admin.class);
    }

    // Load personnel from a CSV file or create an empty file if it doesn't exist
    private static <T extends HMSPersonnel> void loadPersonnelFromCSV(String fileName, HashMap<String, T> personnelMap, Class<T> type) {
        String filePath = "./src/repository/" + folder + "/" + fileName;
        
        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();  // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;  // No data to load, as the file was just created
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1; // To track which line might cause an issue
            while ((line = reader.readLine()) != null) {
                T personnel = csvToPersonnel(line, type);
                if (personnel != null) {
                    personnelMap.put(personnel.getUID(), personnel);
                } else {
                    System.out.println("Warning: Failed to parse personnel at line " + lineNumber + " in " + fileName);
                }
                lineNumber++;
            }
//            System.out.println("Successfully loaded " + personnelMap.size() + " personnel from " + fileName);
            System.out.println("Successfully loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading personnel data: " + e.getMessage());
        }
    }


 // Convert a CSV line to a personnel object
    private static <T extends HMSPersonnel> T csvToPersonnel(String csv, Class<T> type) {
        String[] fields = csv.split(",");

        try {
            if (type == Doctor.class) {
                return type.cast(new Doctor(
                    fields[0],               // UID
                    fields[1],               // fullName
                    fields[2],               // idCard
                    fields[3],               // username
                    fields[4],               // email
                    fields[5],               // phoneNo
                    fields[6],               // passwordHash
                    LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                    fields[8],               // gender
                    fields[9],               // role (e.g., Doctor)
                    fields[10],              // specialty
                    fields[11],              // medicalLicenseNumber
                    LocalDateTime.parse(fields[12]), // dateJoin (LocalDateTime)
                    Integer.parseInt(fields[13])    // yearsOfExperience
                ));
            } else if (type == Patient.class) {
                return type.cast(new Patient(
                    fields[0],               // UID
                    fields[1],               // fullName
                    fields[2],               // idCard
                    fields[3],               // username
                    fields[4],               // email
                    fields[5],               // phoneNo
                    fields[6],               // passwordHash
                    LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                    fields[8],               // gender
                    fields[9],               // role (e.g., Patient)
                    fields[10],              // patientId
                    fields[11],              // bloodType
                    fields[12],              // insuranceInfo
                    fields[13],              // allergies
                    LocalDateTime.parse(fields[14])  // dateOfAdmission (LocalDateTime)
                ));
            } else if (type == Pharmacist.class) {
                return type.cast(new Pharmacist(
                    fields[0],               // UID
                    fields[1],               // fullName
                    fields[2],               // idCard
                    fields[3],               // username
                    fields[4],               // email
                    fields[5],               // phoneNo
                    fields[6],               // passwordHash
                    LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                    fields[8],               // gender
                    fields[9],               // role (e.g., Pharmacist)
                    fields[10],              // pharmacistLicenseNumber
                    LocalDateTime.parse(fields[11])  // dateOfEmployment (LocalDateTime)
                ));
            } else if (type == Admin.class) {
                return type.cast(new Admin(
                    fields[0],               // UID
                    fields[1],               // fullName
                    fields[2],               // idCard
                    fields[3],               // username
                    fields[4],               // email
                    fields[5],               // phoneNo
                    fields[6],               // passwordHash
                    LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                    fields[8],               // gender
                    fields[9],               // role (e.g., Admin)
                    LocalDateTime.parse(fields[10]) // dateOfCreation (LocalDateTime)
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing personnel data: " + e.getMessage());
        }

        return null;
    }


    // Clear all personnel data
    public static boolean clearPersonnelDatabase() {
        DOCTORS.clear();
        PATIENTS.clear();
        PHARMACISTS.clear();
        ADMINS.clear();
        saveAllPersonnelFiles();
        return true;
    }
}
