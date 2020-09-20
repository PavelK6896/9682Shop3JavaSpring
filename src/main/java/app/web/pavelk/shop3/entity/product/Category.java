package app.web.pavelk.shop3.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "title"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(Views.IdTitle.class)
    private Long id;

    @Column(name = "title")
    @JsonView(Views.IdTitle.class)
    private String title;

    @ManyToMany
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonBackReference
    private List<Product> products;
    //@JsonManagedReference is the forward part of reference – the one that gets serialized normally.
    // @JsonBackReference is the back part of reference – it will be omitted from serialization.

    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }


}
