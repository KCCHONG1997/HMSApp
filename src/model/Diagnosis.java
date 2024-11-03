package model;


import java.time.LocalDateTime;

public class Diagnosis {

    private String patientID;
    private String diagnosisID;
    private LocalDateTime diagnosisDate;
    private TreatmentPlans TreatmentPlans;
    private String diagnosisDescription;

    public Diagnosis(String patientID, String diagnosisID, LocalDateTime diagnosisDate, model.TreatmentPlans treatmentPlans, String diagnosisDescription) {
        this.patientID = patientID;
        this.diagnosisID = diagnosisID;
        this.diagnosisDate = diagnosisDate;
        TreatmentPlans = treatmentPlans;
        this.diagnosisDescription = diagnosisDescription;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public TreatmentPlans getTreatmentPlans() {
        return TreatmentPlans;
    }

    public void setTreatmentPlans(TreatmentPlans treatmentPlans) {
        TreatmentPlans = treatmentPlans;
    }

    public String getDiagnosisDescription() {
        return diagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        this.diagnosisDescription = diagnosisDescription;
    }
}

