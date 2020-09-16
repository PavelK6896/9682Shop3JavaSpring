package app.web.pavelk.shop3.unit.service;

import app.web.pavelk.shop3.beans.Cart;
import app.web.pavelk.shop3.entity.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CartTest1 {
    @Autowired
    private Cart cart;

    @Test
    public void addAndClearTest() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            long bookId = i  + 1;
            product.setId(bookId);
            product.setPrice(new BigDecimal(100 + bookId * 10));
            product.setTitle("Product #" + bookId);
            cart.add(product);
        }
        Assertions.assertEquals(10, cart.getItems().size());
        cart.clear();
        Assertions.assertEquals(0, cart.getItems().size());
    }

    @Test
    public void decrementTest() {
        Product product = new Product();
        long bookId = 1;
        product.setId(bookId);
        product.setPrice(new BigDecimal(100 + bookId * 10));
        product.setTitle("Product #" + bookId);
        for (int i = 0; i < 10; i++) {
            cart.add(product);

        }
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(10, cart.getSize());
        cart.decrement(product);
        Assertions.assertEquals(9, cart.getSize());
        Assertions.assertEquals(1, cart.getItems().size());
    }

    @Test
    public void removeByProductIdTest() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            long bookId = i  + 1;
            product.setId(bookId);
            product.setPrice(new BigDecimal(100 + bookId * 10));
            product.setTitle("Product #" + bookId);
            cart.add(product);
        }
        Assertions.assertEquals(10, cart.getItems().size());
        cart.removeByProductId(1L);
        cart.removeByProductId(2L);
        Assertions.assertEquals(8, cart.getItems().size());
    }





}
