package controller;

import model.Doctor;
import repository.PersonnelRepository;

public class DoctorController {
	
	// Method to retrieve a Doctor by ID directly from PersonnelRepository.DOCTORS
    public static Doctor getDoctorById(String doctorId) {
        return PersonnelRepository.DOCTORS.get(doctorId);
    }
    
    public static String getDoctorNameById(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        return doctor != null ? doctor.getFullName() : "Unknown Doctor";
    }

}
