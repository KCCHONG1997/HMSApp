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
			case 1:
				// Code for viewing medical record
                viewPatientMedicalRecord(patient.getUID());
				break;
			case 2:
				// Code for updating personal information
				updatePatientContactInfo(patient.getUID());
				break;
			case 3:
				// Code for viewing available appointment slots
				viewAvailableAppointmentSlots();
				break;
			case 4:
				// Code for scheduling an appointment
				scheduleAppointment();
				break;
			case 5:
				// Code for rescheduling an appointment
				rescheduleAppointment();
				break;
			case 6:
				// Code for canceling an appointment
				cancelAppointment();
				break;
			case 7:
				// Code for viewing scheduled appointments
				viewScheduledAppointments();
				break;
			case 8:
				// Code for viewing past appointment outcomes
				
				break;
			case 9:
				// Code for acknowledging appointments
				acknowledgeRejectedAppointments();
				break;
			case 10:
				System.out.println("Logging out...");
				HMSMain.main(null);
				break;
			default:
				System.out.println("Invalid choice!");
			}
		} while (choice != 10);

		sc.close(); // Close the Scanner
	}
	
	//KC CODE 
	// Method to view a patient's medical record using MedicalRecordUI
//    public static void viewPatientMedicalRecord(String patientID) {
//        RecordsController rc = new RecordsController();
//        MedicalRecord medicalRecord = rc.getMedicalRecordsByPatientID(patientID);
//
//        if (medicalRecord != null) {
//            MedicalRecordUI recordUI = new MedicalRecordUI(medicalRecord);
//            recordUI.displayMedicalRecordInBox();
//        } else {
//            System.out.println("\n=====================================");
//            System.out.println("No medical record found for this patient.");
//            System.out.println("=====================================\n");
//        }
//    }
    
