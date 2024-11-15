package controller;

import java.time.LocalDateTime;
import java.util.UUID;

import enums.RecordFileType;

import java.util.ArrayList;

import model.AppointmentOutcomeRecord;
import model.AppointmentRecord;
import model.Diagnosis;
import model.MedicalRecord;
import model.PaymentRecord;
import model.RecordStatusType;
import repository.RecordsRepository;
import repository.AppointmentOutcomeRecordRepository;
/**
 * Controller class responsible for managing and handling various records (Medical, Appointment, Payment) in the system.
 * This class provides functionality to generate record IDs, add, update, delete, and retrieve records from the repository.
 */
public class RecordsController {

    private static final System.Logger logger = System.getLogger(RecordsController.class.getName());
    /**
     * Generates a unique record ID based on the record type.
     * @param recType the type of record (Appointment, Payment, or Medical)
     * @return a unique record ID as a string
     */
    public static String generateRecordID(RecordFileType recType) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        switch (recType) {
            case APPOINTMENT_RECORDS:
                return "A-" + uuidAsString;
            case PAYMENT_RECORDS:
                return "P-" + uuidAsString;
            case MEDICAL_RECORDS:
                return "MR-" + uuidAsString;
            default:
                return "R-" + uuidAsString;
        }
    }
    /**
     * Checks if a record of the specified type already exists for a given UID.
     * @param UID the unique identifier of the record
     * @param recType the type of record to check (Appointment, Payment, or Medical)
     * @return true if a record exists for the given UID, otherwise false
     */
    public Boolean checkRecordsDuplication(String UID, RecordFileType recType) {
        switch (recType) {
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
    /**
     * Adds a new medical record to the repository.
     * @param mr the MedicalRecord object to be added
     */
    public void addMedicalRecord(MedicalRecord mr) {
        RecordsRepository.MEDICAL_RECORDS.put(mr.getRecordID(), mr);
    }
    /**
     * Updates an existing record based on the record type and provided details.
     * @param recordID the ID of the record to be updated
     * @param recType the type of record (Appointment, Payment, or Medical)
     * @param status the status to be set for the record
     * @param doctorID the doctor ID to be set for the record
     * @param patientID the patient ID to be set for the record
     * @param updatedDate the date and time when the record was updated
     * @return true if the record was successfully updated, otherwise false
     */
    public Boolean updateRecord(String recordID, RecordFileType recType, String status, String doctorID,
            String patientID, LocalDateTime updatedDate) {
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

    private Boolean updateMedicalRecord(String recordID, String status, String doctorID, String patientID,
            LocalDateTime updatedDate) {
        MedicalRecord record = RecordsRepository.MEDICAL_RECORDS.get(recordID);
        if (record != null) {
            if (status != null)
                record.setRecordStatus(RecordStatusType.valueOf(status));
            if (doctorID != null)
                record.setDoctorID(doctorID);
            if (patientID != null)
                record.setPatientID(patientID);
            record.setUpdatedDate(updatedDate);

            RecordsRepository.saveAllRecordFiles();
            logger.log(System.Logger.Level.INFO, "Medical Record updated successfully with ID: {0}", recordID);
            return true;
        }
        logger.log(System.Logger.Level.WARNING, "Medical Record with ID {0} not found.", recordID);
        return false;
    }

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
    /**
     * Deletes a record based on the record ID.
     * @param recordID the ID of the record to be deleted
     * @return true if the record was successfully deleted, otherwise false
     */
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
    /**
     * Retrieves a medical record by the patient ID.
     * @param patientID the ID of the patient whose medical record is to be retrieved
     * @return the medical record for the given patient ID, or null if not found
     */
    public MedicalRecord getMedicalRecordsByPatientID(String patientID) {
        if (RecordsRepository.isRepoLoad()) {
            for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
                System.out.println(record.getPatientID());
                if (record.getPatientID().equals(patientID)) {
                    return record;
                }
            }
        }
        return null;
    }
    /**
     * Retrieves a list of medical records by the doctor ID.
     * @param doctorID the ID of the doctor whose medical records are to be retrieved
     * @return a list of medical records for the given doctor ID
     */
    public ArrayList<MedicalRecord> getMedicalRecordsByDoctorID(String doctorID) {
        ArrayList<MedicalRecord> recordsByDoctor = new ArrayList<>(); // Initialize an empty list

        if (RecordsRepository.isRepoLoaded()) {
            for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
                if (record.getDoctorID().equals(doctorID)) {
                    recordsByDoctor.add(record); // Add matching records to the list
                }
            }
        }
        return recordsByDoctor; // Return the list, even if it might be empty
    }
    /**
     * Retrieves a medical record by the record ID.
     * @param recordID the ID of the medical record to retrieve
     * @return the medical record corresponding to the given record ID, or null if not found
     */
    public MedicalRecord getMedicalRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.MEDICAL_RECORDS.get(recordID);
        else
            return null;
    }
    /**
     * Retrieves an appointment record by the record ID.
     * @param recordID the ID of the appointment record to retrieve
     * @return the appointment record corresponding to the given record ID, or null if not found
     */
    public AppointmentRecord getDiagnosisRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.APPOINTMENT_RECORDS.get(recordID);
        else
            return null;
    }
    /**
     * Retrieves a payment record by the record ID.
     * @param recordID the ID of the payment record to retrieve
     * @return the payment record corresponding to the given record ID, or null if not found
     */
    public PaymentRecord getPaymentRecordbyID(String recordID) {
        if (RecordsRepository.isRepoLoad())
            return RecordsRepository.PAYMENT_RECORDS.get(recordID);
        else
            return null;
    }

    // public ArrayList<AppointmentOutcomeRecord>
    // getAppointmentOutcomeRecordByPatientId(String patientID) {
    // ArrayList<AppointmentOutcomeRecord> records = new ArrayList<>();
    //
    // // Iterate over the patientOutcomeRecords map and collect matching records
    // for (String id :
    // AppointmentOutcomeRecordRepository.patientOutcomeRecords.keySet()) {
    // if (id.equals(patientID)) {
    // records.add(AppointmentOutcomeRecordRepository.patientOutcomeRecords.get(id));
    // }
    // }
    //
    // return records;
    // }
    /**
     * Retrieves a list of appointment outcome records for a given patient ID.
     * This method retrieves all appointment outcome records related to the patient.
     * @param patientID the ID of the patient whose appointment outcome records are to be retrieved
     * @return an ArrayList of AppointmentOutcomeRecord objects associated with the given patient ID, or an empty list if no records are found
     */
    public static ArrayList<AppointmentOutcomeRecord> getAppointmentOutcomeRecordByPatientId(String patientID) {
        // Retrieve the list of records for the given patientID, or an empty list if
        // none exists
        return AppointmentOutcomeRecordRepository.patientOutcomeRecords.getOrDefault(patientID, new ArrayList<>());
    }

}