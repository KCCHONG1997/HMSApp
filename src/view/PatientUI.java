package view;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import HMSApp.HMSMain;
import controller.DoctorController;
import enums.AppointmentStatus;
import model.AppointmentRecord;
import model.Doctor;
import model.Patient;
import repository.PersonnelRepository;
import repository.RecordsRepository;

public class PatientUI extends MainUI{
	
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
        System.out.println("9. Logout");
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
                    break;
                case 2: 
                    // Code for updating personal information
                    break;
                case 3: 
                    // Code for viewing available appointment slots
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
        } while(choice != 9);
        
        sc.close(); // Close the Scanner
    }
    
    
    public static void viewAvailableAppointmentSlots() {
        RecordsRepository.loadAllRecordFiles();

    	System.out.println("\n--- Available Appointment Slots :  ---");

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


    
//    public static void patientChooseAppointment(String recordID, LocalDateTime chosenTime) {
//        AppointmentRecord appointment = RecordsRepository.APPOINTMENT_RECORDS.get(recordID);
//        if (appointment != null && appointment.getAppointmentStatus() == AppointmentStatus.AVAILABLE) {
//            appointment.setAppointmentTime(chosenTime);
//            appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
//            System.out.println("Appointment has been confirmed for: " + chosenTime);
//        } else {
//            System.out.println("No available appointment found or it is already confirmed.");
//        }
//    }
    
}
