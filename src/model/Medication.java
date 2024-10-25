package model;

public class Medication {
    private String medicineName;
    private int medicineQuantity;
    private int periodDays; // The duration for which the medication should be taken (in days)
    
    // Optional fields for dosage, frequency, etc.
    private String dosage;  // e.g., "500mg"
    private String frequency; // e.g., "Twice a day"

    // Constructor
    public Medication(String medicineName, int medicineQuantity, int periodDays, String dosage, String frequency) {
        this.medicineName = medicineName;
        this.medicineQuantity = medicineQuantity;
        this.periodDays = periodDays;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    // Getters and Setters
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    // Optional: toString() method to display medication details
    @Override
    public String toString() {
        return "Medication{" +
                "medicineName='" + medicineName + '\'' +
                ", medicineQuantity=" + medicineQuantity +
                ", periodDays=" + periodDays +
                ", dosage='" + dosage + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
