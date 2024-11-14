package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import enums.AppointmentOutcomeStatus;
import model.*;
import repository.*;
import controller.MedicineController;
import enums.AppointmentStatus;
import enums.PrescriptionStatus;
import helper.Helper;

public class AppointmentRecordOutcomeUI extends MainUI {
    private Doctor doctor;

    public AppointmentRecordOutcomeUI(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    protected void printChoice() {
        System.out.println("Appointment Outcome Menu:");
        System.out.println("1. Record Appointment Outcome");
        System.out.println("2. Back to Doctor Dashboard");
    }

    @Override
    public void start() {
        int choice = 0;
        do {
            printChoice();
            choice = Helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> recordAppointmentOutcome();
                case 2 -> System.out.println("Returning to Doctor Dashboard...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 2);
    }

    public void recordAppointmentOutcome() {
        // prompt doctor to select appointment outcome record that is incompleted,
        // then only update service etc, then set to completed
    	System.out.println("\n--- Incompleted Appointments for Dr. " + doctor.getFullName() + " ---");

        ArrayList<AppointmentOutcomeRecord> incompletedAppointments = new ArrayList<>();
        int index = 1;

        for (ArrayList<AppointmentOutcomeRecord> appointments : AppointmentOutcomeRecordRepository.patientOutcomeRecords.values()) {
            for (AppointmentOutcomeRecord appointment : appointments) {
                if (doctor.getUID().equals(appointment.getDoctorID())
                    && appointment.getAppointmentOutcomeStatus() == AppointmentOutcomeStatus.INCOMPLETED) {
                    incompletedAppointments.add(appointment);
                    System.out.printf("%d. Appointment on %s at %s with Patient ID: %s%n",
                        index,
                        appointment.getAppointmentTime().getDayOfWeek(),
                        appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        appointment.getPatientID());
                    index++;
                }
            }
        }


        if (incompletedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        // Prompt the user to select an appointment
        int selectedIndex = Helper.readInt("\nEnter the number of the appointment to record outcome: ");
        if (selectedIndex < 1 || selectedIndex > incompletedAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // Process the selected appointment outcome
        AppointmentOutcomeRecord selectedAppointment = incompletedAppointments.get(selectedIndex - 1);
        processOutcome(selectedAppointment);
    }

    private void processOutcome(AppointmentOutcomeRecord appointment) {
        System.out.println("\n--- Select Diagnosis for Patient (ID: " + appointment.getPatientID() + ") ---");

        String typeOfService = Helper.readString("Enter the type of service: ");
        String consultationNotes = Helper.readString("Enter your consultation notes: ");
        
        appointment.setTypeOfService(typeOfService);
        appointment.setConsultationNotes(consultationNotes);
        appointment.setAppointmentOutcomeStatus(AppointmentOutcomeStatus.COMPLETED); 

        AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
        System.out.println("Appointment outcome recorded and saved successfully.");
    }



}