//    public static void updatePatientParticularView() {
//        // Fetch the patient object using UID
//        String patientUID = AuthenticationController.cookie.getUid();
//        Patient patient = HMSPersonnelController.getPatientById(patientUID);
//
//        if (patient != null) {
//            UpdatePatientParticularsUI updateView = new UpdatePatientParticularsUI(patient);
//            updateView.start();
//        } else {
//            System.out.println("Patient not found. Unable to update particulars.");
//        }
//    }
    
	
	public void viewPatientMedicalRecord(String patientId) {
	    System.out.println("\n--- Patient Medical Record ---");

	    Patient patient = PatientController.getPatientById(patientId);
	    if (patient != null) {
	        System.out.println("Patient Information:");
	        System.out.println("UID: " + patient.getUID());
	        System.out.println("Full Name: " + patient.getFullName());
	        System.out.println("Email: " + patient.getEmail());
	        System.out.println("Phone No: " + patient.getPhoneNo());
	        System.out.println("Date of Birth: " + patient.getDoB());
	        System.out.println("Gender: " + patient.getGender());
	        System.out.println("---------------------------------------");

	        boolean recordFound = false;
	        for (MedicalRecord record : RecordsRepository.MEDICAL_RECORDS.values()) {
	            if (record.getPatientID().equals(patientId)) {
	                recordFound = true;
	                System.out.println("Blood Type: " + record.getBloodType());
	            }
	        }

	        if (!recordFound) {
	            System.out.println("No medical records found for Patient ID: " + patientId);
	        }

	        ArrayList<Diagnosis> diagnoses = DiagnosisRepository.getDiagnosesByPatientID(patient.getUID());
	        if (diagnoses.isEmpty()) {
	            System.out.println("No diagnosis records found for Patient ID: " + patient.getUID());
	        } else {
	            System.out.println("\n--- Diagnosis Records ---");
	            for (Diagnosis diagnosis : diagnoses) {
	                System.out.println("Diagnosis Date: " + diagnosis.getDiagnosisDate());
	                System.out.println("Diagnosis Description: " + diagnosis.getDiagnosisDescription());

	                String diagnosisID = diagnosis.getDiagnosisID();  // Get the Diagnosis ID
	                TreatmentPlans patientTreatmentPlan = TreatmentPlansRepository.getTreatmentPlansByDiagnosisID(diagnosisID);

	                if (patientTreatmentPlan != null) {
	                    System.out.println("------------------------------------------------");
	                    System.out.println("Diagnosis ID: " + patientTreatmentPlan.getDiagnosisID());
	                    System.out.println("Treatment Date: " + patientTreatmentPlan.getTreatmentDate());
	                    System.out.println("Treatment Description: " + patientTreatmentPlan.getTreatmentDescription());
	                    System.out.println("------------------------------------------------");
	                } else {
	                    System.out.println("No treatment plan found for Diagnosis ID: " + diagnosisID);
	                }
	                ArrayList<PrescribedMedication> prescribedMedications = PrescribedMedicationRepository.diagnosisToMedicationsMap.getOrDefault(diagnosisID, new ArrayList<>());
	                if (prescribedMedications.isEmpty()) {
	                    System.out.println("No prescribed medications found for Diagnosis ID: " + diagnosisID);
	                } else {
	                    System.out.println("--- Prescribed Medications ---");
	                    for (PrescribedMedication medication : prescribedMedications) {
	                        System.out.println("Medicine ID: " + medication.getMedicineID());
	                        System.out.println("Quantity: " + medication.getMedicineQuantity());
	                        System.out.println("Period (Days): " + medication.getPeriodDays());
	                        System.out.println("Dosage: " + medication.getDosage());
	                        System.out.println("Status: " + medication.getPrescriptionStatus());
	                        System.out.println("---------------------------------------");
	                    }
	                }
	            }
	            
	            
	        }
	        
	       
	    } else {
	        System.out.println("Unknown patient with ID: " + patientId);
	    }

	    System.out.println("---------------------------------------");
	}





	
	public static void viewAvailableAppointmentSlots() {

		System.out.println("\n--- Available Appointment Slots :  ---");

		boolean found = false;
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				found = true;
				String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

				System.out.println("Day: " + appointment.getAppointmentTime().getDayOfWeek() + ", Time: "
						+ appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
						+ ", Location: " + appointment.getLocation() + ", Doctor ID: " + appointment.getDoctorID()
						+ ", Doctor: " + doctorName);
			}
		}

		if (!found) {
			System.out.println("No appointments found");
		}

		System.out.println("---------------------------------------");

	}

	    public  void updatePatientContactInfo(String patientId) {
	        Patient patient = getPatientById(patientId);
	        
	        if (patient != null) {
	            // Create a Scanner object to read input from the user
	            Scanner scanner = new Scanner(System.in);

	            System.out.println("\n--- Update Contact Information ---");
	            System.out.println("Current Email: " + patient.getEmail());
	            System.out.println("Current Phone Number: " + patient.getPhoneNo());
	            
	            System.out.print("Enter new email address: ");
	            String newEmail = scanner.nextLine();
	            patient.setEmail(newEmail);
	            
	            System.out.print("Enter new phone number: ");
	            String newPhoneNo = scanner.nextLine();
	            patient.setPhoneNo(newPhoneNo);

	            System.out.println("\nContact information updated successfully!");
	            System.out.println("New Email: " + patient.getEmail());
	            System.out.println("New Phone Number: " + patient.getPhoneNo());
	            
	            PersonnelRepository.saveAllPersonnelFiles();

	        } else {
	            System.out.println("Patient with ID: " + patientId + " not found.");
	        }
	    }

	    public static Patient getPatientById(String patientId) {
	        return PersonnelRepository.PATIENTS.get(patientId);
	    }
	


	public void scheduleAppointment() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the Doctor ID you want to schedule an appointment with:");
		String doctorId = sc.nextLine();

		Doctor doctor = PersonnelRepository.DOCTORS.get(doctorId);
		if (doctor == null) {
			System.out.println("Doctor not found. Please enter a valid Doctor ID.");
			return;
		}

		System.out.println("Enter the date and time for the appointment (yyyy-MM-dd HH:mm):");
		String dateTimeInput = sc.nextLine();

		LocalDateTime appointmentTime;
		try {
			appointmentTime = LocalDateTime.parse(dateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			System.out.println("Invalid date and time format. Please try again.");
			return;
		}

		AppointmentRecord existingAppointment = null;
		for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
			if (appointment.getDoctorID().equals(doctorId) && appointment.getAppointmentTime().equals(appointmentTime)
					&& appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
				existingAppointment = appointment;
				break;
			}
		}

		if (existingAppointment != null) {
			existingAppointment.setAppointmentStatus(AppointmentStatus.PENDING);
			existingAppointment.setPatientID(patient.getUID());
			System.out.println("Appointment confirmed successfully with Dr. " + doctor.getFullName() + " on "
					+ appointmentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			RecordsRepository.saveAllRecordFiles();
		} else {
			System.out.println("No available appointment found for the selected time. Please try again.");
		}
	}
	
	
	public void acknowledgeRejectedAppointments() {
	    System.out.println("\n--- Acknowledge Rejected Appointments ---");

	    boolean found = false;
	    Scanner scanner = new Scanner(System.in);

	    for (AppointmentRecord appointment : RecordsRepository.APPOINTMENT_RECORDS.values()) {
	        if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELED
	                && patient.getUID().equals(appointment.getPatientID())) {  // Check if the appointment belongs to the current user
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
	        if (patient.getUID().equals(appointment.getPatientID()) &&
	            appointment.getAppointmentStatus() != AppointmentStatus.AVAILABLE ) {
	            
	            found = true;
	            String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());

	            System.out.println("Doctor: " + (doctorName != null ? doctorName : "Unknown") +
	                               ", Date: " + appointment.getAppointmentTime().toLocalDate() +
	                               ", Time: " + appointment.getAppointmentTime().toLocalTime() +
	                               ", Status: " + appointment.getAppointmentStatus());
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
	        if (patient.getUID().equals(appointment.getPatientID()) &&
	            appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
	            
	            confirmedAppointments.add(appointment);
	            String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
	            
	            System.out.println(index++ + ". Doctor: " + (doctorName != null ? doctorName : "Unknown") +
	                               ", Date: " + appointment.getAppointmentTime().toLocalDate() +
	                               ", Time: " + appointment.getAppointmentTime().toLocalTime() +
	                               ", Status: " + appointment.getAppointmentStatus());
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
	        if (patient.getUID().equals(appointment.getPatientID()) &&
	            appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
	            
	            confirmedAppointments.add(appointment);
	            String doctorName = DoctorController.getDoctorNameById(appointment.getDoctorID());
	            
	            System.out.println(index++ + ". Doctor: " + (doctorName != null ? doctorName : "Unknown") +
	                               ", Date: " + appointment.getAppointmentTime().toLocalDate() +
	                               ", Time: " + appointment.getAppointmentTime().toLocalTime() +
	                               ", Status: " + appointment.getAppointmentStatus());
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
	                if (appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE &&
	                    appointment.getDoctorID().equals(selectedAppointment.getDoctorID())) {
	                    
	                    availableSlots.add(appointment);
	                    System.out.println(index++ + ". Date: " + appointment.getAppointmentTime().toLocalDate() +
	                                       ", Time: " + appointment.getAppointmentTime().toLocalTime() +
	                                       ", Location: " + appointment.getLocation());
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

	                    selectedAppointment.setAppointmentStatus(AppointmentStatus.AVAILABLE); // Free up the previous slot
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

