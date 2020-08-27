package app.web.pavelk.shop3.soap;


import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ws.product.GetProductAllRequest;
import ws.product.GetProductAllResponse;
import ws.product.GetProductRequest;
import ws.product.GetProductResponse;

import java.util.List;


@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "ws/product";

    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        System.out.println("ProductEndpoint");
        GetProductResponse response = new GetProductResponse();
        Product byId = productsService.findById((long) request.getId());
        ws.product.Product product = new ws.product.Product();
        product.setDescription(byId.getDescription());
        product.setId(byId.getId());
        product.setPrice(byId.getPrice());
        product.setTitle(byId.getTitle());
        response.setProduct(product);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductAllRequest")
    @ResponsePayload
    public GetProductAllResponse getProductAll(@RequestPayload GetProductAllRequest request) {
        System.out.println("getProductAll ");
        GetProductAllResponse getProductAllResponse = new GetProductAllResponse();
        List<ws.product.Product> products = getProductAllResponse.getProducts();

        productsService.findAll().forEach(f -> {
            ws.product.Product product = new ws.product.Product();
            product.setTitle(f.getTitle());
            product.setPrice(f.getPrice());
            product.setId(f.getId());
            product.setDescription(f.getDescription());
            products.add(product);
       });

        return getProductAllResponse;
    }


}
