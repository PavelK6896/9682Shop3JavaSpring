package app.web.pavelk.shop3.controller.order;


import app.web.pavelk.shop3.beans.Cart;
import app.web.pavelk.shop3.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private ProductsService productsService;
    private Cart cart;

    @GetMapping
    public String showCartPage() {
        return "page/cart";
    }

    @GetMapping("/add/{productId}")
    public void addProductToCartById(@PathVariable Long productId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        cart.add(productsService.findById(productId));
        response.sendRedirect(request.getHeader("referer"));
    }


    @GetMapping("/decrement/{productId}")
    public void decrementProductToCartById(@PathVariable Long productId,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {
        cart.decrement(productsService.findById(productId));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/remove/{productId}")
    public void removeProductFromCartById(@PathVariable Long productId,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        cart.removeByProductId(productId);
        response.sendRedirect(request.getHeader("referer"));
    }
}
