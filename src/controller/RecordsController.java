// package controller;

// public interface RecordsController {
// 	void addRecord();
// 	void updateRecord();
// 	void deleteRecord();
// 	void getRecord();
// }
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
	
	
	public String generateRecordID(RecordFileType RecType) {
		return switch (RecType) {
			case MEDICAL_RECORDS -> "MR-" + System.currentTimeMillis();
			case APPOINTMENT_RECORDS -> "A-" + System.currentTimeMillis();
			case PAYMENT_RECORDS -> "P-" + System.currentTimeMillis();
			default -> "R-" + System.currentTimeMillis();
		};
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
	public void createMedicalRecord(
            LocalDateTime createdDate, 
            LocalDateTime updatedDate,
            RecordStatusType recordStatus,
            String patientID,
            String doctorID,
            String bloodType,
            ArrayList<model.DiagnosisRecord> diagnosisList
			) {
		MedicalRecord mr = new MedicalRecord(
				generateRecordID(RecordFileType.MEDICAL_RECORDS),
				createdDate,
				updatedDate,
				recordStatus,
				patientID,
				doctorID,
				bloodType,
				diagnosisList
				);
		RecordsRepository.MEDICAL_RECORDS.put(mr.getRecordID(), mr);
		
		
	}	
	public void updateRecord() {
		
	}
	public void deleteRecord() {
		
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
