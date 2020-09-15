package app.web.pavelk.shop3.unit.service;

import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.exceptions.ProductNotFoundException;
import app.web.pavelk.shop3.repo.ProductsRepository;
import app.web.pavelk.shop3.repo.UsersRepository;
import app.web.pavelk.shop3.service.ProductsService;
import app.web.pavelk.shop3.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductsServiceTest3 {
    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;


    @Test
    public void findOneUserTest() {
        Product product = new Product();
        product.setId(1L);

        Mockito.doReturn(product)
                .when(productsRepository)
                .findById(1L);

        Product product1 = productsService.findById(1L);
        Assertions.assertNotNull(product1);
        Mockito.verify(productsRepository, Mockito.times(1))
                .findById(ArgumentMatchers.eq(1L));

    }

}
