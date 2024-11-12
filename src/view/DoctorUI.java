package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import HMSApp.HMSMain;
import controller.*;
import enums.AppointmentStatus;
import helper.Helper;
import model.*;
import repository.AppointmentOutcomeRecordRepository;
import repository.DiagnosisRepository;
import repository.PersonnelRepository;
import repository.PrescribedMedicationRepository;
import repository.PrescriptionRepository;
import repository.RecordFileType;
import repository.RecordsRepository;
import repository.TreatmentPlansRepository;

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
				// Code for viewing patient medical records
				viewPatientMedicalRecord(doctor.getUID());
				break;
			case 2:
				// Code for updating patient medical records
				selectAndUpdateMedicalRecord();
				break;
			case 3:
				// Code for viewing personal schedule
				viewPersonalSchedule();
				break;
			case 4:
				// Code for setting availability for appointments
				availabilityForAppointments();
				break;
			case 5:
				// Code for accepting or declining appointment requests
				acceptOrDeclineAppointmentRequests();
				break;
			case 6:
				viewUpcomingAppointments();
				// Code for viewing upcoming appointments
				break;
			case 7:
				// Code for recording appointment outcome
				recordAppointmentOutcome();
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

	//1. viewPatientMedicalRecordUI
	public void viewPatientMedicalRecord(String doctorId) {
	    System.out.println("\n--- All Patients' Medical Records for Doctor ID: " + doctorId + " ---");
	    boolean recordsFound = false;
	    for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
	        if (record.getDoctorID().equals(doctorId)) {
	            recordsFound = true;
	            MedicalRecordUI medicalRecordUI = new MedicalRecordUI(record);
	            medicalRecordUI.displayMedicalRecordInBox();
	        }
	    }
	    if (!recordsFound) {
	        System.out.println("No medical records found for Doctor ID: " + doctorId);
	    }
	    System.out.println("---------------------------------------");
	}

	//2. selectAndUpdateMedicalRecordUI;
	public void selectAndUpdateMedicalRecord() {
	    System.out.println("Enter Patient ID to select medical record:");
	    String patientId = Helper.readString();  // Use Helper's readString method

	    // Retrieve the patient's medical record by the doctor and patient ID
	    String medicalRecordID = retrieveMedicalRecordID(doctor.getUID(), patientId);
	    
	    if (medicalRecordID == null) {
	        System.out.println("No medical record found for the specified patient.");
	        return;
	    }

	    MedicalRecord medicalRecord = RecordsRepository.MEDICAL_RECORDS.get(medicalRecordID);

	    if (medicalRecord == null) {
	        System.out.println("Error: Medical record not found in repository.");
	        return;
	    }

	    // Use UpdateMedicalRecordUI to handle the updating process
	    UpdateMedicalRecordUI updateUI = new UpdateMedicalRecordUI(doctor, medicalRecord);
	    updateUI.start();
	}




	//3. viewPersonalSchedule
	public void viewPersonalSchedule() {
		System.out.println("\n--- Appointments for Dr. " + doctor.getFullName() + " (ID: " + doctor.getUID() + ") ---");

		boolean found = false;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())) {
				found = true;
				System.out.println("Appointment Record:");
				System.out.println("  - Appointment ID: " + appointment.getRecordID());
				System.out.println("  - Date & Time: " + appointment.getAppointmentTime().format(dateTimeFormatter));
				System.out.println("  - Location: " + appointment.getLocation());
				System.out.println("  - Status: " + appointment.getAppointmentStatus());
				System.out.println(
						"  - Patient ID: " + (appointment.getPatientID() != null ? appointment.getPatientID() : "N/A"));
				System.out.println("---------------------------------------");
			}
		}

		if (!found) {
			System.out.println("No appointments found for this doctor.");
		}
		System.out.println("---------------------------------------");
	}
	//4. availabilityForAppointments
	public void availabilityForAppointments() {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		AppointmentStatus status = AppointmentStatus.AVAILABLE;

		ArrayList<AppointmentRecord> availableAppointments = new ArrayList<>();

		System.out.println("Select your available days (type 'done' when finished):");

		while (true) {
			System.out.print("Enter a day (e.g., Monday) or type 'done' to finish: ");
			String day = scanner.nextLine().trim().toLowerCase();
			if (day.equals("done"))
				break;

			try {
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
							null,
							null,
							this.doctor.getUID(),
							appointmentTime,
							"level 2 - heart clinic",
							status,
							null
					);

					availableAppointments.add(appointment);
					RecordsRepository.APPOINTMENT_RECORDS.put(appointment.getRecordID(), appointment);

					System.out.println("Created and saved appointment for " + day + " at "
							+ appointmentTime.format(dateTimeFormatter));
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please ensure the date and time format is correct.");
			}
		}

		System.out.println("\n--- Appointment Availability Summary ---");
		for (AppointmentRecord appointment : availableAppointments) {
			System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
					+ appointment.getAppointmentTime().format(dateTimeFormatter) + ", Location: "
					+ appointment.getLocation() + ", Record ID: " + appointment.getRecordID() + ", Doctor ID: "
					+ appointment.getDoctorID());
		}
		System.out.println("---------------------------------------");
		RecordsRepository.saveAllRecordFiles();
	}
	//5.acceptOrDeclineAppointmentRequests
	public void acceptOrDeclineAppointmentRequests() {
		System.out.println(
				"\n--- Request Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");
		Scanner sc = new Scanner(System.in);

		boolean found = false;
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
				found = true;
				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
						+ ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));

				System.out.println("Do you want to accept or decline this appointment? (Type 'accept' or 'decline'): ");
				String choice = sc.nextLine().trim().toLowerCase();

				if ("accept".equals(choice)) {
					appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
					System.out.println(
							"Appointment with Patient ID: " + appointment.getPatientID() + " has been confirmed.");
				} else if ("decline".equals(choice)) {
					appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
					System.out.println("Appointment with Patient ID: " + appointment.getPatientID()
							+ " has been declined and is waiting for patient to acknowledge.");
				} else {
					System.out.println("Invalid choice. Please enter 'accept' or 'decline'.");
				}
			}
		}

		if (!found) {
			System.out.println("No pending appointments found for this doctor.");
		}

		System.out.println("---------------------------------------");

		RecordsRepository.saveAllRecordFiles();

	}
	//6. viewUpcomingAppointments
	public void viewUpcomingAppointments() {
		System.out.println(
				"\n--- Upcoming Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");

		boolean found = false;

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
				found = true;
				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Patient ID: " + appointment.getPatientID()
						+ "\n" + PatientController.getPatientInfoById(appointment.getPatientID())

				);
			}
		}

		if (!found) {
			System.out.println("No upcoming appointments found for this doctor.");
		}

		System.out.println("---------------------------------------");
	}







	public boolean setAppointmentRecordStatus(String AppointmentRecordID, String status){
		boolean flag = false;
		AppointmentRecord appointmentRecord = RecordsRepository.APPOINTMENT_RECORDS.get(AppointmentRecordID);
		if (appointmentRecord!=null)
		{
			appointmentRecord.setAppointmentStatus(AppointmentStatus.toEnumAppointmentStatus(status));

		}
		return flag;

	}
	public AppointmentOutcomeRecord generateAppointmentOutcomeRecord(MedicalRecord medicalRecord,String diagnosisID , String typeOfService, String consultationNotes){

		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
				medicalRecord.getPatientID(),
				medicalRecord.getDoctorID(),
				diagnosisID,
				medicalRecord.getRecordID(),
				LocalDateTime.now(),
				PrescriptionRepository.PRESCRIPTION_MAP.get(diagnosisID),
				typeOfService,
				consultationNotes);

		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(medicalRecord.getPatientID(), outcomeRecord);
		AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
		return outcomeRecord;

	}

	public static String getMedicalRecordID(String doctorId, String patientId) {
	    //  through the medical records in the repository
	    for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
	        // Check if the record matches the provided doctorId and patientId
	        if (record.getDoctorID().equals(doctorId) && record.getPatientID().equals(patientId)) {
	            return record.getRecordID(); // Return the matching record's ID
	        }
	    }
	    // If no record is found, return null or an appropriate message
	    return null; // Or throw an exception if preferred
	}
	public String retrieveMedicalRecordID(String doctorID, String patientID) {
		for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
			if (record.getDoctorID().equals(doctorID) && record.getPatientID().equals(patientID)) {
				return record.getRecordID(); // Return the matching record's ID
			}
		}
		return null; // No matching record found
	}
	public void recordAppointmentOutcome() {
		Scanner scanner = new Scanner(System.in);

		System.out.println(
				"\n--- Confirmed Appointments for: " + doctor.getFullName() + " (UID: " + doctor.getUID() + ") ---");

		ArrayList<AppointmentRecord> confirmedAppointments = new ArrayList<>();
		int index = 1;

		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (doctor.getUID().equals(appointment.getDoctorID())
					&& appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
				confirmedAppointments.add(appointment);
				System.out.println(index + ". Day: " + appointment.getAppointmentTime().getDayOfWeek()
						+ ", Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation()
						+ ", Patient ID: " + appointment.getPatientID()
						+ ", Patient Name: " + PatientController.getPatientNameById(appointment.getPatientID()));
				index++;
			}
		}

		if (confirmedAppointments.isEmpty()) {
			System.out.println("No confirmed appointments found for this doctor.");
			return;
		}

		System.out.print("\nEnter the number of the appointment you want to record the outcome for: ");
		int selectedIndex = scanner.nextInt();
		scanner.nextLine();

		if (selectedIndex < 1 || selectedIndex > confirmedAppointments.size()) {
			System.out.println("Invalid selection. No outcome recorded.");
			return;
		}

		AppointmentRecord selectedAppointment = confirmedAppointments.get(selectedIndex - 1);
		System.out.println("\n--- Select Diagnosis for Patient (ID: " + selectedAppointment.getPatientID() + ") ---");

		// Fetch diagnoses for the selected patient
		ArrayList<Diagnosis> diagnoses = DiagnosisRepository.getDiagnosesByPatientID(selectedAppointment.getPatientID());
		if (diagnoses.isEmpty()) {
			System.out.println("No diagnoses found for this patient.");
			return;
		}

		for (int i = 0; i < diagnoses.size(); i++) {
			Diagnosis diagnosis = diagnoses.get(i);
			System.out.println((i + 1) + ". Diagnosis ID: " + diagnosis.getDiagnosisID()
					+ ", Condition: " + diagnosis.getDiagnosisDescription());
		}

		System.out.print("\nEnter the number of the diagnosis to use: ");
		int diagnosisIndex = scanner.nextInt();
		scanner.nextLine();

		if (diagnosisIndex < 1 || diagnosisIndex > diagnoses.size()) {
			System.out.println("Invalid selection. No outcome recorded.");
			return;
		}

		Diagnosis selectedDiagnosis = diagnoses.get(diagnosisIndex - 1);
		String diagnosisID = selectedDiagnosis.getDiagnosisID();

		System.out.print("The appointment is confirmed. Do you want to record the outcome? (yes/no): ");
		String response = scanner.nextLine().trim().toLowerCase();

		if (!"yes".equals(response)) {
			System.out.println("No changes made.");
			return;
		}

		selectedAppointment.setAppointmentStatus(AppointmentStatus.COMPLETED);

		// appointment outcome record has the same ID as the appointmentRecord
		String outcomeRecordID = selectedAppointment.getRecordID();

		// update prescribedMedication to medical record and appointment outcome record
		ArrayList<PrescribedMedication> medications = new ArrayList<>();
	    Prescription prescription = new Prescription(diagnosisID, LocalDateTime.now(),medications);
	    PrescriptionRepository.addPrescriptionRecord(prescription);
	    System.out.print("Do you want to prescribe medication? (yes/no): ");
	    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
	        while (true) {
	            System.out.print("Enter quantity: ");
	            int quantity = Integer.parseInt(scanner.nextLine());
	            System.out.print("Enter period (days): ");
	            int periodDays = Integer.parseInt(scanner.nextLine());
	            System.out.print("Enter dosage: ");
	            String dosage = scanner.nextLine();
	            PrescribedMedication medication = new PrescribedMedication(diagnosisID,null, quantity, periodDays, enums.PrescriptionStatus.PENDING, dosage);
	            medications.add(medication);
	            PrescribedMedicationRepository.addMedication(diagnosisID, medication);

	            System.out.print("Add another medication? (yes/no): ");
	            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
	                break;
	            }
	        }

	    }


		System.out.print("Enter the type of service: ");
		String typeOfService = scanner.nextLine();
		System.out.print("Enter your consultation notes: ");
		String consultationNotes = scanner.nextLine();

		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
				selectedAppointment.getPatientID(),
				doctor.getUID(),
				diagnosisID,
				outcomeRecordID,
				LocalDateTime.now(),
				prescription,
				typeOfService,
				consultationNotes);

		AppointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(selectedAppointment.getPatientID(),outcomeRecord);
		selectedAppointment.setAppointmentOutcomeRecordID(outcomeRecordID);

//		RecordsRepository.saveAllRecordFiles();
//		AppointmentOutcomeRecordRepository.saveAppointmentOutcomeRecordRepository();
//		DiagnosisRepository.saveAlltoCSV();
//		TreatmentPlansRepository.saveAlltoCSV();
//		PrescribedMedicationRepository.saveAlltoCSV();

		System.out.println("Appointment outcome recorded and saved successfully.");
	}

}
//KC CODE
//	private void viewPatientRecords() {
//        printBreadCrumbs("HMS App UI > Doctor Dashboard > View Patient Records");
//        RecordsController rc = new RecordsController();
//        ArrayList<MedicalRecord> records = rc.getMedicalRecordsByDoctorID(AuthenticationController.cookie.getUid());
//
//        if (records.isEmpty()) {
//            System.out.println("No medical records found for this doctor.");
//            return;
//        }
//        for (MedicalRecord record : records) {
//            MedicalRecordUI recordUI = new MedicalRecordUI(record); // Instantiate MedicalRecordUI
//            recordUI.displayMedicalRecordInBox(); // Display the medical record
//        }
//    }