package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import HMSApp.HMSMain;
import controller.RecordsController;
import enums.AppointmentStatus;
import model.AppointmentOutcomeRecord;
import model.AppointmentRecord;
import model.Doctor;
import model.RecordStatusType;
import repository.RecordFileType;
import repository.RecordsRepository;

public class DoctorUI extends MainUI {
	private  Doctor doctor;

	public DoctorUI(Doctor doctor) {
		this.doctor = doctor;
		RecordsRepository.loadAllRecordFiles();
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
				// Code for viewing patient medical records
				break;
			case 2:
				// Code for updating patient medical records
				break;
			case 3:
				// Code for viewing personal schedule
				viewPersonalSchedule();
				break;
			case 4:
				// Code for setting availability for appointments
				availabilityForAppointments(doctor);
				break;
			case 5:
				// Code for accepting or declining appointment requests
				break;
			case 6:
				viewUpcomingAppointments();
				// Code for viewing upcoming appointments
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

	public static void availabilityForAppointments(Doctor doctor) {
	    Scanner scanner = new Scanner(System.in);
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH");
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	    List<AppointmentRecord> availableAppointments = new ArrayList<>();

	    System.out.println("Select your available days (type 'done' when finished):");

	    String day;
	    while (true) {
	        System.out.print("Enter a day (e.g., Monday) or type 'done' to finish: ");
	        day = scanner.nextLine().trim().toLowerCase();
	        if (day.equals("done")) break;

	        System.out.print("Enter the date for " + day + " (format: yyyy-MM-dd): ");
	        String dateInput = scanner.nextLine();
	        LocalDate date = LocalDate.parse(dateInput);

	        System.out.print("Enter your preferred time slot for " + day + " (format: HH-HH, e.g., 10-16): ");
	        String timeRange = scanner.nextLine();
	        String[] times = timeRange.split("-");
	        int startHour = Integer.parseInt(times[0]);
	        int endHour = Integer.parseInt(times[1]);

	        for (int hour = startHour; hour < endHour; hour++) {
	            LocalDateTime appointmentTime = LocalDateTime.of(date, LocalTime.of(hour, 0));

	            AppointmentRecord appointment = new AppointmentRecord(
	                RecordsController.generateRecordID(RecordFileType.APPOINTMENT_RECORDS),
	                LocalDateTime.now(),
	                LocalDateTime.now(),
	                RecordStatusType.ACTIVE,
	                appointmentTime,
	                "level 2 - heart clinic",
	                AppointmentStatus.AVAILABLE,
	                null,
	                null,
	                doctor.getUID() // Access doctor UID from parameter
	            );

	            availableAppointments.add(appointment);
	            RecordsRepository.APPOINTMENT_RECORDS.put(appointment.getRecordID(), appointment);

	            System.out.println("Created and saved appointment for " + day + " at " + appointmentTime.format(dateTimeFormatter));
	        }
	    }

	    System.out.println("\n--- Appointment Availability Summary ---");
	    for (AppointmentRecord appointment : availableAppointments) {
	        System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + 
	                ", Time: " + appointment.getAppointmentTime().format(dateTimeFormatter) + 
	                ", Location: " + appointment.getlocation() +
	                ", Record ID: " + appointment.getRecordID() +
	                ", doctor ID: " + appointment.getDoctorID());
	    }
	    System.out.println("---------------------------------------");

	    RecordsRepository.saveAllRecordFiles(); // Save records to persist changes
	    scanner.close();
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
                System.out.println("  - Location: " + appointment.getlocation()); // Adjust if necessary
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
	
//	public static void doctorResponseToAppointment(String recordID, boolean accept) {
//	    AppointmentRecord appointment = RecordsRepository.APPOINTMENT_RECORDS.get(recordID);
//	    if (appointment != null && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
//	        if (accept) {
//	            appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
//	            System.out.println("Appointment has been completed.");
//	        } else {
//	            appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
//	            System.out.println("Appointment has been canceled.");
//	        }
//	    } else {
//	        System.out.println("No confirmed appointment found to respond to.");
//	    }
//	}
}

//
//Scanner scanner = new Scanner(System.in);
//
//if (appointmentStatus == AppointmentStatus.COMPLETED) {
//	System.out.println("The appointment is completed. Would you like to");
//	System.out.println("1) Record Appointment Outcome");
//
//	int choice = scanner.nextInt();
//
//	switch (choice) {
//
//	case 1:
//		System.out.println("Please enter the type of service:");
//		String typeOfService = scanner.nextLine();
//
//		System.out.println("Please enter the prescription (as a string):");
//		String prescriptionInput = scanner.nextLine();
//
//		System.out.println("Please enter the consultation notes:");
//		String consultationNotes = scanner.nextLine();
//
//		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord();
//		outcomeRecord.setTypeOfService(typeOfService);
//		// outcomeRecord.setPrescription(new Prescription(prescriptionInput)); //
//		// Assuming Prescription has a constructor
//		outcomeRecord.setConsultationNotes(consultationNotes);
//
//		AppointmentOutcomeRecordRepository.saveOutcomeRecord(recordID, outcomeRecord);
//		System.out.println("Appointment outcome recorded successfully!");
//		break;
//
//	default:
//		System.out.println("Invalid choice. No action taken.");
//		break;
//	}
//}
