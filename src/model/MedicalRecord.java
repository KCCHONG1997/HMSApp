package model;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalRecord extends HMSRecords {
    private String patientID;
    private String bloodType;
    private List<Diagnosis> diagnosis;

    public MedicalRecord(String recordID,
                         LocalDateTime createdDate, 
                         LocalDateTime updatedDate,
                         RecordStatusType recordStatus, 
                         String patientID, 
                         String bloodType,
                         List<Diagnosis> diagnosis
                         ) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.setPatientID(patientID);
        this.setBloodType(bloodType);
        this.diagnosis = diagnosis;
    }
    


    // Getters and Setters
    public List<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }



	public String getPatientID() {
		return patientID;
	}



	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}



	public String getBloodType() {
		return bloodType;
	}



	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

//    public String getTreatmentPlan() {
//        return treatmentPlan;
//    }
//
//    public void setTreatmentPlan(String treatmentPlan) {
//        this.treatmentPlan = treatmentPlan;
//    }
//
//    public String getMRID() {
//        return MRID;
//    }
//
//    public void setMRID(String MRID) {
//        this.MRID = MRID;
//    }

//    public List<PrescriptionItem> getPrescription() {
//        return prescription;
//    }
//
//    public void setPrescription(List<PrescriptionItem> prescription) {
//        this.prescription = prescription;
//    }
}
