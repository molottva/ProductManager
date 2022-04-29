package ru.netology.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Smartphone extends Product {
    protected String manufacturer;

    public Smartphone(int id, String title, int price, String manufacturer) {
        super(id, title, price);
        this.manufacturer = manufacturer;
    }

    public boolean matches(String searchInput) {
        if (super.matches(searchInput)) {
            return true;
        } else {
            return getManufacturer().toUpperCase().contains(searchInput.toUpperCase());
        }
    }
}
