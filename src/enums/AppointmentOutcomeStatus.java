package enums;

public enum AppointmentOutcomeStatus {
    INCOMPLETED, COMPLETED;

    @Override
    public String toString() {
        switch (this) {
            case INCOMPLETED:
                return "Incompleted";
            case COMPLETED:
                return "Completed";
            default:
                return "Unknown";
        }
    }

    public static AppointmentOutcomeStatus toEnumAppointmentOutcomeStatus(String status) {
        switch (status) {
            case "Incompleted":
                return INCOMPLETED;
            case "Completed":
                return COMPLETED;
            default:
                return null; // or throw an exception if you want to handle invalid inputs differently
        }
    }
}
