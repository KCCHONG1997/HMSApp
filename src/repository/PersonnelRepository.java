package repository;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import model.*;

public class PersonnelRepository extends Repository {
    private static final String folder = "data";
    private static final String doctorsFileName = "doctors.csv";
    private static final String patientsFileName = "patients.csv";
    private static final String pharmacistsFileName = "pharmacists.csv";
    private static final String adminsFileName = "admins.csv";
    private static Boolean isRepoLoaded = false;

    // Static data collections for personnel
    public static HashMap<String, Doctor> DOCTORS = new HashMap<>();
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Pharmacist> PHARMACISTS = new HashMap<>();
    public static HashMap<String, Admin> ADMINS = new HashMap<>();

    // Save all personnel files to CSV files
    public static void saveAllPersonnelFiles() {
        savePersonnelToCSV(doctorsFileName, DOCTORS);
        savePersonnelToCSV(patientsFileName, PATIENTS);
        savePersonnelToCSV(pharmacistsFileName, PHARMACISTS);
        savePersonnelToCSV(adminsFileName, ADMINS);
    }

    @Override
    // Load personnel data from CSV files
    public boolean loadFromCSV() {
        try {
            loadPersonnelFromCSV(doctorsFileName, DOCTORS, Doctor.class);
            loadPersonnelFromCSV(patientsFileName, PATIENTS, Patient.class);
            loadPersonnelFromCSV(pharmacistsFileName, PHARMACISTS, Pharmacist.class);
            loadPersonnelFromCSV(adminsFileName, ADMINS, Admin.class);
            setRepoLoaded(true);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading records repository: " + e.getMessage());
            return false;
        }
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
        StringBuilder csvBuilder = new StringBuilder();

        // Add common fields
        csvBuilder.append(personnel.getUID()).append(",");
        csvBuilder.append(personnel.getFullName()).append(",");
        csvBuilder.append(personnel.getIdCard()).append(",");
        csvBuilder.append(personnel.getUsername()).append(",");
        csvBuilder.append(personnel.getEmail()).append(",");
        csvBuilder.append(personnel.getPhoneNo()).append(",");
        csvBuilder.append(personnel.getPasswordHash()).append(",");
        csvBuilder.append(personnel.getDoB().toString()).append(",");
        csvBuilder.append(personnel.getGender()).append(",");
        csvBuilder.append(personnel.getRole()).append(",");

        // Add Doctor-specific fields or empty placeholders
        if (personnel instanceof Doctor) {
            Doctor doctor = (Doctor) personnel;
            csvBuilder.append(doctor.getSpecialty()).append(",");
            csvBuilder.append(doctor.getMedicalLicenseNumber()).append(",");
            csvBuilder.append(doctor.getDateJoin().toString()).append(",");
            csvBuilder.append(doctor.getYearsOfExperiences()).append(",");
        }

        // Add Patient-specific fields or empty placeholders
        if (personnel instanceof Patient) {
            Patient patient = (Patient) personnel;
            csvBuilder.append(patient.getInsuranceInfo()).append(",");
            csvBuilder.append(patient.getAllergies()).append(",");
            csvBuilder.append(patient.getDateOfAdmission().toString()).append(",");
        }

        // Add Pharmacist-specific fields or empty placeholders
        if (personnel instanceof Pharmacist) {
            Pharmacist pharmacist = (Pharmacist) personnel;
            csvBuilder.append(pharmacist.getPharmacistLicenseNumber()).append(",");
            csvBuilder.append(pharmacist.getDateOfEmployment().toString()).append(",");
        }

        // Add Admin-specific fields or empty placeholder
        if (personnel instanceof Admin) {
            Admin admin = (Admin) personnel;
            csvBuilder.append(admin.getDateOfCreation().toString()).append(",");
        }

        // Remove the trailing comma at the end
        if (csvBuilder.charAt(csvBuilder.length() - 1) == ',') {
            csvBuilder.setLength(csvBuilder.length() - 1);
        }

        return csvBuilder.toString();
    }

    // Load personnel from a CSV file or create an empty file if it doesn't exist
    private static <T extends HMSPersonnel> void loadPersonnelFromCSV(String fileName, HashMap<String, T> personnelMap,
            Class<T> type) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile(); // Create an empty file if it doesn't exist
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return; // No data to load, as the file was just created
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
            // System.out.println("Successfully loaded " + personnelMap.size() + " personnel
            // from " + fileName);
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
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // idCard
                        fields[3], // username
                        fields[4], // email
                        fields[5], // phoneNo
                        fields[6], // passwordHash
                        LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                        fields[8], // gender
                        fields[9], // role (e.g., Doctor)
                        fields[10], // specialty
                        fields[11], // medicalLicenseNumber
                        LocalDateTime.parse(fields[12]), // dateJoin (LocalDateTime)
                        Integer.parseInt(fields[13]) // yearsOfExperience
                ));
            } else if (type == Patient.class) {
                return type.cast(new Patient(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // idCard
                        fields[3], // username
                        fields[4], // email
                        fields[5], // phoneNo
                        fields[6], // passwordHash
                        LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                        fields[8], // gender
                        fields[9], // role (e.g., Patient)
                        fields[10], // insurance info
                        fields[11], // allergies
                        LocalDateTime.parse(fields[12]) // dateOfAdmission (LocalDateTime)

                ));
            } else if (type == Pharmacist.class) {
                return type.cast(new Pharmacist(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // idCard
                        fields[3], // username
                        fields[4], // email
                        fields[5], // phoneNo
                        fields[6], // passwordHash
                        LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                        fields[8], // gender
                        fields[9], // role (e.g., Pharmacist)
                        fields[10], // pharmacistLicenseNumber
                        LocalDateTime.parse(fields[11]) // dateOfEmployment (LocalDateTime)
                ));
            } else if (type == Admin.class) {
                return type.cast(new Admin(
                        fields[0], // UID
                        fields[1], // fullName
                        fields[2], // idCard
                        fields[3], // username
                        fields[4], // email
                        fields[5], // phoneNo
                        fields[6], // passwordHash
                        LocalDateTime.parse(fields[7]), // DoB (LocalDateTime)
                        fields[8], // gender
                        fields[9], // role (e.g., Admin)
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

    public static Boolean isRepoLoaded() {
        return isRepoLoaded;
    }

    public static void setRepoLoaded(Boolean isRepoLoaded) {
        PersonnelRepository.isRepoLoaded = isRepoLoaded;
    }

}
