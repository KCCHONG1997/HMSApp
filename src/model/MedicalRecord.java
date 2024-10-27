package model;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalRecord extends HMSRecords {
    private String diagnosis;
    private String treatmentPlan;
    private String MRID;
    private List<PrescriptionItem> prescription;

    public MedicalRecord(String recordID, Doctor createdBy,
                         LocalDateTime createdDate, LocalDateTime updatedDate,
                         RecordStatusType recordStatus, String description,
                         Patient patient, String diagnosis, String treatmentPlan,
                         String MRID, List<PrescriptionItem> prescription) {
        super(recordID, createdBy, createdDate, updatedDate, recordStatus, description, patient);
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.MRID = MRID;
        this.prescription = prescription;
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

    public List<PrescriptionItem> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<PrescriptionItem> prescription) {
        this.prescription = prescription;
    }
}
