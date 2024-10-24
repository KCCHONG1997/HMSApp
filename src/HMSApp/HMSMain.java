package HMSApp;

import java.time.LocalDateTime;

import model.*;
import repository.*;

public class HMSMain {
	public static void main(String[] args) {
		printHMSWelcomeTitle();
		
        Doctor newDoctor = new Doctor(
                "D001",                         // UID
                "Dr. John Doe",                  // Full Name
                "123456789",                     // ID Card (e.g., government ID)
                "johndoe",                       // Username
                "johndoe@example.com",           // Email
                "123-456-7890",                  // Phone Number
                "hashed_password",               // Password Hash (normally would be a hashed value)
                LocalDateTime.of(1980, 1, 1, 0, 0),  // Date of Birth
                "Male",                          // Gender
                "Doctor",                        // Role (Doctor)
                "Cardiology",                    // Specialty
                "MED12345",                      // Medical License Number
                LocalDateTime.now(),             // Date Joined
                10                               // Years of Experience
            );

            // Step 2: Save the Doctor to the repository
            PersonnelRepository.DOCTORS.put(newDoctor.getUID(), newDoctor);

            // Step 3: Persist the changes (save all personnel files)
            PersonnelRepository.saveAllPersonnelFiles();
            /////////////////////////////////////////////////////////////////////////////////////
            
            // Step 1: Read the data from the repository to load all doctors
            PersonnelRepository.readData(PersonnelFileType.DOCTORS);

            // Step 2: Retrieve the doctor using their UID
            Doctor retrievedDoctor = PersonnelRepository.DOCTORS.get("D001");

            if (retrievedDoctor != null) {
                // Step 3: Display the doctor's information
                System.out.println("Doctor UID: " + retrievedDoctor.getUID());
                System.out.println("Doctor Name: " + retrievedDoctor.getFullName());
                System.out.println("Specialty: " + retrievedDoctor.getSpecialty());
                System.out.println("Years of Experience: " + retrievedDoctor.getYearsOfExperiences());
            } else {
                System.out.println("Doctor not found.");
            }
            
	}
	
	private static void printHMSWelcomeTitle() {
	    System.out.println();
	    System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
	    System.out.println("║                                                                                                            ║");
	    System.out.println("║  ____   ____      ______  _______            ______              _____        _____        _____    ║");
	    System.out.println("║ |    | |    |    |      \\/       \\       ___|\\     \\         ___|\\    \\   ___|\\    \\   ___|\\    \\   ║");
	    System.out.println("║ |    | |    |   /          /\\     \\     |    |\\     \\       /    /\\    \\ |    |\\    \\ |    |\\    \\  ║");
	    System.out.println("║ |    |_|    |  /     /\\   / /\\     |    |    |/____/|      |    |  |    ||    | |    ||    | |    | ║");
	    System.out.println("║ |    .-.    | /     /\\ \\_/ / /    /| ___|    \\|   | |      |    |__|    ||    |/____/||    |/____/| ║");
	    System.out.println("║ |    | |    ||     |  \\|_|/ /    / ||    \\    \\___|/       |    .--.    ||    ||    |||    ||    || ║");
	    System.out.println("║ |    | |    ||     |       |    |  ||    |\\     \\          |    |  |    ||    ||____|/|    ||____|/ ║");
	    System.out.println("║ |____| |____||\\____\\       |____|  /|\\ ___\\|_____|         |____|  |____||____|       |____|        ║");
	    System.out.println("║ |    | |    || |    |      |    | / | |    |     |         |    |  |    ||    |       |    |        ║");
	    System.out.println("║ |____| |____| \\|____|      |____|/   \\|____|_____|         |____|  |____||____|       |____|        ║");
	    System.out.println("║   \\(     )/      \\(          )/         \\(    )/             \\(      )/    \\(           \\(          ║");
	    System.out.println("║    '     '        '          '           '    '               '      '      '            '          ║");
	    System.out.println("║                                                                                                     ║");
	    System.out.println("║                           Welcome to Hospital Management System                                     ║");
	    System.out.println("║                                                                                                     ║");
	    System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
	}

}
