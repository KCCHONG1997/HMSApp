package model;

import java.time.LocalDateTime;
import java.util.Scanner;
import enums.AppointmentStatus;
import repository.AppointmentOutcomeRecordRepository;

public class AppointmentRecord extends HMSRecords {
    private LocalDateTime appointmentTime;
    private String location;
    private AppointmentStatus appointmentStatus;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;
    private String patientID;//ck

    // Constructor
    public AppointmentRecord(String recordID, 
                                String patientID, String doctorID, LocalDateTime createdDate, LocalDateTime updatedDate,
                             RecordStatusType recordStatus, String description,
                             LocalDateTime appointmentTime, String location, AppointmentStatus appointmentStatus, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        super(recordID, doctorID, createdDate, updatedDate, recordStatus, description );
    
    

    // // Constructor
    // public AppointmentRecord(String recordID,  LocalDateTime createdDate, LocalDateTime updatedDate,
    //                          RecordStatusType recordStatus, String patientID,
    //                          LocalDateTime appointmentTime) {
    //     super(recordID, createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
 }

    // Getters and Setters
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }
    
    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
    	 this.appointmentStatus = appointmentStatus;
    	 
    	 Scanner scanner = new Scanner(System.in);

    	if (appointmentStatus == AppointmentStatus.COMPLETED) {
    	    System.out.println("The appointment is completed. Would you like to");
    	    System.out.println("1) Record Appointment Outcome");

    	    int choice = scanner.nextInt();

    	    switch (choice) {

    	        	
    	        case 1:
    	            System.out.println("Please enter the type of service:");
    	            String typeOfService = scanner.nextLine();

    	            System.out.println("Please enter the prescription (as a string):");
    	            String prescriptionInput = scanner.nextLine(); 

    	            System.out.println("Please enter the consultation notes:");
    	            String consultationNotes = scanner.nextLine();

                    AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord();
                    outcomeRecord.setTypeOfService(typeOfService);
                   // outcomeRecord.setPrescription(new Prescription(prescriptionInput)); // Assuming Prescription has a constructor
                    outcomeRecord.setConsultationNotes(consultationNotes);
                    
    	          
    	            AppointmentOutcomeRecordRepository.saveOutcomeRecord(recordID, outcomeRecord);
    	            System.out.println("Appointment outcome recorded successfully!");
    	            break;


    	        default:
    	            System.out.println("Invalid choice. No action taken.");
    	            break;
    	    }
    	}
    	 
   

    }
    
    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return appointmentOutcomeRecord;
    }

    public void setAppointmentOutcomeRecord( AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }
    
    
    
    
    public String getPatientID() {
        return patientID;
    }
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

}
