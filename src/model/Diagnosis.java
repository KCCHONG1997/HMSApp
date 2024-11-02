package model;

import java.time.LocalDateTime;
import java.util.List;

public class Diagnosis {
    private String patientID;
    private String diagnosisDescription;
    private LocalDateTime diagnosisDate;
    private List<Prescription> prescriptions;

    // Constructor
    public Diagnosis(String patientID, String diagnosisDescription, LocalDateTime diagnosisDate, List<Prescription> prescriptions) {
        this.patientID = patientID;
        this.diagnosisDescription = diagnosisDescription;
        this.diagnosisDate = diagnosisDate;
        this.prescriptions = prescriptions;
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDiagnosisDescription() {
        return diagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }

    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
