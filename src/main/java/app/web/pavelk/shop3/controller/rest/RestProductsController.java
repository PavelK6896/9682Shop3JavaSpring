package app.web.pavelk.shop3.controller.rest;


import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.repo.ProductsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class RestProductsController extends AbstractRestController<Product, ProductsRepository> {
    public RestProductsController(ProductsRepository repo) {
        super(repo);
    }
}

