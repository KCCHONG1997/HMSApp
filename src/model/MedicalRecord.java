package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MedicalRecord extends HMSRecords {
    private String bloodType;
    private ArrayList <DiagnosisRecord> Diagnosis;


    private String patientID;
    private String doctorID;



    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public ArrayList<model.DiagnosisRecord> getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(ArrayList<model.DiagnosisRecord> diagnosis) {
        Diagnosis = diagnosis;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }



    public MedicalRecord(String recordID,
                         LocalDateTime createdDate, LocalDateTime updatedDate,
                         RecordStatusType recordStatus,String patientID,String doctorID,
                         String bloodType,ArrayList<model.DiagnosisRecord> diagnosis) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.bloodType = bloodType;
        this.Diagnosis= diagnosis;

    }


}
