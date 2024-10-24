package model;

import java.time.LocalDateTime;

public class PaymentRecord extends HMSRecords {
    private double paymentAmount;

    // Constructor
    public PaymentRecord(String recordID, Doctor createdBy, LocalDateTime createdDate, LocalDateTime updatedDate,
                         RecordStatusType recordStatus, String description, Patient patient,
                         double paymentAmount) {
        super(recordID, createdBy, createdDate, updatedDate, recordStatus, description, patient);
        this.paymentAmount = paymentAmount;
    }

    // Getters and Setters
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
