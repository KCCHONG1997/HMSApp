package model;

public enum RecordStatusType {
    ACTIVE,     // Record is actively in use
    INACTIVE,   // Record is not in use but available
    ARCHIVED,   // Record is archived for future reference
    DELETED;    // Record is marked as deleted (soft delete)

    // Optional: You can define methods to return more information about the status
    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case INACTIVE:
                return "Inactive";
            case ARCHIVED:
                return "Archived";
            case DELETED:
                return "Deleted";
            default:
                return "Unknown";
        }
    }


    public static RecordStatusType toEnumRecordStatusType(String status) {
        switch (status) {
            case "Active":
                return ACTIVE;
            case "Inactive":
                return INACTIVE;
            case "Archived":
                return ARCHIVED;
            case "Deleted":
                return DELETED;
            default:
                return null;  // or throw an exception if you want to handle invalid inputs differently
        }
    }
}