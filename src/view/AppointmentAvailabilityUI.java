package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controller.RecordsController;
import enums.AppointmentStatus;
import helper.DateTimePicker;
import helper.Helper;
import model.AppointmentRecord;
import model.Doctor;
import model.RecordStatusType;
import repository.RecordFileType;
import repository.RecordsRepository;

public class AppointmentAvailabilityUI extends MainUI {
    private Doctor doctor;

    public AppointmentAvailabilityUI(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    protected void printChoice() {
        System.out.println("Appointment Availability Menu:");
        System.out.println("1. Set Availability for Appointments");
        System.out.println("2. Back to Doctor Dashboard");
    }

    @Override
    public void start() {
        int choice;
        do {
            printChoice();
            choice = Helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> setAvailabilityForAppointments();
                case 2 -> System.out.println("Returning to Doctor Dashboard...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 2);
    }

    public void setAvailabilityForAppointments() {
        List<AppointmentRecord> availableAppointments = new ArrayList<>();
        AppointmentStatus status = AppointmentStatus.AVAILABLE;

        System.out.println("Enter your availability. Type 'done' when finished.");

        while (true) {
            if (!Helper.promptConfirmation("add available time slot")) {
                break;
            }
            // Prompt for location
            String location = Helper.readString("Enter the location (e.g., 'Level 2 - Heart Clinic'): ");

            LocalDateTime appointmentDateTime = DateTimePicker.pickDateTime("Enter the appointment date and time:");
            

            // Create appointment record
            AppointmentRecord appointment = new AppointmentRecord(
                    RecordsController.generateRecordID(RecordFileType.APPOINTMENT_RECORDS),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    RecordStatusType.ACTIVE,
                    null,
                    null,
                    doctor.getUID(),
                    appointmentDateTime,
                    location,
                    status,
                    null
            );

            // Add and save the appointment
            availableAppointments.add(appointment);
            RecordsRepository.APPOINTMENT_RECORDS_RECORDID.put(appointment.getRecordID(), appointment);

            System.out.printf("Created and saved appointment for %s at %s\n",
                    appointmentDateTime.toLocalDate(), appointmentDateTime.toLocalTime());
        }

        displayAvailabilitySummary(availableAppointments);
        RecordsRepository.saveAllRecordFiles();
    }

    private void displayAvailabilitySummary(List<AppointmentRecord> availableAppointments) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("\n--- Appointment Availability Summary ---");

        for (AppointmentRecord appointment : availableAppointments) {
            System.out.printf("Date: %s, Time: %s, Location: %s, Record ID: %s, Doctor ID: %s\n",
                    appointment.getAppointmentTime().toLocalDate(),
                    appointment.getAppointmentTime().format(dateTimeFormatter),
                    appointment.getLocation(),
                    appointment.getRecordID(),
                    appointment.getDoctorID());
        }

        System.out.println("---------------------------------------");
    }
}
