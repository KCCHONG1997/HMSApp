package model;

import java.time.LocalDate;

public class Medicine {
    private String medicineID;
    private String name;
    private String manufacturer;
    private LocalDate expiryDate;
    private int inventoryStock;  // New field for inventory stock

    // Constructor
    public Medicine(String medicineID, String name, String manufacturer, LocalDate expiryDate, int inventoryStock) {
        this.medicineID = medicineID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.inventoryStock = inventoryStock;
    }

    // Getters and Setters
    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInventoryStock() {
        return inventoryStock;
    }

    public void setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
    }
}