package app.web.pavelk.shop3.controller.rest;

import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.exceptions.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//swagger-ui.html
//T тип для контролера по сущьности созданной
//R репозиторий для этой же сущьности
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:8080")
@Api("CRUD")
@Slf4j
public abstract class AbstractRestController<T, R extends JpaRepository<T, Long>> {
    protected R repo;

    //инжектим репозитой в клас
    public AbstractRestController(R repo) {
        this.repo = repo;
    }

    //1GET
    @GetMapping(value = "/page", produces = "application/json")
    public Page<T> getAllPage(@PageableDefault Pageable pageable) {
        System.out.println(pageable);
        return repo.findAll(pageable);
    }

    //2GET
    @GetMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("getAllList")
    public List<T> getAllList() {
        return repo.findAll();
    }

    //3
    @GetMapping(value = "resp/{id}", produces = "application/json")
    @ApiOperation("Returns one product by id")
    public ResponseEntity<?> getOneResT(
            @PathVariable(value = "id") @ApiParam("Id of the product to be requested. Cannot be empty") Long id) {
        if (!repo.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
        }
        return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
    }

    //4
    @GetMapping("{id}")//2 // спринг сам должен вытищить из базы объект T
    public T getOneT(@PathVariable(value = "id") Long id) {

        System.out.println(id);
        System.out.println(repo.getOne(id));
        return repo.getOne(id);
    }

    //5
    //4 изменение -1 спринг должен привезти из бд Т обдж, -2 спринг должен собрать объект Т и присвоить ид
    @PutMapping("{id}")
    public T update(@PathVariable(value = "id") Long id, @RequestBody T obj) {
        T one = repo.getOne(id);
        // BeanUtils.copyProperties(obj, one, "id");//копирует проперти из одного в другй
        return repo.save(obj);
    }


    //6PUT
    @ApiOperation("Update entity")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyProduct(@RequestBody T obj) {
        ResponseEntity<T> tResponseEntity;
        if (obj.getClass() == Product.class) {
            Product p = (Product) obj;
            if (repo.existsById(p.getId())) {
                tResponseEntity = new ResponseEntity<>(repo.save(obj), HttpStatus.OK);
                log.info("update " + obj.toString());
            } else {
                tResponseEntity = new ResponseEntity<>(obj, HttpStatus.NOT_FOUND);
                log.info("not product " + obj.toString());
            }
        } else {
            tResponseEntity = new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
            log.info("not product " + obj.toString());
        }
        return tResponseEntity;
    }

    //7POST
    @ApiOperation("Create entity")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> add(@RequestBody T obj) {
        ResponseEntity<T> tResponseEntity;
        if (obj.getClass() == Product.class) {
            Product p = (Product) obj;

            if (!repo.existsById(p.getId())) {
                tResponseEntity = new ResponseEntity<>(repo.save(obj), HttpStatus.OK);
                log.info("add " + obj.toString());
            } else {
                tResponseEntity = new ResponseEntity<>(obj, HttpStatus.ALREADY_REPORTED);
                log.info("exists product " + obj.toString());
            }
        } else {
            tResponseEntity = new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
            log.info("not product " + obj.toString());
        }
        return tResponseEntity;
    }

    //8DELETE
    @ApiOperation("Delete")
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        log.info("Delete id " + id);
        repo.deleteById(id);
    }

    //9DELETE
    @ApiOperation("Delete all")
    @DeleteMapping
    public void deleteAll() {
        log.info("Delete all");
        repo.deleteAll();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> jsonException(InvalidDataAccessApiUsageException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NO_CONTENT);
    }
}
