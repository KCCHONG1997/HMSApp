package model;

import java.time.LocalDateTime;

public class TreatmentPlan {
    private String treatmentDescription;
    private LocalDateTime treatmentDate;

    // Constructor
    public TreatmentPlan(String treatmentDescription, LocalDateTime treatmentDate) {
        this.treatmentDescription = treatmentDescription;
        this.treatmentDate = treatmentDate;
    }

    // Getters and Setters
    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    public LocalDateTime getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(LocalDateTime treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
}
