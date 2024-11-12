package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.RecordsController;
import repository.RecordFileType;

public class MedicalRecord extends HMSRecords {

    private String patientID;
    private String doctorID;
    private String bloodType;
    private ArrayList <Diagnosis> Diagnosis;

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


    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public ArrayList<model.Diagnosis> getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(ArrayList<model.Diagnosis> diagnosis) {
        Diagnosis = diagnosis;
    }


    public MedicalRecord(String recordID,
                         LocalDateTime createdDate, LocalDateTime updatedDate,
                         RecordStatusType recordStatus,String patientID,String doctorID,
                         String bloodType,ArrayList<model.Diagnosis> diagnosis) {
        super(recordID, createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.bloodType = bloodType;
        this.Diagnosis= diagnosis;
                         }

	public void addDiagnosis(model.Diagnosis newDiagnosis) {

		this.Diagnosis.add(newDiagnosis);
	}



}
