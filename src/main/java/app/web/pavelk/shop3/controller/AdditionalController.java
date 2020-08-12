package app.web.pavelk.shop3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdditionalController {
    @GetMapping("/about")
    public String aboutPage() {
        return "page/about";
    }

    @GetMapping("/")
    public String homepage() {
        return "page/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "page/login_page";
    }

    @GetMapping("/api/v1/authorization")
    @ResponseBody
    public boolean authorization() {
        return true;
    }
    
}