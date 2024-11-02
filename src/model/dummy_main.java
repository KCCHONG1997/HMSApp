package model;
import repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static repository.DiagnosisRepository.*;
import static repository.RecordsRepository.*;

public class dummy_main {
    public static void main(String[] args) {
//        System.out.println("hello");
//        PersonnelRepository.loadAllPersonnelFiles();
//        Doctor doctorA = PersonnelRepository.DOCTORS.get("D001");
//        //RecordsRepository.loadAllRecordFiles();
//        //RecordsRepository.
//        System.out.println(doctorA.getUID());



//
//
//    public Patient(String UID, String fullName, String idCard, String username, String email, String phoneNo,
//                String passwordHash, LocalDateTime DoB, String gender, String role,
//                String insuranceInfo, String allergies, LocalDateTime dateOfAdmission) {
//            super(UID, fullName, idCard, username, email, phoneNo, passwordHash, DoB, gender, role);
//
//
//            this.insuranceInfo = insuranceInfo;
//            this.allergies = allergies;
//            this.dateOfAdmission = dateOfAdmission;
//        }


//            public MedicalRecord(String recordID,
//                LocalDateTime createdDate, LocalDateTime updatedDate,
//                RecordStatusType recordStatus,String patientID,String doctorID,
//                String bloodType,ArrayList<model.DiagnosisRecord> diagnosis) {
//            super(recordID, createdDate, updatedDate, recordStatus);
//            this.patientID = patientID;
//            this.doctorID = doctorID;
//            this.bloodType = bloodType;
//            this.Diagnosis= diagnosis;
//
//        }


//            public TreatmentPlans(String diagnosisID, LocalDateTime treatmentDate, String treatmentDescription) {
//            this.diagnosisID = diagnosisID;
//            this.treatmentDate = treatmentDate;
//            this.treatmentDescription = treatmentDescription;
//        }

        //testing
//        Patient patientA = new Patient(
//                "P001", "ck","M22222W","ckhng",
//                "hngbob77@gmail.com","+658888888","1234567",LocalDateTime.now(),
//                "male","cooker","noInsurance","noAllergies", LocalDateTime.now());
//


//
//        TreatmentPlans treatmentPlansA = new TreatmentPlans("DIAG001",LocalDateTime.now(),"MRI Scan");
//        TreatmentPlans treatmentPlansB = new TreatmentPlans("DIAG002",LocalDateTime.now(),"cook it");
//        DiagnosisRecord diagnosisRecordA = new DiagnosisRecord("P001","DIAG003",LocalDateTime.now(),
//                treatmentPlansA,"too hot");
//        DiagnosisRecord diagnosisRecordB = new DiagnosisRecord("P001","DIAG002",LocalDateTime.now(),
//                treatmentPlansA,"too siao");
//
//
//        ArrayList<DiagnosisRecord> diagnosisRecordsA = new ArrayList<>();
//        diagnosisRecordsA.add(diagnosisRecordA);
//        diagnosisRecordsA.add(diagnosisRecordB);
//
//
//        MedicalRecord medicalRecord = new MedicalRecord(
//                "MR002", LocalDateTime.now(), LocalDateTime.now(),
//                RecordStatusType.ACTIVE, "P001", "D001", "TypeO",diagnosisRecordsA
//        );
////        public static HashMap<String, MedicalRecord> MEDICAL_RECORDS = new HashMap<>();
//        MEDICAL_RECORDS.put(medicalRecord.getRecordID(), medicalRecord);
//        saveAllRecordFiles();


        //HashMap<String, ArrayList<DiagnosisRecord>> patientDiagnosisRecords = new HashMap<>();
//        patientDiagnosisRecords.put("P002",diagnosisRecordsA);
//        saveDiagnosisRecordsToCSV("diagnosis_records.csv", patientDiagnosisRecords);
        DiagnosisRepository.loadDiagnosisRecordsFromCSV("diagnosis_records.csv", patientDiagnosisRecords);
        loadAllRecordFiles();
        MedicalRecord medicalRecord1 =  MEDICAL_RECORDS.get("MR002");
        System.out.println(medicalRecord1.getDiagnosis().getFirst().getDiagnosisID());







    }
}
