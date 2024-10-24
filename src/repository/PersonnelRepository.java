package repository;

import java.io.*;
import java.util.HashMap;
import model.*;

public class PersonnelRepository {
    private static final String folder = "data";

    // Static data collections for personnel
    public static HashMap<String, Doctor> DOCTORS = new HashMap<>();
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Pharmacist> PHARMACISTS = new HashMap<>();
    public static HashMap<String, Admin> ADMINS = new HashMap<>();

    // Method to save all personnel files into the data store
    public static void saveAllPersonnelFiles() {
        persistData(PersonnelFileType.DOCTORS);
        persistData(PersonnelFileType.PATIENTS);
        persistData(PersonnelFileType.PHARMACISTS);
        persistData(PersonnelFileType.ADMINS);
    }

    // Method to persist a particular file type into the data store
    public static void persistData(PersonnelFileType fileType) {
        writeSerializedObject(fileType);
    }

    // Method to read data from file based on the file type
    public static void readData(PersonnelFileType fileType) {
        readSerializedObject(fileType);
    }

    // Reads the serialized object from a file and loads it into the appropriate collection
    private static boolean readSerializedObject(PersonnelFileType fileType) {
        String fileExtension = ".dat";
        String directoryPath = "./src/repository/" + folder;
        String filePath = directoryPath + "/" + fileType.fileName + fileExtension;

        try {
            // Ensure the data directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it doesn't exist
            }

            // Now proceed to read the file
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object object = objectInputStream.readObject();

            if (!(object instanceof HashMap)) {
                return false;
            }

            // Populate the appropriate collection based on fileType
            switch (fileType) {
                case DOCTORS:
                    DOCTORS = (HashMap<String, Doctor>) object;
                    break;
                case PATIENTS:
                    PATIENTS = (HashMap<String, Patient>) object;
                    break;
                case PHARMACISTS:
                    PHARMACISTS = (HashMap<String, Pharmacist>) object;
                    break;
                case ADMINS:
                    ADMINS = (HashMap<String, Admin>) object;
                    break;
                default:
                    break;
            }

            objectInputStream.close();
            fileInputStream.close();

        } catch (EOFException e) {
            System.out.println("No data found, initializing empty personnel records.");
            initializeEmptyCollections(fileType);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Writes the serialized object to a file based on fileType
    private static boolean writeSerializedObject(PersonnelFileType fileType) {
        String fileExtension = ".dat";
        String directoryPath = "./src/repository/" + folder;
        String filePath = directoryPath + "/" + fileType.fileName + fileExtension;

        try {
            // Ensure the data directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it doesn't exist
            }

            // Now proceed to write the file
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            switch (fileType) {
                case DOCTORS:
                    objectOutputStream.writeObject(DOCTORS);
                    break;
                case PATIENTS:
                    objectOutputStream.writeObject(PATIENTS);
                    break;
                case PHARMACISTS:
                    objectOutputStream.writeObject(PHARMACISTS);
                    break;
                case ADMINS:
                    objectOutputStream.writeObject(ADMINS);
                    break;
                default:
                    break;
            }

            objectOutputStream.close();
            fileOutputStream.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error saving personnel data: " + e.getMessage());
            return false;
        }
    }

    // Initializes empty collections for a particular file type
    private static void initializeEmptyCollections(PersonnelFileType fileType) {
        switch (fileType) {
            case DOCTORS:
                DOCTORS = new HashMap<>();
                break;
            case PATIENTS:
                PATIENTS = new HashMap<>();
                break;
            case PHARMACISTS:
                PHARMACISTS = new HashMap<>();
                break;
            case ADMINS:
                ADMINS = new HashMap<>();
                break;
            default:
                break;
        }
    }

    // Clears all personnel data
    public static boolean clearPersonnelDatabase() {
        DOCTORS.clear();
        PATIENTS.clear();
        PHARMACISTS.clear();
        ADMINS.clear();
        saveAllPersonnelFiles();
        return true;
    }
}
