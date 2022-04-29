package ru.netology.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends Product {
    protected String author;

    public Book(int id, String title, int price, String author) {
        super(id, title, price);
        this.author = author;
    }

    public boolean matches(String searchInput) {
        if (super.matches(searchInput)) {
            return true;
        } else {
            return getAuthor().toUpperCase().contains(searchInput.toUpperCase());
        }
    }
}
