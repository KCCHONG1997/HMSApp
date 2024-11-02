package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;

public class AppointmentRecord extends HMSRecords {
    private LocalDateTime appointmentTime;
    private String location;
    private AppointmentStatus appointmentStatus;

    // Constructor
    public AppointmentRecord(String recordID, Doctor createdBy, LocalDateTime createdDate, LocalDateTime updatedDate,
                             RecordStatusType recordStatus, String description, Patient patient,
                             LocalDateTime appointmentTime, String location, AppointmentStatus appointmentStatus) {
        super(recordID, createdBy, createdDate, updatedDate, recordStatus, description, patient);
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.appointmentStatus = appointmentStatus;
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
    
    public AppointmentStatus appointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
    
    
}
