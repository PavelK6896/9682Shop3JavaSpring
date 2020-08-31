package app.web.pavelk.shop3.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdditionalController {
    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @GetMapping("/about")
    public String aboutPage() {
        return "page/about";
    }

    @GetMapping("/")
    public String homepage() {
        System.out.println("activeProfile == " + activeProfile);
        if (activeProfile.equals("jwt") ) {
            return "../static/index";
        }else if(activeProfile.equals("jsBasic")){
            return "../static/indexBasic";
        }
        return "page/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "page/login_page";
    }

}
