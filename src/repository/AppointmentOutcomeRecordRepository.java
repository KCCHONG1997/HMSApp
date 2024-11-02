package repository;

import java.io.*;
import java.util.HashMap;
import model.AppointmentOutcomeRecord;

public class AppointmentOutcomeRecordRepository {

	 private static final String FILE_NAME = "./src/repository/data/appointment_outcomes.csv";
	    private static final HashMap<String, AppointmentOutcomeRecord> OUTCOME_RECORDS = new HashMap<>();

	    // Method to save an outcome record to the CSV file after it's created
	    public static void saveOutcomeRecord(String recordID, AppointmentOutcomeRecord outcomeRecord) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
	            writer.write(outcomeRecordToCSV(outcomeRecord));
	            writer.newLine();
	            System.out.println("Outcome record saved to file: " + FILE_NAME);
	        } catch (IOException e) {
	            System.out.println("Error saving outcome record to CSV: " + e.getMessage());
	        }
	    }

	    // Convert an AppointmentOutcomeRecord to a CSV line
	    private static String outcomeRecordToCSV(AppointmentOutcomeRecord outcomeRecord) {
	        return String.join(",",
	                outcomeRecord.getTypeOfService(),
	                outcomeRecord.getPrescription().toString(),  // Convert prescription to a suitable format
	                outcomeRecord.getConsultationNotes()
	        );
	    }

	    // Load outcome records from CSV file
	    public static void loadOutcomeRecords() {
	        // Ensure directory exists
	        File directory = new File("./src/repository/data");
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        File file = new File(FILE_NAME);
	        if (!file.exists()) {
	            try {
	                file.createNewFile();
	                System.out.println("Created empty file: " + FILE_NAME);
	            } catch (IOException e) {
	                System.out.println("Error creating file: " + e.getMessage());
	            }
	            return; // No data to load
	        }

	        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] fields = line.split(",");
	                String recordID = fields[0];
	                AppointmentOutcomeRecord outcomeRecord = csvToOutcomeRecord(fields);
	                OUTCOME_RECORDS.put(recordID, outcomeRecord);
	            }
	            System.out.println("Loaded " + OUTCOME_RECORDS.size() + " outcome records from " + FILE_NAME);
	        } catch (IOException e) {
	            System.out.println("Error reading appointment outcome records: " + e.getMessage());
	        }
	    }

	    // Convert CSV fields to AppointmentOutcomeRecord
	    private static AppointmentOutcomeRecord csvToOutcomeRecord(String[] fields) {
	        AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord();
	        outcomeRecord.setTypeOfService(fields[1]);
//	        outcomeRecord.setPrescription(new Prescription(fields[2])); // Assuming Prescription has an appropriate constructor
	        outcomeRecord.setConsultationNotes(fields[3]);
	        return outcomeRecord;
	    }

	    // Retrieve an outcome record by ID
	    public static AppointmentOutcomeRecord getOutcomeRecord(String recordID) {
	        return OUTCOME_RECORDS.get(recordID);
	    }
}
