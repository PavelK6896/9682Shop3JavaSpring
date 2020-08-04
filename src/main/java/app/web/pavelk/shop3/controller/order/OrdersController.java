package app.web.pavelk.shop3.controller.order;


import app.web.pavelk.shop3.beans.Cart;
import app.web.pavelk.shop3.entity.order.Order;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.service.OrdersService;
import app.web.pavelk.shop3.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private UsersService usersService;
    private OrdersService ordersService;
    private Cart cart;

    @GetMapping("/create")
    public String createOrder(Principal principal, Model model) {
        User user = usersService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        return "page/user/order_info";
    }

    @PostMapping("/confirm")//оформить
    @ResponseBody
    public String confirmOrder(Principal principal,
                               @RequestParam String address,
                               @RequestParam String phone) {
        User user = usersService.findByPhone(principal.getName());
        Order order = new Order(user, cart, phone, address);
        order = ordersService.saveOrder(order);
        return order.getId() + " " + order.getPrice();
    }
}
