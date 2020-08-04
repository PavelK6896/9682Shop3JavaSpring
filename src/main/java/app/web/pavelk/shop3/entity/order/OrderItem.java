package app.web.pavelk.shop3.entity.order;

import app.web.pavelk.shop3.entity.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders_items")
@Data
public class OrderItem { //обертка для продуктов
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = new BigDecimal(0).add(product.getPrice());
    }

    public void increment() {
        this.quantity++;
        this.price = new BigDecimal(quantity * product.getPrice().doubleValue());
    }

    public void decrement() {
        this.quantity--;
        this.price = new BigDecimal(quantity * product.getPrice().doubleValue());
    }

    public boolean isEmpty() {
        return quantity == 0;
    }
}
