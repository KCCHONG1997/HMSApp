package model;

import java.time.LocalDateTime;

public class PrescribedMedication {
    private Medicine medicationName;
    private int medicineQuantity;
    private LocalDateTime prescriptionStartDate;
    private LocalDateTime prescriptionEndDate;
    private PrescriptionStatus prescriptionStatus;
    private String dosage;

    // Constructor
    public PrescribedMedication(Medicine medicationName, int medicineQuantity, LocalDateTime prescriptionStartDate,
                                LocalDateTime prescriptionEndDate, PrescriptionStatus prescriptionStatus, String dosage) {
        this.medicationName = medicationName;
        this.medicineQuantity = medicineQuantity;
        this.prescriptionStartDate = prescriptionStartDate;
        this.prescriptionEndDate = prescriptionEndDate;
        this.prescriptionStatus = prescriptionStatus;
        this.dosage = dosage;
    }

    // Getters and Setters
    public Medicine getMedication() {
        return medicationName;
    }

    public void setMedication(Medicine medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public LocalDateTime getPrescriptionStartDate() {
        return prescriptionStartDate;
    }

    public void setPrescriptionStartDate(LocalDateTime prescriptionStartDate) {
        this.prescriptionStartDate = prescriptionStartDate;
    }

    public LocalDateTime getPrescriptionEndDate() {
        return prescriptionEndDate;
    }

    public void setPrescriptionEndDate(LocalDateTime prescriptionEndDate) {
        this.prescriptionEndDate = prescriptionEndDate;
    }

    public PrescriptionStatus getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(PrescriptionStatus prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
