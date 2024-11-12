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
}
