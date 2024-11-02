package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;

public class AppointmentOutcomeRecord {
    private String typeOfService;
    private Prescription prescription;
    private String consultationNotes;
    private AppointmentRecord appointmentRecord; // Composition

    public AppointmentOutcomeRecord(String recordID, Doctor createdBy, LocalDateTime createdDate,
            LocalDateTime updatedDate, RecordStatusType recordStatus, String description, Patient patient,
            LocalDateTime appointmentTime, String location, AppointmentStatus appointmentStatus, 
            String typeOfService, Prescription prescription, String consultationNotes) {
        
        // Creating an AppointmentRecord instance within AppointmentOutcomeRecord
        this.appointmentRecord = new AppointmentRecord(recordID, createdBy, createdDate, updatedDate, 
                recordStatus, description, patient, appointmentTime, location, appointmentStatus);
        
        this.typeOfService = typeOfService;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public AppointmentRecord getAppointmentRecord() {
        return appointmentRecord; // Method to access the AppointmentRecord instance
    }
}
