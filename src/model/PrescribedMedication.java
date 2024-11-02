package model;
import java.time.LocalDateTime;
import enums.PrescriptionStatus;
public class PrescribedMedication {
    private Medicine medicationName;
    private String medicineQuantity;
    private LocalDateTime prescriptionStartDate;
    private LocalDateTime prescriptionEndDate;
    private PrescriptionStatus PresciptionStatus;
    private Medicine medicine;
    private String dosage;

    public PrescribedMedication(Medicine medicationName, String medicineQuantity, LocalDateTime prescriptionStartDate, LocalDateTime prescriptionEndDate, PrescriptionStatus presciptionStatus, Medicine medicine, String dosage) {
        this.medicationName = medicationName;
        this.medicineQuantity = medicineQuantity;
        this.prescriptionStartDate = prescriptionStartDate;
        this.prescriptionEndDate = prescriptionEndDate;
        PresciptionStatus = presciptionStatus;
        this.medicine = medicine;
        this.dosage = dosage;
    }
    public Medicine getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(Medicine medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(String medicineQuantity) {
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

    public PrescriptionStatus getPresciptionStatus() {
        return PresciptionStatus;
    }

    public void setPresciptionStatus(PrescriptionStatus presciptionStatus) {
        PresciptionStatus = presciptionStatus;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }




}