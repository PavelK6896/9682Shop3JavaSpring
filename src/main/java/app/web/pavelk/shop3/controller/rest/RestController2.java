package app.web.pavelk.shop3.controller.rest;


import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.entity.product.Views;
import app.web.pavelk.shop3.service.CategoriesService;
import app.web.pavelk.shop3.service.ProductsService;
import app.web.pavelk.shop3.utils.ProductFilter;
import app.web.pavelk.shop3.utils.FilterDto;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class RestController2 {

    private CategoriesService categoriesService;
    private ProductsService productsService;

    @Autowired
    public RestController2(CategoriesService categoriesService, ProductsService productsService) {
        this.categoriesService = categoriesService;
        this.productsService = productsService;
    }


    @GetMapping(value = "/categories", produces = "application/json")
    @JsonView(Views.IdTitle.class)
    public List<Category> getAllCategory() {
        List<Category> all = categoriesService.findAll();
        System.out.println(all);
        return all;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/filter")
    public Page<Product> getFilter(@RequestBody String jsonString) throws JsonProcessingException {

        FilterDto filterDto = objectMapper.readValue(jsonString, FilterDto.class);

        List<Category> allById = null;

        if (filterDto.getSet() != null) {
            List<Long> collect = filterDto.getSet().stream().map(v -> {
                return Long.parseLong(v);
            }).collect(Collectors.toList());
            allById = categoriesService.findAllById(collect);
        }

        ProductFilter productFilter = new ProductFilter(filterDto, allById);
        Page<Product> products = productsService.findAll(productFilter.getSpec(), 1);
        return products;

    }


}
