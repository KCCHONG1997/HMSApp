package repository;

public enum PersonnelFileType {
    DOCTORS("doctors"),
    PATIENTS("patients"),
    PHARMACISTS("pharmacists"),
    ADMINS("admins");

    public final String fileName;

    PersonnelFileType(String fileName) {
        this.fileName = fileName;
    }
}
