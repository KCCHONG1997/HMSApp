package model;

import java.time.LocalDateTime;

public class AppointmentRecord extends HMSRecords {
    private LocalDateTime appointmentTime;

    // Constructor
    public AppointmentRecord(String recordID, Doctor createdBy, LocalDateTime createdDate, LocalDateTime updatedDate,
                             RecordStatusType recordStatus, String description, Patient patient,
                             LocalDateTime appointmentTime) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.appointmentTime = appointmentTime;
    }

    // Getters and Setters
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
