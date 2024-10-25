package model;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalRecord extends HMSRecords {
    private String diagnosis;
    private String treatmentPlan;
    private String MRID;
    private List<Medication> medicationList;

    // Constructor
    public MedicalRecord(String recordID, Doctor createdBy, LocalDateTime createdDate, LocalDateTime updatedDate,
                         RecordStatusType recordStatus, String description, Patient patient,
                         String diagnosis, String treatmentPlan, String MRID, List<Medication> medicationList) {
        super(recordID, createdBy, createdDate, updatedDate, recordStatus, description, patient);
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.MRID = MRID;
        this.medicationList = medicationList;
    }

    // Getters and Setters
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getMRID() {
        return MRID;
    }

    public void setMRID(String MRID) {
        this.MRID = MRID;
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }
}
