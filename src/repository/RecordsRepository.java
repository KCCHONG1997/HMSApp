package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import enums.AppointmentStatus;
import model.*;
import model.RecordStatusType;
import java.util.List;

import static model.RecordStatusType.toEnumRecordStatusType;

public class RecordsRepository {
    private static final String folder = "data";
    private static Boolean loadRepo = false;

    // Static data collections for different record types
    public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
    public static HashMap<String, AppointmentRecord> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    /**
     * Method to save all record files into CSV format
     */
    public static void saveAllRecordFiles() {
        saveRecordsToCSV("medical_records.csv", MEDICAL_RECORDS);
//        saveRecordsToCSV("appointment_records.csv", APPOINTMENT_RECORDS);
//        saveRecordsToCSV("payment_records.csv", PAYMENT_RECORDS);
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
            medRecord.getRecordID(),
            medRecord.getCreatedDate().toString(),
            medRecord.getUpdatedDate().toString(),
            medRecord.getRecordStatus().toString(),
            medRecord.getPatientID(),
            medRecord.getDoctorID(),
            medRecord.getBloodType()
            //medRecord.getDiagnosis()
        );
    } else if (record instanceof AppointmentRecord) {
        AppointmentRecord appRecord = (AppointmentRecord) record;
        return String.join(",",
            appRecord.getRecordID(),
            appRecord.getCreatedDate().toString(),
            appRecord.getUpdatedDate().toString(),
            appRecord.getRecordStatus().toString(),
            appRecord.getPatientID(),
            appRecord.getAppointmentTime().toString()
        );
    } else if (record instanceof PaymentRecord) {
        PaymentRecord payRecord = (PaymentRecord) record;
        return String.join(",",
            payRecord.getRecordID(),
            payRecord.getCreatedDate().toString(),
            payRecord.getUpdatedDate().toString(),
            payRecord.getRecordStatus().toString(),
            payRecord.getPatientID(),
            String.valueOf(payRecord.getPaymentAmount())      // Payment Amount
        );
    }
    return "";
}


    /**
     * Load all record files from CSV format, or create them if they don't exist
     */
    public static void loadAllRecordFiles() {
    	if (!loadRepo)
        loadRecordsFromCSV("medical_records.csv", MEDICAL_RECORDS, MedicalRecord.class);
//        loadRecordsFromCSV("appointment_records.csv", APPOINTMENT_RECORDS, AppointmentRecord.class);
//        loadRecordsFromCSV("payment_records.csv", PAYMENT_RECORDS, PaymentRecord.class);
    }
    
    public static Boolean isRepoLoad() {
    	return RecordsRepository.loadRepo;
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
//                        medRecord.getRecordID(),
//                        medRecord.getCreatedDate().toString(),
//                        medRecord.getUpdatedDate().toString(),
//                        medRecord.getRecordStatus().toString(),
//                        medRecord.getPatientID(),
//                        medRecord.getDoctorID(),
//                        medRecord.getBloodType()
                    //fields[0],                                       // recordID (MRID)
                    LocalDateTime.parse(fields[1]),                  // createdDate
                    LocalDateTime.parse(fields[2]),                  // updatedDate
                    toEnumRecordStatusType(fields[3]),             // recordStatus
                    fields[4],                                       // patientID
                    fields[5],                                       // doctorID
                    fields[6],                                       // blood type
                    DiagnosisRepository.patientDiagnosisRecords.getOrDefault(fields[4], new ArrayList<>())                               // bloodType
                ));
            } else if (type == AppointmentRecord.class) {
                // Parse fields[10] into parts for AppointmentOutcomeRecord
//                String[] outcomeFields = fields[10].split(";");  // Assuming ";" as delimiter for `typeOfService`, `prescription`, `consultationNotes`
//                
//                // Create AppointmentOutcomeRecord using parsed data
//                AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord();
//                if (outcomeFields.length >= 3) {  // Ensure we have enough fields for typeOfService, prescription, consultationNotes
//                    outcomeRecord.setTypeOfService(outcomeFields[0]);            // Set typeOfService
//                    outcomeRecord.setPrescription(new Prescription(outcomeFields[1])); // Create Prescription from string
//                    outcomeRecord.setConsultationNotes(outcomeFields[2]);        // Set consultationNotes
//                }
//              
//                
//                return type.cast(new AppointmentRecord(
////                        appRecord.getRecordID(),
////                        appRecord.getCreatedDate().toString(),
////                        appRecord.getUpdatedDate().toString(),
////                        appRecord.getRecordStatus().toString(),
////                        appRecord.getPatientID(),
////                        appRecord.getAppointmentTime().toString()                    
//                    LocalDateTime.parse(fields[1]),                  // createdDate
//                    LocalDateTime.parse(fields[2]),                  // updatedDate
//                    RecordStatusType.valueOf(fields[3]),             // recordStatus
//                    fields[4],                                       // patientID
//                    LocalDateTime.parse(fields[5]),                   // appointmentTime
//                    fields[8],                                       // location
//                    AppointmentStatus.valueOf(fields[9]),            // appointmentStatus
//                    outcomeRecord                                    // appointmentOutcomeRecord
//                ));
            } else if (type == PaymentRecord.class) {
//                return type.cast(new PaymentRecord(
////                        payRecord.getRecordID(),
////                        payRecord.getCreatedDate().toString(),
////                        payRecord.getUpdatedDate().toString(),
////                        payRecord.getRecordStatus().toString(),
////                        payRecord.getPatientID(),
////                        String.valueOf(payRecord.getPaymentAmount())      // Payment Amount
//                    fields[0],                                       // recordID
//                    LocalDateTime.parse(fields[1]),                  // createdDate
//                    LocalDateTime.parse(fields[2]),                  // updatedDate
//                    RecordStatusType.valueOf(fields[3]),             // recordStatus
//                    fields[4],                                       // patientID
//                    Double.parseDouble(fields[5])                    // paymentAmount
//                ));
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


























