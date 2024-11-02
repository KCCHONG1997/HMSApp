package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;

import enums.AppointmentStatus;
import model.*;

public class RecordsRepository {
    private static final String folder = "data";

    // Static data collections for different record types
    public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
    public static HashMap<String, AppointmentRecord> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    /**
     * Method to save all record files into CSV format
     */
    public static void saveAllRecordFiles() {
        saveRecordsToCSV("medical_records.csv", MEDICAL_RECORDS);
        saveRecordsToCSV("appointment_records.csv", APPOINTMENT_RECORDS);
        saveRecordsToCSV("payment_records.csv", PAYMENT_RECORDS);
    }

    /**
     * Save a specific record map to a CSV file
     */
    private static <T extends HMSRecords> void saveRecordsToCSV(String fileName, HashMap<String, T> recordsMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        // Ensure the directory exists
        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T record : recordsMap.values()) {
                writer.write(recordToCSV(record));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving record data: " + e.getMessage());
        }
    }

    // Convert a record object to a CSV line
    private static String recordToCSV(HMSRecords record) {
    if (record instanceof MedicalRecord) {
        MedicalRecord medRecord = (MedicalRecord) record;
        return String.join(",",
            medRecord.getMRID(),                               // MRID
            medRecord.getPatient().getUID(),                   // Patient UID
            medRecord.getCreatedBy().getUID(),                 // Doctor UID
            medRecord.getDiagnosis(),                          // Diagnosis
            medRecord.getTreatmentPlan(),                      // Treatment Plan
            medRecord.getCreatedDate().toString(),             // Date of Record
            medRecord.getRecordStatus().toString()             // Record Status
        );
    } else if (record instanceof AppointmentRecord) {
        AppointmentRecord appRecord = (AppointmentRecord) record;
        return String.join(",",
            appRecord.getRecordID(),                           // Record ID
            appRecord.getPatient().getUID(),                   // Patient UID
            appRecord.getCreatedBy().getUID(),                 // Doctor UID
            appRecord.getAppointmentTime().toString(),         // Appointment Time
            appRecord.getRecordStatus().toString()             // Appointment Status
        );
    } else if (record instanceof PaymentRecord) {
        PaymentRecord payRecord = (PaymentRecord) record;
        return String.join(",",
            payRecord.getRecordID(),                           // Record ID
            payRecord.getPatient().getUID(),                   // Patient UID
            String.valueOf(payRecord.getPaymentAmount()),      // Payment Amount
            payRecord.getCreatedDate().toString(),             // Payment Date
            payRecord.getRecordStatus().toString()             // Payment Status
        );
    }
    return "";
}


    /**
     * Load all record files from CSV format, or create them if they don't exist
     */
    public static void loadAllRecordFiles() {
        loadRecordsFromCSV("medical_records.csv", MEDICAL_RECORDS, MedicalRecord.class);
        loadRecordsFromCSV("appointment_records.csv", APPOINTMENT_RECORDS, AppointmentRecord.class);
        loadRecordsFromCSV("payment_records.csv", PAYMENT_RECORDS, PaymentRecord.class);
    }

    /**
     * Load records from a CSV file or create an empty file if it doesn't exist
     */
    private static <T extends HMSRecords> void loadRecordsFromCSV(String fileName, HashMap<String, T> recordsMap, Class<T> type) {
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
                T record = csvToRecord(line, type);
                if (record != null) {
                    recordsMap.put(record.getRecordID(), record);
                }
            }
            System.out.println("Successfully loaded " + recordsMap.size() + " records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading record data: " + e.getMessage());
        }
    }

    // Convert a CSV line to a record object
    private static <T extends HMSRecords> T csvToRecord(String csv, Class<T> type) {
        String[] fields = csv.split(",");

        try {
            if (type == MedicalRecord.class) {
                return type.cast(new MedicalRecord(
                    fields[0],                                       // recordID (MRID)
                    PersonnelRepository.DOCTORS.get(fields[1]),      // createdBy (Doctor object by UID)
                    LocalDateTime.parse(fields[2]),                  // createdDate
                    LocalDateTime.parse(fields[3]),                  // updatedDate
                    RecordStatusType.valueOf(fields[4]),             // recordStatus
                    fields[5],                                       // description
                    PersonnelRepository.PATIENTS.get(fields[6]),     // patient (Patient object by UID)
                    fields[7],                                       // diagnosis
                    fields[8],                                       // treatmentPlan
                    fields[0],                                       // MRID (same as recordID in this case)
                    null                                             // medicationList (still needs handling)
                ));
            } else if (type == AppointmentRecord.class) {
                return type.cast(new AppointmentRecord(
                    fields[0],                                       // recordID
                    PersonnelRepository.DOCTORS.get(fields[1]),      // createdBy (Doctor object by UID)
                    LocalDateTime.parse(fields[2]),                  // createdDate
                    LocalDateTime.parse(fields[3]),                  // updatedDate
                    RecordStatusType.valueOf(fields[4]),             // recordStatus
                    fields[5],                                       // description
                    PersonnelRepository.PATIENTS.get(fields[6]),     // patient (Patient object by UID)
                    LocalDateTime.parse(fields[7]),                   // appointmentTime
                    fields[8],										 // location
                    AppointmentStatus.valueOf(fields[9])         // appointmentStatus
                    
                ));
            } else if (type == PaymentRecord.class) {
                return type.cast(new PaymentRecord(
                    fields[0],                                       // recordID
                    PersonnelRepository.DOCTORS.get(fields[1]),      // createdBy (Doctor object by UID)
                    LocalDateTime.parse(fields[2]),                  // createdDate
                    LocalDateTime.parse(fields[3]),                  // updatedDate
                    RecordStatusType.valueOf(fields[4]),             // recordStatus
                    fields[5],                                       // description
                    PersonnelRepository.PATIENTS.get(fields[6]),     // patient (Patient object by UID)
                    Double.parseDouble(fields[7])                    // paymentAmount
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing record data: " + e.getMessage());
        }

        return null;
    }


    /**
     * Clear all record data and save empty files
     */
    public static boolean clearRecordDatabase() {
        MEDICAL_RECORDS.clear();
        APPOINTMENT_RECORDS.clear();
        PAYMENT_RECORDS.clear();
        saveAllRecordFiles();
        return true;
    }
}
