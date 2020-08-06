package app.web.pavelk.shop3.controller.rest;

import app.web.pavelk.shop3.exceptions.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public abstract class AbstractRestController<T, R extends JpaRepository<T, Long>> {
    protected R repo;

    //инжектим репозитой в клас
    public AbstractRestController(R repo) {
        this.repo = repo;
    }

    //1
    @GetMapping//1 все сущьности //формируем страницу поумолчанию
    public Page<T> getAllPage(@PageableDefault Pageable pageable) {
        return repo.findAll(pageable);
    }

    //2
    @GetMapping(value = "/list", produces = "application/json")
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


    //6
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing product")
    public ResponseEntity<?> modifyProduct(@RequestBody T obj) {
        return new ResponseEntity<>(repo.save(obj), HttpStatus.OK);
    }

    //7
    //3 добавление //получаем данные в теле собирает объект Т //присваиваеться идентификатор
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new product")
    public T add(@RequestBody T obj) {
        return repo.save(obj);
    }

    //8
    @DeleteMapping(value = "{id}")//5
    @ApiOperation("Remove")
    public void delete(@PathVariable(value = "id") Long id) {
        repo.deleteById(id);
    }

    //9
    @DeleteMapping
    @ApiOperation("Removes all products")
    public void deleteAllProducts() {
        System.out.println("DeleteMapping");
       // repo.deleteAll();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
