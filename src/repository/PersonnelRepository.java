package repository;

import java.util.HashMap;
import model.Admin;
import model.Doctor;
import model.Patient;
import model.Pharmacist;

import java.io.*;

public class PersonnelRepository {
    private static final String folder = "data";

    // Static data collections for personnel
    public static HashMap<String, Doctor> DOCTORS = new HashMap<>();
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Pharmacist> PHARMACISTS = new HashMap<>();
    public static HashMap<String, Admin> ADMINS = new HashMap<>();
    
    /**
     * Method to save all personnel files into the data store
     */
    public static void saveAllPersonnelFiles() {
        persistData(PersonnelFileType.DOCTORS);
        persistData(PersonnelFileType.PATIENTS);
        persistData(PersonnelFileType.PHARMACISTS);
        persistData(PersonnelFileType.ADMINS);
    }

    /**
     * Method to persist a particular file type into the data store
     * @param fileType type of file to be saved
     */
    public static void persistData(PersonnelFileType fileType) {
        writeSerializedObject(fileType);
    }

    /**
     * Method to read data from file based on the file type
     * @param fileType type of file to be read
     */
    public static void readData(PersonnelFileType fileType) {
        readSerializedObject(fileType);
    }

    private static boolean readSerializedObject(PersonnelFileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/repository/" + folder + "/" + fileType.fileName + fileExtension;

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

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

        } catch (EOFException e) {
            System.out.println("No data found, initializing empty personnel records.");
            initializeEmptyCollections(fileType);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean writeSerializedObject(PersonnelFileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/repository/" + folder + "/" + fileType.fileName + fileExtension;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

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

        } catch (IOException e) {
            System.out.println("Error saving personnel data: " + e.getMessage());
            return false;
        }
        return true;
    }

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

    public static boolean clearPersonnelDatabase() {
        DOCTORS.clear();
        PATIENTS.clear();
        PHARMACISTS.clear();
        ADMINS.clear();
        saveAllPersonnelFiles();
        return true;
    }
}
