package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import HMSApp.HMSMain;
import controller.AuthenticationController;
import controller.DoctorController;
import controller.HMSPersonnelController;
import controller.PatientController;
import controller.RecordsController;
import enums.AppointmentStatus;
import model.AppointmentRecord;
import model.Diagnosis;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.PrescribedMedication;
import model.TreatmentPlans;
import repository.DiagnosisRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.RecordsRepository;
import repository.TreatmentPlansRepository;

public class PatientUI extends MainUI {

	private static Patient patient;

	public PatientUI(Patient patient) {
		this.patient = patient;
	}

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
		System.out.println("9. Acknowledge Rejected Appointment Slots");
		System.out.println("10. Logout");
	}

	public void start() {
		showPatientDashboard();
	}

	public void showPatientDashboard() {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		do {
			printChoice();
			choice = sc.nextInt();
			switch (choice) {
			case 1 -> viewPatientMedicalRecord(patient.getUID()); 
			case 2 -> updatePatientPrivateInfo(patient.getUID());
			case 3 -> viewAvailableAppointmentSlots();
			case 4 -> scheduleAppointment(); 
			case 5 -> rescheduleAppointment();
			case 6 -> cancelAppointment();
			case 7 -> viewScheduledAppointments();
			case 8 -> cancelAppointment(); // Code for viewing past appointment outcomes
			case 9 -> acknowledgeRejectedAppointments();
			case 10 -> System.out.println("Logging out...");
			default -> System.out.println("Invalid choice!");
			}
		} while (choice != 10);

		sc.close(); // Close the Scanner
	}
	
	// 1. viewPatientMedicalRecordUI
	public void viewPatientMedicalRecord(String patientID) {
		System.out.println("\n--- Patient Medical Records for Patient ID: " + patientID + " ---");
		boolean recordsFound = false;
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getPatientID().equals(patientID)) {
				recordsFound = true;
				MedicalRecordUI medicalRecordUI = new MedicalRecordUI(record);
				medicalRecordUI.displayMedicalRecordInBox();
			}
		}
		if (!recordsFound) {
			System.out.println("No medical records found for Patient ID: " + patientID);
		}
		System.out.println("---------------------------------------");
	}

	// 2. updatePatientContactInfo
	public void updatePatientPrivateInfo(String patientId) {
		System.out.println("\n--- Patient Personal Information for Patient ID: " + patientId + " ---");
		boolean recordsFound = false;
		for (Patient patient : PersonnelRepository.PATIENTS.values()) {
			if (patient.getUID().equals(patientId)) {
				recordsFound = true;
				UpdatePatientParticularsUI updatePatientParticularsUI = new UpdatePatientParticularsUI(patient);
				updatePatientParticularsUI.start();
			}
		}
		if (!recordsFound) {
			System.out.println("No Personal Information found for Patient ID: " + patientId);
		}
		System.out.println("---------------------------------------");
	}
	
	
	// 3. viewAvailableAppointmentSlots
	public static void viewAvailableAppointmentSlots() {
		System.out.println("\n--- Available Appointment Slots :  ---");
		boolean found = false;
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				found = true;
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
				System.out.println("Doctor ID        : " + appointment.getDoctorID());
		        System.out.println("Doctor           : " + doctorName);
				System.out.println("Day              : " + appointment.getAppointmentTime().getDayOfWeek());
		        System.out.println("Date             : " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		        System.out.println("Time             : " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		        System.out.println("Location         : " + appointment.getLocation());
		        System.out.println("---------------------------------------");
			}
		}

		if (!found) {
			System.out.println("No appointments found");
		}

		System.out.println("---------------------------------------");

	}

	// 4. scheduleAppointment
	public void scheduleAppointment() {
		System.out.println("\n--- Schedule Appointment for Patient ID: " + patient.getUID() + " ---");
		ScheduleAppointmentUI scheduleAppointmentUI = new ScheduleAppointmentUI(patient);
		scheduleAppointmentUI.start();
	}
		
	
	
	public void acknowledgeRejectedAppointments() {
		System.out.println("\n--- Acknowledge Rejected Appointments ---");

		boolean found = false;
		Scanner scanner = new Scanner(System.in);

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELED
					&& patient.getUID().equals(appointment.getPatientID())) { // Check if the appointment belongs to the
																				// current user
				found = true;
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Doctor ID: " + appointment.getDoctorID()
						+ ", Doctor: " + doctorName);
				System.out.println("This appointment has been cancelled by the doctor.");

				System.out.print("Do you acknowledge this cancellation? (yes/no): ");
				String userResponse = scanner.nextLine().trim().toLowerCase();

				if (userResponse.equals("yes")) {
					appointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
					appointment.setPatientID(null);
					System.out.println("The appointment status has been changed to AVAILABLE.");
				} else {
					System.out.println("The appointment remains CANCELLED.");
				}
			}
		}

		if (!found) {
			System.out.println("No cancelled appointments found for your user ID.");
		}

		System.out.println("-----------------------------------------");
		RecordsRepository.saveAllRecordFiles();
	}

	public static void viewScheduledAppointments() {
		System.out.println("\n--- Scheduled Appointments ---");

		boolean found = false;

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patient.getUID().equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() != AppointmentStatus.AVAILABLE) {

				found = true;
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

				System.out.println("Doctor: " + (doctorName != null ? doctorName : "Unknown") + ", Date: "
						+ appointment.getAppointmentTime().toLocalDate() + ", Time: "
						+ appointment.getAppointmentTime().toLocalTime() + ", Status: "
						+ appointment.getAppointmentStatus());
			}
		}

		if (!found) {
			System.out.println("No scheduled appointments found.");
		}

		System.out.println("----------------------------------");
	}

	public void cancelAppointment() {
		System.out.println("\n--- Scheduled Appointments ---");

		List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		int index = 1;

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patient.getUID().equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

				confirmedAppointments.add(appointment);
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

				System.out.println(index++ + ". Doctor: " + (doctorName != null ? doctorName : "Unknown") + ", Date: "
						+ appointment.getAppointmentTime().toLocalDate() + ", Time: "
						+ appointment.getAppointmentTime().toLocalTime() + ", Status: "
						+ appointment.getAppointmentStatus());
			}
		}

		if (confirmedAppointments.isEmpty()) {
			System.out.println("No confirmed appointments found.");
			System.out.println("----------------------------------");
			return;
		}

		System.out.println("----------------------------------");

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of the appointment you wish to cancel: ");
		int choice;

		try {
			choice = Integer.parseInt(scanner.nextLine().trim());

			if (choice >= 1 && choice <= confirmedAppointments.size()) {
				AppointmentRecord selectedAppointment = confirmedAppointments.get(choice - 1);

				selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);
				selectedAppointment.setPatientID(null);
				System.out.println("The appointment has been successfully cancelled.");

			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
		}

		System.out.println("----------------------------------");
		RecordsRepository.saveAllRecordFiles();
	}

	public void rescheduleAppointment() {
		System.out.println("\n--- Reschedule an Appointment ---");

		List<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		int index = 1;

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (patient.getUID().equals(appointment.getPatientID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

				confirmedAppointments.add(appointment);
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

				System.out.println(index++ + ". Doctor: " + (doctorName != null ? doctorName : "Unknown") + ", Date: "
						+ appointment.getAppointmentTime().toLocalDate() + ", Time: "
						+ appointment.getAppointmentTime().toLocalTime() + ", Status: "
						+ appointment.getAppointmentStatus());
			}
		}

		if (confirmedAppointments.isEmpty()) {
			System.out.println("No appointments available for rescheduling.");
			System.out.println("----------------------------------");
			return;
		}

		System.out.println("----------------------------------");

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of the appointment you wish to reschedule: ");
		int choice;

		try {
			choice = Integer.parseInt(scanner.nextLine().trim());

			if (choice >= 1 && choice <= confirmedAppointments.size()) {
				AppointmentRecord selectedAppointment = confirmedAppointments.get(choice - 1);

				System.out.println("\n--- Available Slots for Rescheduling ---");
				List<AppointmentRecord> availableSlots = new ArrayList<>();
				index = 1;

				for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
					if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE
							&& appointment.getDoctorID().equals(selectedAppointment.getDoctorID())) {

						availableSlots.add(appointment);
						System.out.println(index++ + ". Date: " + appointment.getAppointmentTime().toLocalDate()
								+ ", Time: " + appointment.getAppointmentTime().toLocalTime() + ", Location: "
								+ appointment.getLocation());
					}
				}

				if (availableSlots.isEmpty()) {
					System.out.println("No available slots for rescheduling with the selected doctor.");
					System.out.println("----------------------------------");
					return;
				}

				System.out.println("----------------------------------");

				System.out.print("Enter the number of the new slot: ");
				int newSlotChoice;

				try {
					newSlotChoice = Integer.parseInt(scanner.nextLine().trim());

					if (newSlotChoice >= 1 && newSlotChoice <= availableSlots.size()) {
						AppointmentRecord newSlot = availableSlots.get(newSlotChoice - 1);

						selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE); // Free up the previous
																								// slot
						selectedAppointment.setPatientID(null);
						newSlot.setAppointmentStatus(AppointmentStatus.PENDING);
						newSlot.setPatientID(patient.getUID());
						System.out.println("Appointment has been successfully rescheduled.");

					} else {
						System.out.println("Invalid selection. Please enter a valid number.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid number.");
				}
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
		}

		System.out.println("----------------------------------");
		RecordsRepository.saveAllRecordFiles();
	}
}
