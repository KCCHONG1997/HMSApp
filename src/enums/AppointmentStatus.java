package enums;

import model.RecordStatusType;

public enum AppointmentStatus {
    CONFIRMED, CANCELED, COMPLETED, AVAILABLE;

    @Override
    public String toString() {
        switch (this) {
            case CONFIRMED:
                return "Confirmed";
            case CANCELED:
                return "Canceled";
            case COMPLETED:
                return "Completed";
            case AVAILABLE:
                return "Available";
            default:
                return "Unknown";
        }
    }

    public static AppointmentStatus toEnumAppointmentStatus(String status) {
        switch (status) {
            case "Confirmed":
                return CONFIRMED;
            case "Canceled":
                return CANCELED;
            case "Completed":
                return COMPLETED;
            case "Available":
                return AVAILABLE;
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }

}