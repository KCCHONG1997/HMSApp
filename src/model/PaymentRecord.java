package model;

import java.time.LocalDateTime;

public class PaymentRecord extends HMSRecords {
    private double paymentAmount;
    private String patientID;

    // Constructor
    public PaymentRecord(String recordID,
    		LocalDateTime createdDate, 
    		LocalDateTime updatedDate,
            RecordStatusType recordStatus, 
            String patientID,
            double paymentAmount) 
    {
        super(recordID,createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.paymentAmount = paymentAmount;
    }

	// Getters and Setters
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
