package HMSApp;

import java.time.LocalDateTime;
import model.*;
import repository.*;

public class HMSMain {
    public static void main(String[] args) {
        printHMSWelcomeTitle();
        loadRepository();

        // Always read the Repo first before adding new Object
        Doctor newDoctor = new Doctor(
                "D003",                         // UID
                "Dr. KCKC",                  // Full Name
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

        // Load the repository again to retrieve the newly added doctor
        loadRepository();

        // Step 4: Retrieve the doctor using their UID
        Doctor retrievedDoctor = PersonnelRepository.DOCTORS.get("D002");

        if (retrievedDoctor != null) {
            // Step 5: Display the doctor's information
            System.out.println("Doctor UID: " + retrievedDoctor.getUID());
            System.out.println("Doctor Name: " + retrievedDoctor.getFullName());
            System.out.println("Specialty: " + retrievedDoctor.getSpecialty());
            System.out.println("Years of Experience: " + retrievedDoctor.getYearsOfExperiences());
        } else {
            System.out.println("Doctor not found.");
        }
        Doctor retrievedDoctor1 = PersonnelRepository.DOCTORS.get("D003");

        if (retrievedDoctor1 != null) {
            // Step 5: Display the doctor's information
            System.out.println("Doctor UID: " + retrievedDoctor1.getUID());
            System.out.println("Doctor Name: " + retrievedDoctor1.getFullName());
            System.out.println("Specialty: " + retrievedDoctor1.getSpecialty());
            System.out.println("Years of Experience: " + retrievedDoctor1.getYearsOfExperiences());
        } else {
            System.out.println("Doctor not found.");
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
}
