package model;
import java.time.LocalDateTime;
public class Medicine {
    private String medicineName;
    private String medicineID;
    private String manufacturer;
    private LocalDateTime expiryDate;
    private int inventoryStock;
    private LocalDateTime  replenishDate;
    
    public Medicine(String medicineID, String medicineName, String manufacturer, LocalDateTime expiryDate, int inventoryStock, LocalDateTime replenishDate) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.inventoryStock = inventoryStock;
        this.replenishDate = replenishDate;
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



	public LocalDateTime getReplenishDate() {
		return replenishDate;
	}



	public void setReplenishDate(LocalDateTime replenishDate) {
		this.replenishDate = replenishDate;
	}


}
