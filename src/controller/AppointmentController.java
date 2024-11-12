package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import enums.AppointmentStatus;
import model.AppointmentOutcomeRecord;
import model.AppointmentRecord;
import model.Doctor;
import model.MedicalRecord;
import model.PaymentRecord;
import model.RecordStatusType;
import repository.AppointmentOutcomeRecordRepository;
import repository.RecordFileType;
import repository.RecordsRepository;
import java.util.Comparator;

public class AppointmentController {

	private static final System.Logger logger = System.getLogger(RecordsController.class.getName());

    public static String generateRecordID(RecordFileType recType) {		
    	UUID uuid = UUID.randomUUID();
    	String uuidAsString = uuid.toString();
        switch (recType) {
            case APPOINTMENT_OUTCOME_RECORDS:
                return "AO-" + uuidAsString;
            case DIAGNOSIS_RECORDS:
            	return "DIAG-" + uuidAsString;
            case MEDICINE_RECORDS:
            	return "MR-" + uuidAsString;
            default:
                return "R-" + uuidAsString;
        }
    }

    public static ArrayList<AppointmentRecord> getAppointmentsByDoctorAndPatient(String doctorID, String patientID, AppointmentStatus status) {
        ArrayList<AppointmentRecord> filteredAppointments = new ArrayList<>();

        // Loop through all appointment records
        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
            // Check if appointment matches both the doctorID and patientID, and has a CONFIRMED status
            if (doctorID.equals(appointment.getDoctorID()) &&
                    patientID.equals(appointment.getPatientID()) &&
                    appointment.getAppointmentStatus() == status) {

                // Add to filtered list
                filteredAppointments.add(appointment);
            }
        }

        return filteredAppointments;

        // If no appointments match the criteria
    }



    public static AppointmentRecord getEarliestAppointment(ArrayList<AppointmentRecord> appointments) {
        if (appointments.isEmpty()) {
            return null; // Return null if there are no pending appointments
        }

        // Sort appointments by AppointmentTime in ascending order
        appointments.sort(Comparator.comparing(AppointmentRecord::getAppointmentTime));

        // Return the first (earliest) appointment
        return appointments.get(0);
    }

    public static AppointmentRecord retrieveEarliestPendingAppointmentRecord(String doctorID, String patientID){
        ArrayList<AppointmentRecord> pendingAppointments ;
        pendingAppointments = AppointmentController.getAppointmentsByDoctorAndPatient(doctorID,
                                                                                    patientID,
                                                                                    AppointmentStatus.PENDING);

        AppointmentRecord currentAppointmentRecord = AppointmentController.getEarliestAppointment(pendingAppointments);
        return currentAppointmentRecord;
    }

}