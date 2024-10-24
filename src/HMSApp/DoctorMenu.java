package HMSApp;

public class DoctorMenu {
    private String username;
    // extract the information of patient and create a Doctor object using the username
    public DoctorMenu(String username){
        this.username=username;
    }

    public static void printMenu(){
        System.out.println("Doctor Menu: ");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments ");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout ");
    }
}
