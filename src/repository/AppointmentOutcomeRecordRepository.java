package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import model.AppointmentOutcomeRecord;
import model.Prescription;

public class AppointmentOutcomeRecordRepository extends Repository{
    private static final String folder = "data";
    
    private static boolean isRepoLoaded = false;
    private static final String AppointmentOutcomeRecordsfileName = "appointment_outcome_records.csv";
    public static HashMap<String,AppointmentOutcomeRecord> patientOutcomeRecords = new HashMap<>();

    
	@Override
	public boolean loadFromCSV() {
		loadAppoinmentOutcomeRecordsFromCSV(AppointmentOutcomeRecordsfileName,patientOutcomeRecords);
		setRepoLoaded(true);
		return false;
	}

    public static void saveAppoinmentOutcomeRecordsToCSV(String fileName, HashMap<String, AppointmentOutcomeRecord> patientOutcomeRecords) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String patientID : patientOutcomeRecords.keySet()) {
                AppointmentOutcomeRecord record = patientOutcomeRecords.get(patientID);
                if(record != null)
                {
                	writer.write(appointmentOutcomeToCSV(patientID,record));
                    writer.newLine();
                }
                
            }
            System.out.println("Appointment outcome records successfully saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving appointment outcome records to CSV: " + e.getMessage());
        }
    }

    // Convert an AppointmentOutcomeRecord object to a CSV line
    private static String appointmentOutcomeToCSV(String patientID, AppointmentOutcomeRecord record) {
        return String.join(",",
                record.getAppointmentTime().toString(),   // Appointment time
                record.getTypeOfService(),                // Type of Service
                record.getPrescription().toString(),      // prescription 
                "\"" + record.getConsultationNotes() + "\"", // Consultation Notes
                record.getPatientID() +                   // Patient ID
                record.getDoctorID()                    //Doctor ID
        );
    }
   
    /**
     * Load AppointmentOutcomeRecord records from a CSV file, or create an empty file if it doesn't exist
     */
    public static void loadAppoinmentOutcomeRecordsFromCSV(String fileName, HashMap<String, AppointmentOutcomeRecord> patientOutcomeRecords) {
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
            while ((line = reader.readLine()) != null) {
                AppointmentOutcomeRecord record = csvToOutcomeRecord(line);
                String patientID = getPatientIDFromCSV(line);
                if (record != null && patientID != null) {
                	patientOutcomeRecords.put(patientID, record);
                }
                
            }
            System.out.println("Successfully loaded " + patientOutcomeRecords.size() + " appointment outcome records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading appointment outcome records: " + e.getMessage());
        }
    }
    private static String getPatientIDFromCSV(String csv) {
        String[] fields = csv.split(",");
        return fields[4];
    }

    // Convert a CSV line to an AppointmentOutcomeRecord object
    private static AppointmentOutcomeRecord csvToOutcomeRecord(String csv) {
        // Split by comma, ignoring commas within quotes
        String[] fields = csv.split(",");
        try {
            LocalDateTime appointmentTime = LocalDateTime.parse(fields[0]);
            String typeOfService = fields[1];
            Prescription prescription = PrescriptionRepository.PRESCRIPTION_MAP.get(fields[2]);
            String consultationNotes = fields[3].replace("\"", "");
            String patientID = fields[4];
            String doctorID = fields[5];
            
            return new AppointmentOutcomeRecord(appointmentTime,typeOfService,  prescription, consultationNotes,patientID, doctorID);
        } catch (Exception e) {
            System.out.println("Error parsing appointment outcome record data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clear all appointment outcome record data and save empty files
     */
    public static boolean clearOutcomeRecordDatabase() {
        patientOutcomeRecords.clear();
        saveAppoinmentOutcomeRecordsToCSV("appointment_outcome_records.csv", patientOutcomeRecords);
        return true;
    }

	public static boolean isRepoLoaded() {
		return isRepoLoaded;
	}

	public static void setRepoLoaded(boolean isRepoLoaded) {
		AppointmentOutcomeRecordRepository.isRepoLoaded = isRepoLoaded;
	}


}
