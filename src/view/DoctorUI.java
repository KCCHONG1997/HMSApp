package view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import HMSApp.HMSMain;
import controller.AuthenticationController;
import controller.RecordsController;
import enums.AppointmentStatus;
import model.AppointmentRecord;
import model.Doctor;
import model.MedicalRecord;
import repository.RecordsRepository;

public class DoctorUI extends MainUI {
    private Doctor doctor;

    public DoctorUI(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    protected void printChoice() {
        System.out.printf("Welcome! Dr. --- %s ---\n", doctor.getFullName());
        printBreadCrumbs("HMS App UI > Doctor Dashboard");
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

    public void start() {
        showDoctorDashboard();
    }

    public void showDoctorDashboard() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            printChoice();
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewPatientRecords(); // Use the modified method
                    break;
                case 2:
                    // Code for updating patient medical records
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    availabilityForAppointments(doctor);
                    break;
                case 5:
                    // Code for accepting or declining appointment requests
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    // Code for recording appointment outcome
                    break;
                case 8:
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 8);

        sc.close(); // Close the Scanner
    }

    private void viewPatientRecords() {
        printBreadCrumbs("HMS App UI > Doctor Dashboard > View Patient Records");
        RecordsController rc = new RecordsController();
        ArrayList<MedicalRecord> records = rc.getMedicalRecordsByDoctorID(AuthenticationController.cookie.getUid());

        if (records.isEmpty()) {
            System.out.println("No medical records found for this doctor.");
            return;
        }

        for (MedicalRecord record : records) {
            MedicalRecordUI recordUI = new MedicalRecordUI(record); // Instantiate MedicalRecordUI
            recordUI.displayMedicalRecordInBox(); // Display the medical record
        }
    }
    
    public static void availabilityForAppointments(Doctor doctor) {
        // Code for setting availability for appointments
    }

    public void viewPersonalSchedule() {
        if (doctor == null) {
            System.out.println("Doctor information is not available.");
            return;
        }

        System.out.println("\n--- Schedule for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");

        boolean found = false;
        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
            if (appointment.getDoctorID().equals(doctor.getUID())) {
                found = true;
                System.out.println("Appointment Record:");
                System.out.println("  - Appointment ID: " + appointment.getRecordID());
                System.out.println("  - Day: " + appointment.getAppointmentTime().getDayOfWeek());
                System.out.println("  - Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                System.out.println("  - Location: " + appointment.getlocation());
                System.out.println("  - Status: " + appointment.getAppointmentStatus());
                System.out.println("  - Patient ID: " + appointment.getPatientID());
                System.out.println("  - Outcome Record: " + (appointment.getAppointmentOutcomeRecord() != null ? appointment.getAppointmentOutcomeRecord().toString() : "N/A"));
                System.out.println("---------------------------------------");
            }
        }

        if (!found) {
            System.out.println("No appointments found for this doctor.");
        }

        System.out.println("---------------------------------------");
    }

    public void viewUpcomingAppointments() {
        System.out.println("\n--- Upcoming Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");

        boolean found = false;
        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
            if (appointment.getDoctorID().equals(doctor.getUID()) && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                found = true;
                System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() +
                    ", Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    ", Location: " + appointment.getlocation() + 
                    ", Patient ID: " + appointment.getPatientID());
            }
        }

        if (!found) {
            System.out.println("No upcoming appointments found for this doctor.");
        }

        System.out.println("---------------------------------------");
    }
}
