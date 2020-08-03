package app.web.pavelk.shop3.controller.product;

import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.service.CategoriesService;
import app.web.pavelk.shop3.service.ProductsService;
import app.web.pavelk.shop3.utils.ProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private CategoriesService categoriesService;

    @Autowired
    public ProductsController(ProductsService productsService,
                              CategoriesService categoriesService) {
        this.productsService = productsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model,
                          @RequestParam Map<String, String> requestParams,
                          @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {

        List<Category> categoriesFilter = null;
        if (categoriesIds != null) {
            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
        }

        ProductFilter productFilter = new ProductFilter(requestParams, categoriesFilter);
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        Page<Product> products = productsService.findAll(productFilter.getSpec(), pageNumber);
        model.addAttribute("products", products);
        model.addAttribute("filterDef", productFilter.getFilterDefinition().toString()); // attribute save

        return "page/all_products";
    }



    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", categoriesService.findAll());
        model.addAttribute("product", productsService.findAll());
        return "page/admin/add_product_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute Product product,
                                 @RequestParam(required = false) List<Long> listIdLongCategory) {
        if (listIdLongCategory != null) {
            List<Category> categories = categoriesService.findAllById(listIdLongCategory);
            product.setCategories(categories);
        }
        productsService.saveOrUpdate(product);
        return "redirect:/products/add";
    }

    @PostMapping("/add/category")
    public String saveNewCategory(@ModelAttribute Category category) {
        categoriesService.save(category);
        return "redirect:";
    }

}