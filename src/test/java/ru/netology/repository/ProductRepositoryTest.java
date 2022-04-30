package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Book;
import ru.netology.data.Product;
import ru.netology.data.Smartphone;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    ProductRepository repository = new ProductRepository();
    Product bookOne = new Book(1, "Война и мир. 3 тома", 1000, "Л.Н.Толстой");
    Product bookTwo = new Book(2, "История создания Звездных войн", 3000, "Джордж Лукас");
    Product bookThree = new Book(3, "Русская история в жизнеописаниях ее главнейших деятелей", 2000, "Н.И.Костомаров");
    Product bookFour = new Book(4, "Исскуство войны", 500, "Сунь-цзы");
    Product bookFive = new Book(5, "История искусств. Живопись. Скульптура. Архитектура", 1500, "П.П.Гнедич");
    Product smartphoneOne = new Smartphone(6, "iPhone 13", 200_000, "Apple");
    Product smartphoneTwo = new Smartphone(7, "iPhone 13 Mini", 150_000, "Apple");
    Product smartphoneThree = new Smartphone(8, "Galaxy S22 Ultra", 150_000, "Samsung");
    Product smartphoneFour = new Smartphone(9, "Galaxy Z Fold3 5G", 130_000, "Samsung");
    Product smartphoneFive = new Smartphone(10, "Galaxy A52", 100_000, "Samsung");

    @BeforeEach
    void setup() {
        repository.addProduct(bookOne);
        repository.addProduct(bookTwo);
        repository.addProduct(bookThree);
        repository.addProduct(bookFour);
        repository.addProduct(bookFive);
        repository.addProduct(smartphoneOne);
        repository.addProduct(smartphoneTwo);
        repository.addProduct(smartphoneThree);
        repository.addProduct(smartphoneFour);
        repository.addProduct(smartphoneFive);
    }

    @Test
    void shouldFindByIdSuccess() {
        Product expected = bookFour;
        int id = 4;
        assertEquals(expected, repository.findById(id));
    }

    @Test
    void shouldFindByIdFailed() {
        int id = 3432;
        assertNull(repository.findById(id));
    }

    //тесты на NotFoundException
    @Test
    void shouldRemoveIdSuccess() {
        Product[] expected = new Product[]{
                bookOne,
                bookTwo,
                bookThree,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive};
        int id = 7;
        repository.removeId(id);
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    void shouldRemoveIdFailed() {
        int id = 17;
        assertThrows(NotFoundException.class, () -> {
            repository.removeId(id);
        });
    }

    //тесты на AlreadyExistsException
    @Test
    void shouldAddProductSuccess() {
        Product bookSix = new Book(11, "Мастер и Маргарита", 1500, "М.А.Булгаков");
        Product[] expected = new Product[]{
                bookOne,
                bookTwo,
                bookThree,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneTwo,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive,
                bookSix};
        repository.addProduct(bookSix);
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    void shouldAddProductFailed() {
        assertThrows(AlreadyExistsException.class, () -> {
            repository.addProduct(bookThree);
        });
    }
}