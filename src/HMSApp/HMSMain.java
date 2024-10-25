package HMSApp;


import java.time.LocalDateTime;
import java.util.Scanner;

import model.*;
import repository.*;
import view.*;
import controller.*;  // Import the controller
 
public class HMSMain {
    public static void main(String[] args) {
        printHMSWelcomeTitle();
        PersonnelRepository.loadAllPersonnelFiles();
    
        
        
        
        
        
        
        
        
        
        /////////////////////////////// Example: Retrieve and display a doctor from the repository
        Doctor dummyDoctor = new Doctor(
                "D001",                            // UID
                "Dr. Test Dummy",                   // Full Name
                "1234567890",                       // ID Card
                "testdummy",                        // Username
                "dummy@example.com",                // Email
                "123-456-7890",                     // Phone Number
                "dummy_password_hash",              // Password Hash
                LocalDateTime.of(1980, 1, 1, 0, 0), // Date of Birth (LocalDateTime)
                "Male",                             // Gender
                "Doctor",                           // Role
                "Cardiology",                       // Specialty
                "MED123456",                        // Medical License Number
                LocalDateTime.now(),                // Date Joined
                15                                  // Years of Experience
            );
        
     // Save the dummy doctor to the repository
        PersonnelRepository.DOCTORS.put(dummyDoctor.getUID(), dummyDoctor);

        // Persist the changes to the CSV file
        PersonnelRepository.saveAllPersonnelFiles();
        
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Doctor UID to search: ");
        String uid = scanner.nextLine();
        
        // Try to retrieve a doctor by UID from the repository
        Doctor retrievedDoctor = PersonnelRepository.DOCTORS.get(uid);
        
        if (retrievedDoctor != null) {
            System.out.println("Doctor UID: " + retrievedDoctor.getUID());
            System.out.println("Doctor Name: " + retrievedDoctor.getFullName());
            System.out.println("Specialty: " + retrievedDoctor.getSpecialty());
            System.out.println("Years of Experience: " + retrievedDoctor.getYearsOfExperiences());
        } else {
            System.out.println("Doctor with UID " + uid + " not found.");
        }

        scanner.close();
        
        ///////////////////////////////////EXAMPLE
        
        
        
        
    }

    private static void printHMSWelcomeTitle() {
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
