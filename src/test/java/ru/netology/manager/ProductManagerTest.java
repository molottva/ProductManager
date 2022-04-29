package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.data.Book;
import ru.netology.data.Product;
import ru.netology.data.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class ProductManagerTest {
    ProductManager managerEmpty = new ProductManager();
    ProductManager managerOneProduct = new ProductManager();
    ProductManager managerAllProduct = new ProductManager();

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
        managerOneProduct.add(bookOne);
        managerAllProduct.add(bookOne);
        managerAllProduct.add(bookTwo);
        managerAllProduct.add(bookThree);
        managerAllProduct.add(bookFour);
        managerAllProduct.add(bookFive);
        managerAllProduct.add(smartphoneOne);
        managerAllProduct.add(smartphoneTwo);
        managerAllProduct.add(smartphoneThree);
        managerAllProduct.add(smartphoneFour);
        managerAllProduct.add(smartphoneFive);
    }

    //тесты на логику репозитория
    @Test
    void shouldAddRepositoryEmpty() {
        Product[] expected = new Product[]{smartphoneOne};
        managerEmpty.add(smartphoneOne);
        assertArrayEquals(expected, managerEmpty.findAll());
    }

    @Test
    void shouldAddRepositoryWithOneProduct() {
        Product[] expected = new Product[]{
                bookOne,
                smartphoneOne};
        managerOneProduct.add(smartphoneOne);
        assertArrayEquals(expected, managerOneProduct.findAll());
    }

    @Test
    void shouldAddRepositoryWithTenProduct() {
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
        managerAllProduct.add(bookSix);
        assertArrayEquals(expected, managerAllProduct.findAll());
    }

    @Test
    void shouldRemoveIdFromRepositoryWithOneProduct() {
        int id = 1;
        Product[] expected = new Product[0];
        managerOneProduct.removeId(id);
        assertArrayEquals(expected, managerOneProduct.findAll());
    }

    @Test
    void shouldRemoveIdFromRepositoryWithTenProduct() {
        int idBook = 3;
        int idSmartphone = 7;
        Product[] expected = new Product[]{
                bookOne,
                bookTwo,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive};
        managerAllProduct.removeId(idBook);
        managerAllProduct.removeId(idSmartphone);
        assertArrayEquals(expected, managerAllProduct.findAll());
    }

    //тесты на логику менеджера
    @Test
    void shouldMatchesTrue() {
        assertTrue(managerEmpty.matches(smartphoneOne, "iphONE 13"));
    }

    @Test
    void shouldMatchesFalse() {
        assertFalse(managerEmpty.matches(smartphoneOne, "Apple"));
    }

    @Mock
    ProductRepository repository = Mockito.mock(ProductRepository.class);
    @InjectMocks
    ProductManager managerWithMock = new ProductManager(repository);

    @Test
    void shouldSearchByTitleEmptyMock() {
        Product[] returnedEmpty = new Product[0];
        doReturn(returnedEmpty).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle(" "));
    }

    @Test
    void shouldSearchByTitleMockWithOneProductFound() {
        Product[] returnedOneProduct = new Product[]{smartphoneFour};
        doReturn(returnedOneProduct).when(repository).findAll();
        Product[] expected = new Product[]{smartphoneFour};
        assertArrayEquals(expected, managerWithMock.searchByTitle("fold3"));
    }

    @Test
    void shouldSearchByTitleMockWithOneProductUnFound() {
        Product[] returnedOneProduct = new Product[]{smartphoneFour};
        doReturn(returnedOneProduct).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle("iPhone"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductFoundOneResult() {
        Product[] returnedAllProduct = new Product[]{
                bookOne,
                bookTwo,
                bookThree,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneTwo,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[]{bookFour};
        assertArrayEquals(expected, managerWithMock.searchByTitle("исскуство"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductFoundMoreResult() {
        Product[] returnedAllProduct = new Product[]{
                bookOne,
                bookTwo,
                bookThree,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneTwo,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[]{
                bookOne,
                smartphoneOne,
                smartphoneTwo,
                smartphoneFour};
        assertArrayEquals(expected, managerWithMock.searchByTitle("3"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductUnFound() {
        Product[] returnedAllProduct = new Product[]{
                bookOne,
                bookTwo,
                bookThree,
                bookFour,
                bookFive,
                smartphoneOne,
                smartphoneTwo,
                smartphoneThree,
                smartphoneFour,
                smartphoneFive};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle("толстой"));
    }
}