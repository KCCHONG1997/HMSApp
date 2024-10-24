package repository;

import java.util.HashMap;
import model.*;

import java.io.*;

public class RecordsRepository {
    private static final String folder = "data";

    // Static data collections for different record types
    public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
    public static HashMap<String, AppointmentRecord> APPOINTMENT_RECORDS = new HashMap<>();
    public static HashMap<String, PaymentRecord> PAYMENT_RECORDS = new HashMap<>();

    /**
     * Method to save all record files into the data store
     */
    public static void saveAllRecordFiles() {
        persistData(RecordFileType.MEDICAL_RECORDS);
        persistData(RecordFileType.APPOINTMENT_RECORDS);
        persistData(RecordFileType.PAYMENT_RECORDS);
    }

    /**
     * Method to persist a particular file type into the data store
     * @param fileType type of file to be saved
     */
    public static void persistData(RecordFileType fileType) {
        writeSerializedObject(fileType);
    }

    /**
     * Method to read data from file based on the file type
     * @param fileType type of file to be read
     */
    public static void readData(RecordFileType fileType) {
        readSerializedObject(fileType);
    }

    private static boolean readSerializedObject(RecordFileType fileType) {
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
                case MEDICAL_RECORDS:
                    MEDICAL_RECORDS = (HashMap<String, MedicalRecord>) object;
                    break;
                case APPOINTMENT_RECORDS:
                    APPOINTMENT_RECORDS = (HashMap<String, AppointmentRecord>) object;
                    break;
                case PAYMENT_RECORDS:
                    PAYMENT_RECORDS = (HashMap<String, PaymentRecord>) object;
                    break;
                default:
                    break;
            }

        } catch (EOFException e) {
            System.out.println("No data found, initializing empty records.");
            initializeEmptyCollections(fileType);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean writeSerializedObject(RecordFileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/repository/" + folder + "/" + fileType.fileName + fileExtension;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            switch (fileType) {
                case MEDICAL_RECORDS:
                    objectOutputStream.writeObject(MEDICAL_RECORDS);
                    break;
                case APPOINTMENT_RECORDS:
                    objectOutputStream.writeObject(APPOINTMENT_RECORDS);
                    break;
                case PAYMENT_RECORDS:
                    objectOutputStream.writeObject(PAYMENT_RECORDS);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println("Error saving record data: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static void initializeEmptyCollections(RecordFileType fileType) {
        switch (fileType) {
            case MEDICAL_RECORDS:
                MEDICAL_RECORDS = new HashMap<>();
                break;
            case APPOINTMENT_RECORDS:
                APPOINTMENT_RECORDS = new HashMap<>();
                break;
            case PAYMENT_RECORDS:
                PAYMENT_RECORDS = new HashMap<>();
                break;
            default:
                break;
        }
    }

    public static boolean clearRecordDatabase() {
        MEDICAL_RECORDS.clear();
        APPOINTMENT_RECORDS.clear();
        PAYMENT_RECORDS.clear();
        saveAllRecordFiles();
        return true;
    }
}
