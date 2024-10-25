package HMSApp;


import java.time.LocalDateTime;
import model.*;
import repository.*;
import controller.HMSPersonnelController;  // Import the controller
 
public class HMSMain {
    public static void main(String[] args) {
        printHMSWelcomeTi

        loadRepository
    
              // Create a new Doctor object
               Doctor 
                         "D003",   
                         "Dr. KCKC",                     //
                         "123456789",        
                         "johndoe",         
                         "johndoe@example.com", 
                         "123-456-7890",           
                         "hashed_pas
                          L
                       
                                "Doctor",  
                          
                       
                                LocalDateT
                          
                     );
                    
                          
                
                        // Step 3: Retrieve and d
                        di
                     dis
                    }
                    
             
         
     

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
