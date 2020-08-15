package app.web.pavelk.shop3.service;

import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.product.dto.CategoryDto;
import app.web.pavelk.shop3.repo.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    private CategoriesRepository categoriesRepository;

    @Autowired
    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> findAll() {//1
        return categoriesRepository.findAll();
    }


    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoriesRepository.findAllById(ids);
    }


    public void save(Category category) {
        categoriesRepository.save(category);
    }

    public List<Category> findAllById(List<Long> searchValues) {


        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(searchValues);


        return categoriesRepository.findAllById(searchValues);
    }

}
