package model;

import java.time.LocalDateTime;

public class AppointmentRecord extends HMSRecords {
    private LocalDateTime appointmentTime;


    private String patientID;

    // Constructor
    public AppointmentRecord(String recordID,  LocalDateTime createdDate, LocalDateTime updatedDate,
                             RecordStatusType recordStatus, String patientID,
                             LocalDateTime appointmentTime) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.appointmentTime = appointmentTime;
    }

    // Getters and Setters
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public String getPatientID() {
        return patientID;
    }
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

}
