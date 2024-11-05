package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;

public class AppointmentOutcomeRecord {
	private LocalDateTime appointmentTime;
    private String typeOfService;
    private Prescription prescription;
    private String consultationNotes;
    private String patientID; 
    private String doctorID;

	public AppointmentOutcomeRecord(
			LocalDateTime appointmentTime, 
			String typeOfService, 
			Prescription prescription,
			String consultationNotes, 
			String patientID,
			String doctorID) {
		this.appointmentTime = appointmentTime;
		this.typeOfService = typeOfService;
		this.prescription = prescription;
		this.consultationNotes = consultationNotes;
		this.patientID = patientID;
		this.doctorID = doctorID;
	}

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getConsultationNotes() { 
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }
    
    public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	
	public String getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}

}
