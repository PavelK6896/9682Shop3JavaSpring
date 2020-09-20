package app.web.pavelk.shop3.service;


import app.web.pavelk.shop3.beans.Cart;
import app.web.pavelk.shop3.entity.order.Order;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order saveOrder(Order order) {
        return ordersRepository.save(order);
    }

    public java.util.Optional<Order> findById(Long id){
        return ordersRepository.findById(id);
    }

    public Order createOrder(User user, String phone, String address) {
        Order order = new Order(user,cart, phone, address);
        return ordersRepository.save(order);
    }



}
