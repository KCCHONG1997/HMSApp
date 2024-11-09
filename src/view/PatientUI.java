package view;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import HMSApp.HMSMain;
import controller.AuthenticationController;
import controller.DoctorController;
import controller.HMSPersonnelController;
import controller.RecordsController;
import model.AppointmentRecord;
import model.MedicalRecord;
import model.Patient;
import repository.RecordsRepository;
import enums.AppointmentStatus;

public class PatientUI extends MainUI {
    
    
    @Override
    public void printChoice() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    public void start() {
        showPatientDashboard();
    }

    public void showPatientDashboard() {
        String patientUID = AuthenticationController.cookie.getUid();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            printChoice();
            choice = sc.nextInt();
            switch (choice) {
                case 1: 
                    viewPatientMedicalRecord(patientUID);
                    break;
                case 2: 
                    updatePatientParticularView();
                    break;
                case 3: 
                    viewAvailableAppointmentSlots();
                    break;
                case 4: 
                    // Code for scheduling an appointment
                    break;
                case 5: 
                    // Code for rescheduling an appointment
                    break;
                case 6: 
                    // Code for canceling an appointment
                    break;
                case 7: 
                    // Code for viewing scheduled appointments
                    break;
                case 8: 
                    // Code for viewing past appointment outcomes
                    break;
                case 9: 
                    System.out.println("Logging out...");
                    HMSMain.main(null);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        } while (choice != 9);
        
        sc.close();
    }

    // Method to view a patient's medical record using MedicalRecordUI
    public static void viewPatientMedicalRecord(String patientID) {
        RecordsController rc = new RecordsController();
        MedicalRecord medicalRecord = rc.getMedicalRecordsByPatientID(patientID);

        if (medicalRecord != null) {
            MedicalRecordUI recordUI = new MedicalRecordUI(medicalRecord);
            recordUI.displayMedicalRecordInBox();
        } else {
            System.out.println("\n=====================================");
            System.out.println("No medical record found for this patient.");
            System.out.println("=====================================\n");
        }
    }
    
    public static void updatePatientParticularView() {
        // Fetch the patient object using UID
        String patientUID = AuthenticationController.cookie.getUid();
        Patient patient = HMSPersonnelController.getPatientById(patientUID);

        if (patient != null) {
            UpdatePatientParticularsUI updateView = new UpdatePatientParticularsUI(patient);
            updateView.start();
        } else {
            System.out.println("Patient not found. Unable to update particulars.");
        }
    }

    public static void viewAvailableAppointmentSlots() {
        System.out.println("\n--- Available Appointment Slots ---");

        boolean found = false;
        for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
                found = true;
                String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

                System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() +
                        ", Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        ", Location: " + appointment.getlocation() +
                        ", Doctor: " + doctorName);
            }
        }

        if (!found) {
            System.out.println("No appointments found");
        }

        System.out.println("---------------------------------------");
    }
}
