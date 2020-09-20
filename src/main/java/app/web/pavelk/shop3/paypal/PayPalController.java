package app.web.pavelk.shop3.paypal;

import app.web.pavelk.shop3.entity.order.Order;
import app.web.pavelk.shop3.service.OrderService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/paypal")
public class PayPalController {
    private String clientId = "";
    private String clientSecret = "";
    private String mode = "sandbox";//тест или лайв

    private APIContext apiContext = new APIContext(clientId, clientSecret, mode);//конект

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/buy/{orderId}")//перенаправление
    public String buy(Model model, @PathVariable(name = "orderId") Long orderId, Principal principal) {
        try {
            Order order = orderService.findById(orderId).get();//достать заказ из базы

            //оплата на пайпал
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
            RedirectUrls redirectUrls = new RedirectUrls();
            //ответ от пай пал колбек
            redirectUrls.setCancelUrl("http://localhost:8189/market/paypal/cancel");//отменять
            redirectUrls.setReturnUrl("http://localhost:8189/market/paypal/success/" + order.getId());//успех
            //--

            //информация о платеже
            Amount amount = new Amount();
            amount.setCurrency("RUB");
            amount.setTotal(order.getPrice().toString());
            //--

            //описание
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription("Покупка в Market");
            //--

            //несколько транзакци
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            //оплата
            Payment payment = new Payment();
            payment.setPayer(payer);//покупатель
            payment.setRedirectUrls(redirectUrls);//колбек
            payment.setTransactions(transactions);
            payment.setIntent("sale");//продаем
            //--

            //запрос
            Payment doPayment = payment.create(apiContext);

            Iterator<Links> links = doPayment.getLinks().iterator();

            while (links.hasNext()) { //читаем ответ
                Links link = links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {//если есть линк в ответе
                    return "redirect:" + link.getHref();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        model.addAttribute("message", "Вы сюда не должны были попасть...");
        return "page/paypal-result";
    }

    @GetMapping("/success/{orderId}")
    public String success(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable(name = "orderId") Long orderId) {
        try {

            String paymentId = request.getParameter("paymentId");//информация о платеже
            String payerId = request.getParameter("PayerID");//о пакупатели


            if (paymentId == null || paymentId.isEmpty() || payerId == null || payerId.isEmpty()) {
                return "redirect:/";
            }

            Payment payment = new Payment();
            payment.setId(paymentId);

            //Исполнение
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);

            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            if (executedPayment.getState().equals("approved")) {//оплата прошла
                model.addAttribute("message", "Ваш заказ #" + orderId + " сформирован и оплачен");
            } else {
                //ждем дальше
                model.addAttribute("message", "Что-то пошло не так при формировании заказа, попробуйте повторить операцию");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page/paypal-result";
    }

    @GetMapping("/cancel")//если отмена
    public String cancel(Model model) {
        model.addAttribute("message", "Оплата заказа была отменена");

        return "page/paypal-result";
    }
}
