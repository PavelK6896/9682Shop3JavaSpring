package app.web.pavelk.shop3.integration;

import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.repo.ProductsRepository;
import app.web.pavelk.shop3.service.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class Test6 {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @Test
    public void test6() {
        Product product = new Product();
        product.setTitle("pr");
        productsRepository.save(product);
        Product product1 = productsService.findById(5L);
        Assertions.assertNotNull(product1);
        Assertions.assertEquals(product, product1);
    }
}
