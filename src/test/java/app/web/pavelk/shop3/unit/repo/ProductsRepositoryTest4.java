package app.web.pavelk.shop3.unit.repo;


import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.repo.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@DataJpaTest//тесты jpa
@ActiveProfiles("test")//отдельные настройки
public class ProductsRepositoryTest4 {//тестировать репоситорий
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private TestEntityManager entityManager;//для настройки базы

    @Test
    public void initDbTest() {
        List<Product> productsList = productsRepository.findAll();
        Assertions.assertEquals(4, productsList.size());
    }

    @Test
    public void productRepositoryTest() {
        //настраиваем базу при старте
        Product product = new Product();
        product.setTitle("Apple");
        Product outProduct = entityManager.persist(product);
        entityManager.flush();
        //--

        List<Product> productsList = productsRepository.findAll();
        Assertions.assertEquals(5, productsList.size());
        Assertions.assertEquals("Apple", productsList.get(4).getTitle());//вторая книга название
    }


}
