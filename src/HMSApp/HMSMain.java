package HMSApp;

<<<<<<< Updated upstream
import java.time.LocalDateTime;
import model.*;
import repository.*;
import controller.HMSPersonnelController;  // Import the controller

public class HMSMain {
    public static void main(String[] args) {
        printHMSWelcomeTitle();
        loadRepository();

        // Create a new Doctor object
        Doctor newDoctor = new Doctor(
                "D003",                         // UID
                "Dr. KCKC",                     // Full Name
                "123456789",                     // ID Card
                "johndoe",                       // Username
                "johndoe@example.com",           // Email
                "123-456-7890",                  // Phone Number
                "hashed_password",               // Password Hash
                LocalDateTime.of(1980, 1, 1, 0, 0),  // Date of Birth
                "Male",                          // Gender
                "Doctor",                        // Role
                "Cardiology",                    // Specialty
                "MED12345",                      // Medical License Number
                LocalDateTime.now(),             // Date Joined
                10                               // Years of Experience
        );

//        HMSPersonnelController.addPersonnel(newDoctor);

        // Step 3: Retrieve and display doctor information using the helper method
        displayDoctorInformation("D001");
        displayDoctorInformation("D003");
    }

    // Temporary Helper method to retrieve and display doctor information
    private static void displayDoctorInformation(String uid) {
        Doctor retrievedDoctor = (Doctor) HMSPersonnelController.getPersonnelByUID(uid, PersonnelFileType.DOCTORS);

        if (retrievedDoctor != null) {
            System.out.println("Doctor UID: " + retrievedDoctor.getUID());
            System.out.println("Doctor Name: " + retrievedDoctor.getFullName());
            System.out.println("Specialty: " + retrievedDoctor.getSpecialty());
            System.out.println("Years of Experience: " + retrievedDoctor.getYearsOfExperiences());
        } else {
            System.out.println("Doctor " + uid + " not found.");
        }
    }

    // Load all repositories (Doctors, Patients, Pharmacists, Admins, etc.)
    private static void loadRepository() {
        PersonnelRepository.readData(PersonnelFileType.DOCTORS);
        PersonnelRepository.readData(PersonnelFileType.PATIENTS);
        PersonnelRepository.readData(PersonnelFileType.PHARMACISTS);
        PersonnelRepository.readData(PersonnelFileType.ADMINS);
        System.out.println("All repositories loaded successfully.");
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
=======
import java.util.Scanner;
public class HMSMain {
	public static void main(String[] args) {
		printHMSWelcomeTitle();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter you role: ");
		System.out.println("1. Doctor ");
		System.out.println("2. Patient");
		System.out.println("3. Pharmacist");
		System.out.println("4. Administrator");
		
		int choice = sc.nextInt();
		switch(choice){
			case 1:
			Login.doctorLogin();
			case 2:
			Login.patientLogin();
			case 3:
			Login.pharmacistLogin();
			case 4:
			Login.administratorLogin();
		}
	}
	
	private static void printHMSWelcomeTitle() {
	    System.out.println();
	    System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
	    System.out.println("║                                                                                                     ║");
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



>>>>>>> Stashed changes
}
