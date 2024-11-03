package model;

import java.time.LocalDateTime;

public class PaymentRecord extends HMSRecords {
    private double paymentAmount;
    private String patientID;

    // Constructor
    public PaymentRecord(
    		LocalDateTime createdDate, 
    		LocalDateTime updatedDate,
            RecordStatusType recordStatus, 
            String patientID,
            double paymentAmount) 
    {
        super(createdDate, updatedDate, recordStatus);
        this.patientID = patientID;
        this.paymentAmount = paymentAmount;
    }
	// TODO Auto-generated constructor stub
	

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
