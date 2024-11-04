package controller;

import java.time.LocalDateTime;
import model.AppointmentRecord;
import model.MedicalRecord;
import model.PaymentRecord;
import model.RecordStatusType;
import repository.RecordsRepository;
import repository.RecordFileType;

public class RecordsController {

    private static final System.Logger logger = System.getLogger(RecordsController.class.getName());

    public static String generateRecordID(RecordFileType recType) {
        switch (recType) {
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

    public void addMedicalRecord(MedicalRecord mr) {
        RecordsRepository.MEDICAL_RECORDS.put(mr.getRecordID(), mr);
    }    

    public Boolean updateRecord(String recordID, RecordFileType recType, String status, String doctorID, String patientID, LocalDateTime updatedDate) {
        if (!RecordsRepository.isRepoLoad()) {
            logger.log(System.Logger.Level.WARNING, "Repository not loaded. Cannot update record.");
            return false;
        }

        switch (recType) {
            case MEDICAL_RECORDS:
                return updateMedicalRecord(recordID, status, doctorID, patientID, updatedDate);
            case APPOINTMENT_RECORDS:
                return updateAppointmentRecord(recordID, updatedDate);
            case PAYMENT_RECORDS:
                return updatePaymentRecord(recordID, updatedDate);
            default:
                logger.log(System.Logger.Level.WARNING, "Invalid record type specified.");
                return false;
        }
    }

    private Boolean updateMedicalRecord(String recordID, String status, String doctorID, String patientID, LocalDateTime updatedDate) {
        MedicalRecord record = RecordsRepository.MEDICAL_RECORDS.get(recordID);
        if (record != null) {
            if (status != null) record.setRecordStatus(RecordStatusType.valueOf(status));
            if (doctorID != null) record.setDoctorID(doctorID);
            if (patientID != null) record.setPatientID(patientID);
            record.setUpdatedDate(updatedDate);

            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Medical Record updated successfully with ID: {0}", recordID);
            return true;
        }
        logger.log(System.Logger.Level.WARNING, "Medical Record with ID {0} not found.", recordID);
        return false;
    }

    //FIXME
    private Boolean updateAppointmentRecord(String recordID, LocalDateTime updatedDate) {
        AppointmentRecord record = RecordsRepository.APPOINTMENT_RECORDS.get(recordID);
        if (record != null) {
            record.setUpdatedDate(updatedDate);
            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Appointment Record updated successfully with ID: {0}", recordID);
            return true;
        }
        logger.log(System.Logger.Level.WARNING, "Appointment Record with ID {0} not found.", recordID);
        return false;
    }

    private Boolean updatePaymentRecord(String recordID, LocalDateTime updatedDate) {
        PaymentRecord record = RecordsRepository.PAYMENT_RECORDS.get(recordID);
        if (record != null) {
            record.setUpdatedDate(updatedDate);
            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Payment Record updated successfully with ID: {0}", recordID);
            return true;
        }
        logger.log(System.Logger.Level.WARNING, "Payment Record with ID {0} not found.", recordID);
        return false;
    }

    public Boolean deleteRecord(String recordID) {
        if (deleteMedicalRecord(recordID)) {
            return true;
        } else if (deleteAppointmentRecord(recordID)) {
            return true;
        } else if (deletePaymentRecord(recordID)) {
            return true;
        } else {
            logger.log(System.Logger.Level.WARNING, "Record with ID {0} not found for deletion.", recordID);
            return false;
        }
    }

    private Boolean deleteMedicalRecord(String recordID) {
        if (RecordsRepository.MEDICAL_RECORDS.containsKey(recordID)) {
            RecordsRepository.MEDICAL_RECORDS.remove(recordID);
            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Deleted Medical Record with ID: {0}", recordID);
            return true;
        }
        return false;
    }

    private Boolean deleteAppointmentRecord(String recordID) {
        if (RecordsRepository.APPOINTMENT_RECORDS.containsKey(recordID)) {
            RecordsRepository.APPOINTMENT_RECORDS.remove(recordID);
            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Deleted Appointment Record with ID: {0}", recordID);
            return true;
        }
        return false;
    }

    private Boolean deletePaymentRecord(String recordID) {
        if (RecordsRepository.PAYMENT_RECORDS.containsKey(recordID)) {
            RecordsRepository.PAYMENT_RECORDS.remove(recordID);
            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Deleted Payment Record with ID: {0}", recordID);
            return true;
        }
        return false;
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
                    return record;
                }
            }
        }
        return null;
    }

    public MedicalRecord getMedicalRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.MEDICAL_RECORDS.get(recordID);
        else 
            return null;
    }

    public AppointmentRecord getDiagnosisRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.APPOINTMENT_RECORDS.get(recordID);
        else 
            return null;
    }

    public PaymentRecord getPaymentRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.PAYMENT_RECORDS.get(recordID);
        else 
            return null;
    }
}
