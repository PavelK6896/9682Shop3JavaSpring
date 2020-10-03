package app.web.pavelk.shop3.controller.order;


import app.web.pavelk.shop3.beans.Cart;
import app.web.pavelk.shop3.entity.order.Order;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.entity.user.dto.SystemUser;
import app.web.pavelk.shop3.service.OrderService;
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
    private OrderService orderService;
    private Cart cart;

    @GetMapping("/create")
    public String createOrder(Principal principal, Model model) {
//        @RequestParam(name = "phone") String phone,
//        @RequestParam(name = "firstName") String firstName,
//        @RequestParam(name = "address") String address

        String phone = "1";
        String firstName = "1";
        String address = "1";
        User user = null;
        if (principal != null) {
            user = usersService.findByPhone(principal.getName());
        } else {
            if (usersService.isUserExist(phone)) {
                user = usersService.findByPhone(phone);
            } else {
                SystemUser systemUser = new SystemUser();
                systemUser.setPhone(phone);
                systemUser.setFirstName(firstName);
              //  user = usersService.save(systemUser);
            }
        }
        //создаеться заказ
        Order order = orderService.createOrder(user, phone, address);

        //редирект на оплату
        return "redirect:/paypal/buy/" + order.getId();
    }

    @PostMapping("/confirm")//оформить
    @ResponseBody
    public String confirmOrder(Principal principal,
                               @RequestParam String address,
                               @RequestParam String phone) {
        User user = usersService.findByPhone(principal.getName());
        Order order = new Order(user, cart, phone, address);
        order = orderService.saveOrder(order);
        return order.getId() + " " + order.getPrice();
    }
}
