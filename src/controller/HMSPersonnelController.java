package controller;

import java.util.Map;
import model.*;
import repository.*;

public class HMSPersonnelController {

    // Add a new personnel (e.g., Doctor, Patient, etc.)
    public static boolean addPersonnel(HMSPersonnel personnel) {
        if (personnel == null || personnel.getUID() == null) {
            System.out.println("Error: Invalid personnel data.");
            return false;
        }

        // Determine the type of personnel and add it to the appropriate collection
        if (personnel instanceof Doctor) {
            PersonnelRepository.DOCTORS.put(personnel.getUID(), (Doctor) personnel);
            System.out.println("Doctor added: " + personnel.getFullName());
        } else if (personnel instanceof Patient) {
            PersonnelRepository.PATIENTS.put(personnel.getUID(), (Patient) personnel);
            System.out.println("Patient added: " + personnel.getFullName());
        } else if (personnel instanceof Pharmacist) {
            PersonnelRepository.PHARMACISTS.put(personnel.getUID(), (Pharmacist) personnel);
            System.out.println("Pharmacist added: " + personnel.getFullName());
        } else if (personnel instanceof Admin) {
            PersonnelRepository.ADMINS.put(personnel.getUID(), (Admin) personnel);
            System.out.println("Admin added: " + personnel.getFullName());
        } else {
            System.out.println("Error: Unsupported personnel type.");
            return false;
        }

        // Save the updated personnel to the file
        PersonnelRepository.saveAllPersonnelFiles();
        return true;
    }

    // Remove personnel by UID
    public static boolean removePersonnel(String UID, PersonnelFileType type) {
        if (UID == null || UID.isEmpty()) {
            System.out.println("Error: Invalid UID.");
            return false;
        }

        switch (type) {
            case DOCTORS:
                Doctor removedDoctor = PersonnelRepository.DOCTORS.remove(UID);
                if (removedDoctor != null) {
                    System.out.println("Doctor removed: " + removedDoctor.getFullName());
                    PersonnelRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case PATIENTS:
                Patient removedPatient = PersonnelRepository.PATIENTS.remove(UID);
                if (removedPatient != null) {
                    System.out.println("Patient removed: " + removedPatient.getFullName());
                    PersonnelRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case PHARMACISTS:
                Pharmacist removedPharmacist = PersonnelRepository.PHARMACISTS.remove(UID);
                if (removedPharmacist != null) {
                    System.out.println("Pharmacist removed: " + removedPharmacist.getFullName());
                    PersonnelRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            case ADMINS:
                Admin removedAdmin = PersonnelRepository.ADMINS.remove(UID);
                if (removedAdmin != null) {
                    System.out.println("Admin removed: " + removedAdmin.getFullName());
                    PersonnelRepository.saveAllPersonnelFiles();
                    return true;
                }
                break;
            default:
                System.out.println("Error: Unsupported personnel type.");
                return false;
        }

        System.out.println("Error: Personnel not found with UID: " + UID);
        return false;
    }

    // Retrieve personnel by UID
    public static HMSPersonnel getPersonnelByUID(String UID, PersonnelFileType type) {
        if (UID == null || UID.isEmpty()) {
            System.out.println("Error: Invalid UID.");
            return null;
        }

        switch (type) {
            case DOCTORS:
                return PersonnelRepository.DOCTORS.get(UID);
            case PATIENTS:
                return PersonnelRepository.PATIENTS.get(UID);
            case PHARMACISTS:
                return PersonnelRepository.PHARMACISTS.get(UID);
            case ADMINS:
                return PersonnelRepository.ADMINS.get(UID);
            default:
                System.out.println("Error: Unsupported personnel type.");
                return null;
        }
    }

    // Update personnel details
    public static boolean updatePersonnel(String UID, HMSPersonnel updatedPersonnel) {
        if (UID == null || UID.isEmpty() || updatedPersonnel == null) {
            System.out.println("Error: Invalid update request.");
            return false;
        }

        // Determine the type of personnel and update the record
        if (updatedPersonnel instanceof Doctor && PersonnelRepository.DOCTORS.containsKey(UID)) {
            PersonnelRepository.DOCTORS.put(UID, (Doctor) updatedPersonnel);
            System.out.println("Doctor updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Patient && PersonnelRepository.PATIENTS.containsKey(UID)) {
            PersonnelRepository.PATIENTS.put(UID, (Patient) updatedPersonnel);
            System.out.println("Patient updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Pharmacist && PersonnelRepository.PHARMACISTS.containsKey(UID)) {
            PersonnelRepository.PHARMACISTS.put(UID, (Pharmacist) updatedPersonnel);
            System.out.println("Pharmacist updated: " + updatedPersonnel.getFullName());
        } else if (updatedPersonnel instanceof Admin && PersonnelRepository.ADMINS.containsKey(UID)) {
            PersonnelRepository.ADMINS.put(UID, (Admin) updatedPersonnel);
            System.out.println("Admin updated: " + updatedPersonnel.getFullName());
        } else {
            System.out.println("Error: Personnel not found for update.");
            return false;
        }

        // Save the updated data
        PersonnelRepository.saveAllPersonnelFiles();
        return true;
    }

    // List all personnel of a specific type
    public static void listAllPersonnel(PersonnelFileType type) {
        Map<String, ? extends HMSPersonnel> personnelMap = null;

        switch (type) {
            case DOCTORS:
                personnelMap = PersonnelRepository.DOCTORS;
                break;
            case PATIENTS:
                personnelMap = PersonnelRepository.PATIENTS;
                break;
            case PHARMACISTS:
                personnelMap = PersonnelRepository.PHARMACISTS;
                break;
            case ADMINS:
                personnelMap = PersonnelRepository.ADMINS;
                break;
            default:
                System.out.println("Error: Unsupported personnel type.");
                return;
        }

        if (personnelMap != null && !personnelMap.isEmpty()) {
            System.out.println("Listing all personnel of type: " + type);
            for (HMSPersonnel personnel : personnelMap.values()) {
                System.out.println("UID: " + personnel.getUID() + ", Name: " + personnel.getFullName());
            }
        } else {
            System.out.println("No personnel found for type: " + type);
        }
    }
}