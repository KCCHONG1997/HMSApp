package repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.*;

public class RecordsRepository {
    private static final String folder = "data";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
    public static HashMap<String, AppointmentRecord> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    public static void saveAllRecordFiles() {
        saveRecordsToCSV("medical_records.csv", MEDICAL_RECORDS);
        saveRecordsToCSV("appointment_records.csv", APPOINTMENT_RECORDS);
        saveRecordsToCSV("payment_records.csv", PAYMENT_RECORDS);
    }

    private static <T extends HMSRecords> void saveRecordsToCSV(String fileName, HashMap<String, T> recordsMap) {
        String filePath = "./src/repository/" + folder + "/" + fileName;

        File directory = new File("./src/repository/" + folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T record : recordsMap.values()) {
                String csvLine = recordToCSV(record);
                System.out.println("Saving record to CSV: " + csvLine);
                writer.write(csvLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving record data: " + e.getMessage());
        }
    }

    private static String recordToCSV(HMSRecords record) {
        if (record instanceof MedicalRecord) {
            MedicalRecord medRecord = (MedicalRecord) record;
            return String.join(",",
                medRecord.getRecordID(),
                medRecord.getCreatedDate().format(DATE_TIME_FORMATTER),
                medRecord.getUpdatedDate().format(DATE_TIME_FORMATTER),
                medRecord.getRecordStatus().toString(),
                medRecord.getPatientID(),
                medRecord.getBloodType(),
                diagnosisListToCSV(medRecord.getDiagnosis())
            );
        }
        return "";
    }

    private static String diagnosisListToCSV(List<Diagnosis> diagnosisList) {
        // Format each Diagnosis into a CSV string
        StringBuilder diagnosisCSV = new StringBuilder();

        for (Diagnosis diagnosis : diagnosisList) {
            String patientID = diagnosis.getPatientID();
            String diagnosisDescription = diagnosis.getDiagnosisDescription();
            String diagnosisDate = diagnosis.getDiagnosisDate().format(DATE_TIME_FORMATTER);
            String prescriptionsCSV = prescriptionListToCSV(diagnosis.getPrescriptions());

            diagnosisCSV.append(patientID)
                        .append(";")
                        .append(diagnosisDescription)
                        .append(";")
                        .append(diagnosisDate);

            // Add prescriptions only if they are present
            if (!prescriptionsCSV.isEmpty()) {
                diagnosisCSV.append(";").append(prescriptionsCSV);
            }
            
            diagnosisCSV.append("|"); // Separate each Diagnosis entry with "|"
        }

        // Remove the last "|" character if present
        if (diagnosisCSV.length() > 0 && diagnosisCSV.charAt(diagnosisCSV.length() - 1) == '|') {
            diagnosisCSV.setLength(diagnosisCSV.length() - 1);
        }

        return diagnosisCSV.toString();
    }

    private static String prescriptionListToCSV(List<Prescription> prescriptionList) {
        // Format each Prescription into a CSV string
        StringBuilder prescriptionsCSV = new StringBuilder();

        for (Prescription prescription : prescriptionList) {
            String prescriptionDate = prescription.getPrescriptionDate().format(DATE_TIME_FORMATTER);
            
            // Format each medication for the prescription
            StringBuilder medicationsCSV = new StringBuilder();
            for (PrescribedMedication med : prescription.getMedications()) {
                medicationsCSV.append(med.getMedication().getMedicineName())
                              .append("#")
                              .append(med.getMedicineQuantity())
                              .append("#")
                              .append(med.getPrescriptionStartDate().format(DATE_TIME_FORMATTER))
                              .append("#")
                              .append(med.getPrescriptionEndDate().format(DATE_TIME_FORMATTER))
                              .append("#")
                              .append(med.getPrescriptionStatus())
                              .append("#")
                              .append(med.getDosage())
                              .append("&"); // Separate each medication with "&"
            }

            // Remove the last "&" character from medicationsCSV
            if (medicationsCSV.length() > 0 && medicationsCSV.charAt(medicationsCSV.length() - 1) == '&') {
                medicationsCSV.setLength(medicationsCSV.length() - 1);
            }

            // Append medications and prescription date, separating multiple prescriptions by "^"
            prescriptionsCSV.append(medicationsCSV)
                            .append("^")
                            .append(prescriptionDate)
                            .append("@"); // Separate each Prescription with "@"
        }

        // Remove the last "@" character if present
        if (prescriptionsCSV.length() > 0 && prescriptionsCSV.charAt(prescriptionsCSV.length() - 1) == '@') {
            prescriptionsCSV.setLength(prescriptionsCSV.length() - 1);
        }

        return prescriptionsCSV.toString();
    }


    public static void loadAllRecordFiles() {
        loadRecordsFromCSV("medical_records.csv", MEDICAL_RECORDS, MedicalRecord.class);
    }

    private static <T extends HMSRecords> void loadRecordsFromCSV(String fileName, HashMap<String, T> recordsMap, Class<T> type) {
        String filePath = "./src/repository/" + folder + "/" + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created empty file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder recordBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Process complete record if an empty line or break is found
                    if (recordBuilder.length() > 0) {
                        T record = csvToRecord(recordBuilder.toString().trim(), type);
                        if (record != null) {
                            recordsMap.put(record.getRecordID(), record);
                        }
                        recordBuilder.setLength(0); // Reset for the next record
                    }
                } else {
                    recordBuilder.append(line).append(" "); // Concatenate lines for a single record
                }
            }
            // Process the last record if file doesn't end with an empty line
            if (recordBuilder.length() > 0) {
                T record = csvToRecord(recordBuilder.toString().trim(), type);
                if (record != null) {
                    recordsMap.put(record.getRecordID(), record);
                }
            }
            System.out.println("Successfully loaded " + recordsMap.size() + " records from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading record data: " + e.getMessage());
        }
    }

    private static <T extends HMSRecords> T csvToRecord(String csv, Class<T> type) {
        String[] fields = csv.split(",");
//        System.out.println("kchong testing");
//        System.out.println(fields[1]);

        try {
            if (type == MedicalRecord.class) {
                List<Diagnosis> diagnosis = parseDiagnosisList(fields[6].trim());
                return type.cast(new MedicalRecord(
                    fields[0].trim(),
                    LocalDateTime.parse(fields[1].trim(), DATE_TIME_FORMATTER),
                    LocalDateTime.parse(fields[2].trim(), DATE_TIME_FORMATTER),
                    RecordStatusType.valueOf(fields[3].trim().toUpperCase()),
                    fields[4].trim(),
                    fields[5].trim(),
                    diagnosis
                ));
            }
        } catch (Exception e) {
            System.out.println("Error parsing record data in line: " + csv);
            e.printStackTrace();
        }
        return null;
    }

    private static List<Diagnosis> parseDiagnosisList(String diagnosisCSV) {
//        System.out.println("kchong testing2 diagnosisCSV");
//        System.out.println(diagnosisCSV);
        
        List<Diagnosis> diagnosisList = new ArrayList<>();

        // Split the diagnosisCSV by "|" to get each diagnosis entry
        String[] diagnosisEntries = diagnosisCSV.split("\\|");

        // Loop through each diagnosis entry
        for (String diagnosis : diagnosisEntries) {
//            System.out.println("Individual Diagnosis Entry:");
//            System.out.println(diagnosis.trim());

            // Split the diagnosis entry into its fields (patientID, description, date, prescriptions)
            String[] diagnosisFields = diagnosis.split(";");
            
            if (diagnosisFields.length >= 3) {  // Ensure there are enough fields
                String patientID = diagnosisFields[0].trim();
                String diagnosisDescription = diagnosisFields[1].trim();
                LocalDateTime diagnosisDate = LocalDateTime.parse(diagnosisFields[2].trim(), DATE_TIME_FORMATTER);

//                System.out.println("Patient ID: " + patientID);
//                System.out.println("Description: " + diagnosisDescription);
//                System.out.println("Date: " + diagnosisDate);

                // Parse prescriptions if they exist
                List<Prescription> prescriptions = new ArrayList<>();
                if (diagnosisFields.length > 3) {
                    String prescriptionsCSV = diagnosisFields[3].trim();

                    // Split the prescriptions by "^"
                    String[] prescriptionEntries = prescriptionsCSV.split("\\^");
                    for (String prescriptionData : prescriptionEntries) {
                        System.out.println("Prescription Entry: " + prescriptionData.trim());
                        prescriptions.add(parsePrescription(prescriptionData.trim()));
                    }
                }

                // Create Diagnosis object and add it to the list
                diagnosisList.add(new Diagnosis(patientID, diagnosisDescription, diagnosisDate, prescriptions));
            } else {
                System.out.println("Error: Diagnosis entry missing fields.");
            }
            
            System.out.println();  // For spacing between entries
        }

//        System.out.println();
        return diagnosisList;
    }

    // Helper method to parse individual Prescription entries
    private static Prescription parsePrescription(String prescriptionData) {
        String[] fields = prescriptionData.split("&");
        List<PrescribedMedication> medications = new ArrayList<>();

        for (String medicationData : fields) {
            String[] medFields = medicationData.split("#");
            if (medFields.length == 6) {
                String medicineName = medFields[0].trim();
                int quantity = Integer.parseInt(medFields[1].trim());
                LocalDateTime startDate = LocalDateTime.parse(medFields[2].trim(), DATE_TIME_FORMATTER);
                LocalDateTime endDate = LocalDateTime.parse(medFields[3].trim(), DATE_TIME_FORMATTER);
                PrescriptionStatus status = PrescriptionStatus.valueOf(medFields[4].trim().toUpperCase());
                String dosage = medFields[5].trim();

                Medicine medicine = new Medicine(medicineName, "UnknownID", "UnknownManufacturer", LocalDateTime.now().plusYears(1), 0);
                medications.add(new PrescribedMedication(medicine, quantity, startDate, endDate, status, dosage));
            } else {
                System.out.println("Error: Missing fields in medication data: " + medicationData);
            }
        }

        return new Prescription(medications, LocalDateTime.now());  // Replace LocalDateTime.now() with appropriate prescription date if available
    }
}
