package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AppointmentRecord;
import model.MedicalRecord;
import model.PaymentRecord;
import model.RecordStatusType;
import repository.RecordsRepository;
import repository.RecordFileType;

public class RecordsController {
	
	
	// public String generateRecordID(RecordFileType RecType) {
	// 	return switch (RecType) {
	public static String generateRecordID(RecordFileType RecType) {
		switch (RecType) {
			case APPOINTMENT_RECORDS:
				return "A-" + System.currentTimeMillis();
			case PAYMENT_RECORDS:
				return "P-" + System.currentTimeMillis();
			case MEDICAL_RECORDS:
				return "MR-" + System.currentTimeMillis();
			default:
				return "R-" + System.currentTimeMillis();
		}
	}
	
	
	public Boolean checkRecordsDuplication(String UID, RecordFileType recType) {
		switch(recType){
			case MEDICAL_RECORDS:
				return RecordsRepository.MEDICAL_RECORDS.get(UID) != null;
			case APPOINTMENT_RECORDS:
				return RecordsRepository.APPOINTMENT_RECORDS.get(UID) != null;
			case PAYMENT_RECORDS:
				return RecordsRepository.PAYMENT_RECORDS.get(UID) != null;
			default:
				return true;
		}
	}
	
	//automatically creates ID
	public void addMedicalRecord(MedicalRecord mr) {
		RecordsRepository.MEDICAL_RECORDS.put(mr.getRecordID(), mr);
		
		
	}	
	public void updateRecord() {
		
	}
	public void deleteRecord() {
		
	}
	
public MedicalRecord getMedicalRecordsByPatientID(String patientID) {
    if (RecordsRepository.isRepoLoad()) {
        for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
            if (record.getPatientID().equals(patientID)) {
                return record;
            }
        }
    }
    return null;
}

public MedicalRecord getMedicalRecordsByDoctorID(String doctorID) {
    if (RecordsRepository.isRepoLoad()) {
        for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
            if (record.getDoctorID().equals(doctorID)) {
                return record; // Return the record if found
            }
        }
    }
    return null;
}

	
	
	// Always check for null when use this function
	public MedicalRecord getMedicalRecordbyID(String RecordID) {
		if (RecordsRepository.isRepoLoad())
			return RecordsRepository.MEDICAL_RECORDS.get(RecordID);
		else 
			return null;
	}
	
	// Always check for null when use this function
	public AppointmentRecord getDiagnosisRecordbyID(String RecordID) {
		if (RecordsRepository.isRepoLoad())
			return RecordsRepository.APPOINTMENT_RECORDS.get(RecordID);
		else 
			return null;
	}
	
	// Always check for null when use this function
	public PaymentRecord getPaymentRecordbyID(String RecordID) {
		if (RecordsRepository.isRepoLoad())
			return RecordsRepository.PAYMENT_RECORDS.get(RecordID);
		else 
			return null;
	}
}
