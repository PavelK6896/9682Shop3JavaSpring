package app.web.pavelk.shop3.controller.user;


import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.entity.user.dto.SystemUser;
import app.web.pavelk.shop3.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private UsersService usersService;

    @Autowired
    public void setUserService(UsersService usersService) {
        this.usersService = usersService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "page/user/registration-form";
    }

    @PostMapping("/register/process")
    public String processRegistrationForm(
            @ModelAttribute("systemUser") @Validated SystemUser systemUser,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "page/user/registration-form";
        }

        User existing = usersService.findByPhone(systemUser.getPhone());

        if (existing != null) {
            model.addAttribute("registrationError", "User with phone number: ["
                    + systemUser.getPhone() + "] is already exist это уже существует");

            systemUser.setPhone(null);

            model.addAttribute("systemUser", systemUser);

            return "page/user/registration-form";
        }

        usersService.saveRegisterUser(systemUser);

        return "page/user/registration-confirmation";
    }
}
