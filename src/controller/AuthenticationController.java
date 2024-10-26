package controller;

import java.util.Map;
import model.*;
import repository.PersonnelFileType;
import repository.PersonnelRepository;

public class AuthenticationController {

    // Method to authenticate a user based on username and password
	public static HMSPersonnel login(String username, String password, PersonnelFileType role) {
	    // Determine the correct personnel map to check based on the role
	    Map<String, ? extends HMSPersonnel> personnelMap = null;

	    switch (role.toString().toLowerCase()) {
	        case "admins":
	            personnelMap = PersonnelRepository.ADMINS;
	            break;
	        case "doctors":
	            personnelMap = PersonnelRepository.DOCTORS;
	            break;
	        case "pharmacists":
	            personnelMap = PersonnelRepository.PHARMACISTS;
	            break;
	        case "patients":
	            personnelMap = PersonnelRepository.PATIENTS;
	            break;
	        default:
	            System.out.println("Login failed: Invalid role provided.");
	            return null;
	    }

	    // If a valid role is provided, check the corresponding personnel map
	    for (HMSPersonnel personnel : personnelMap.values()) {
	        if (personnel.getUsername().equals(username) && verifyPassword(personnel, password)) {
	            System.out.println(role + " " + personnel.getFullName() + " logged in successfully.");
	            return personnel;
	        }
	    }

	    // If no user is found in the map for the provided role
	    System.out.println("Login failed: Invalid username or password.");
	    return null;
	}


    // Method to verify if the password matches the stored password hash
    private static boolean verifyPassword(HMSPersonnel personnel, String password) {
        // For simplicity, we're directly comparing the password (in a real app, you'd hash the password)
        return personnel.getPasswordHash().equals(password);
    }

    // Optional: Implement a logout method if needed
    public static void logout(HMSPersonnel personnel) {
        System.out.println(personnel.getFullName() + " has been logged out.");
    }
}
