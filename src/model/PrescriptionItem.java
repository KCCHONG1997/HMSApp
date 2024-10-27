package model;

public class PrescriptionItem {
    private String medicineQuantity;
    private int periodDays;
    private Medicine medicine;

    public PrescriptionItem(String medicineQuantity, int periodDays, Medicine medicine) {
        this.medicineQuantity = medicineQuantity;
        this.periodDays = periodDays;
        this.medicine = medicine;
    }

    public String getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(String medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
