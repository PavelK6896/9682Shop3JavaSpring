package app.web.pavelk.shop3.utils;

import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.repo.specifications.ProductSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if (map.containsKey("title") && !map.get("title").isEmpty()) {
            String title = map.get("title");
            spec = spec.and(ProductSpecifications.titleLike(title));
            filterDefinition.append("&title=").append(title);
        }
        if (categories != null && !categories.isEmpty()) {
            Specification specCategories = null;
            for (Category c : categories) {
                if (specCategories == null) {
                    specCategories = ProductSpecifications.categoryIs(c);
                } else {
                    specCategories = specCategories.or(ProductSpecifications.categoryIs(c));
                }
            }
            spec = spec.and(specCategories);
        }
    }


    public ProductFilter(FilterDto filterDto, List<Category> allById) {
        this.spec = Specification.where(null);
        if (filterDto.getName() != null) {
            spec = spec.and(ProductSpecifications.titleLike(filterDto.getName()));
        }

        if (filterDto.getMin() != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(filterDto.getMin())));
        }

        if (filterDto.getMax() != null) {
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(Integer.parseInt(filterDto.getMax())));
        }

        if (allById != null) {
            Specification specCategories = null;
            for (Category c : allById) {
                if (specCategories == null) {
                    specCategories = ProductSpecifications.categoryIs(c);
                } else {
                    specCategories = specCategories.or(ProductSpecifications.categoryIs(c));
                }
            }
            spec = spec.and(specCategories);
        }

    }


}
