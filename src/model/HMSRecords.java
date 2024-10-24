package model;

import java.time.LocalDateTime;

public abstract class HMSRecords {
    private String recordID;
    private Doctor createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private RecordStatusType recordStatus;
    private String description;
    private Patient patient;

    // Constructor
    public HMSRecords(String recordID, Doctor createdBy, LocalDateTime createdDate, LocalDateTime updatedDate,
                      RecordStatusType recordStatus, String description, Patient patient) {
        this.recordID = recordID;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.recordStatus = recordStatus;
        this.description = description;
        this.patient = patient;
    }

    // Getters and Setters
    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Doctor getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Doctor createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public RecordStatusType getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatusType recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
