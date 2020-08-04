package app.web.pavelk.shop3.service;


import app.web.pavelk.shop3.entity.order.Order;
import app.web.pavelk.shop3.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    private OrdersRepository ordersRepository;

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order saveOrder(Order order) {
        return ordersRepository.save(order);
    }
}
