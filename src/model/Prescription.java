package model;

import java.time.LocalDateTime;
import java.util.List;

public class Prescription {
    private List<PrescribedMedication> medications;
    private LocalDateTime prescriptionDate;

    // Constructor
    public Prescription(List<PrescribedMedication> medications, LocalDateTime prescriptionDate) {
        this.medications = medications;
        this.prescriptionDate = prescriptionDate;
    }

    // Getters and Setters
    public List<PrescribedMedication> getMedications() {
        return medications;
    }

    public void setMedications(List<PrescribedMedication> medications) {
        this.medications = medications;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
}
