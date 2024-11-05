package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.RecordsController;
import enums.AppointmentStatus;
import repository.AppointmentOutcomeRecordRepository;
import repository.RecordFileType;

public class AppointmentRecord extends HMSRecords {
	private LocalDateTime appointmentTime;
	private String location;
	private AppointmentStatus appointmentStatus;
	private AppointmentOutcomeRecord appointmentOutcomeRecord;
	private String patientID;
	private String doctorID;

	// Constructor when retrieving CSV to an object
	public AppointmentRecord(
			String recordID, // KC - we need this when retrieving from CSV
			String patientID,
			LocalDateTime createdDate,
			LocalDateTime updatedDate,
			RecordStatusType recordStatus,
			LocalDateTime appointmentTime,
			String location,
			AppointmentStatus appointmentStatus,
			ArrayList<AppointmentOutcomeRecord> appointmentOutcomeRecord) {
		super(recordID, createdDate, updatedDate, recordStatus);

		this.patientID = patientID;
		this.appointmentTime = appointmentTime;
		this.location = location;
		this.appointmentStatus = appointmentStatus;
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
	}

	// Constructor when generating a new AppointmentRecord
	public AppointmentRecord(
			String recordID,
			LocalDateTime createdDate,
			LocalDateTime updatedDate,
			RecordStatusType recordStatus,
			LocalDateTime appointmentTime,
			String location,
			AppointmentStatus appointmentStatus,
			AppointmentOutcomeRecord appointmentOutcomeRecord,
			String patientID,
			String doctorID) {
		super(recordID, createdDate, updatedDate, recordStatus);
		this.patientID = patientID;
		this.doctorID = doctorID;
		this.appointmentTime = appointmentTime;
		this.location = location;
		this.appointmentStatus = appointmentStatus;
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
	}

	// Getters and Setters
	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getlocation() {
		return location;
	}

	public void setlocation(String location) {
		this.location = location;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;

	}

	public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
		return appointmentOutcomeRecord;
	}

	public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord AppointmentOutcomeRecord) {
		this.appointmentOutcomeRecord = appointmentOutcomeRecord;
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
