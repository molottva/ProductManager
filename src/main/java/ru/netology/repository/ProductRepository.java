package ru.netology.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.data.Product;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRepository {
    private Product[] products = new Product[0];

    //добавление продукта в конец массива
    public void addProduct (Product addProduct) {
        Product[] tmp = new Product[products.length + 1];
        System.arraycopy(products, 0, tmp, 0, products.length);
        tmp[products.length] = addProduct;
        products = tmp;
    }

    //получить все сохранненные продукты в порядке добавления
    public Product[] findAll () {
        return getProducts();
    }

    //удалить по id
    public void removeId (int id) {
        Product[] tmp = new Product[products.length - 1];
        int i = 0;
        for (Product product: products) {
            if (product.getId() != id) {
                tmp[i] = product;
                i++;
            }
        }
        products = tmp;
    }
}


