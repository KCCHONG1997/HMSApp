package HMSApp;
import java.util.Scanner;
public class Login {
    public static void doctorLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        //if username exists in doctor database, then continue
        // else  HMSMain.main(null);
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        //hashed the password and compare with database
        // if not valid HMSMain.main(null);
        DoctorMenu(username);

    }

    public static void pharmacistLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        //if username exists in pharmacist database, then continue
        // else  HMSMain.main(null);
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        //hashed the password and compare with database
        // if not valid HMSMain.main(null);
        PharmacistMenu(username);

    }

    public static void patientLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        //if username exists in patient database, then continue
        // else  HMSMain.main(null);
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        //hashed the password and compare with database
        // if not valid HMSMain.main(null);
        PatientMenu(username);

    }

    public static void administratorLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        //if username exists in administrator database, then continue
        // else  HMSMain.main(null);
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        //hashed the password and compare with database
        // if not valid HMSMain.main(null);
        AdministratorMenu(username);

    }
}
