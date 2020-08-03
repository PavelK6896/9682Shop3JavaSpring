package app.web.pavelk.shop3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    
}