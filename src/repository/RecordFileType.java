package repository;

public enum RecordFileType {
    MEDICAL_RECORDS("medicalRecords"),
    APPOINTMENT_RECORDS("appointmentRecords"),
    PAYMENT_RECORDS("paymentRecords");

    public final String fileName;

    RecordFileType(String fileName) {
        this.fileName = fileName;
    }
}
