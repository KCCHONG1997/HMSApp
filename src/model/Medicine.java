package model;
import java.time.LocalDateTime;
public class Medicine {
    private String medicineName;
    private String medicineID;
    private String manufacturer;
    private LocalDateTime expiryDate;
    private int inventoryStock;
    
    
    public Medicine(String medicineName, String medicineID, String manufacturer, LocalDateTime expiryDate, int inventoryStock) {
        this.medicineName = medicineName;
        this.medicineID = medicineID;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.inventoryStock = inventoryStock;
    }
    
	public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInventoryStock() {
        return inventoryStock;
    }

    public void setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
    }


}
