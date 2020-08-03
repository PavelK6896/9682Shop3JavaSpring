package app.web.pavelk.shop3.controller.user;


import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.product.Product;
import app.web.pavelk.shop3.entity.user.Role;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.repo.RolesRepository;
import app.web.pavelk.shop3.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private UsersService userService;


    @Autowired
    public ProfileController(UsersService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String profileDto(Model model, Principal principal) {
        model.addAttribute("profile", userService.findByUsernameDto(principal.getName()));
        return "page/profile";
    }

    @Secured("ROLE_ADMIN")//уровень методов защта
    @GetMapping("/admin")
    public String adminProfile(Model model) {
        model.addAttribute("profileAll", userService.findAll());
        model.addAttribute("role", userService.findAllRole());


        return "page/admin/add_profile";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute User user,
                                 @RequestParam(required = false) List<Long> listIdLongRole) {
        if (listIdLongRole != null) {
            Iterable<Role> roles = userService.findAllById(listIdLongRole);
            Iterator<Role> iterator1 = roles.iterator();
            Collection<Role> target = new ArrayList<>();
            iterator1.forEachRemaining(target::add);
            user.setRoles(target);
        }

        if(user.getPhone() == null || user.getPassword() == null){
            return "redirect:/profile/admin";
        }

        userService.saveUser(user);
        return "redirect:/profile/admin";
    }

    @PostMapping("/delete")
    public String saveNewProduct(@RequestParam("phone") String phone, Principal principal) {
        if (principal.getName().equals(phone)){
            System.out.println(phone);
            return "redirect:/profile/admin";
        }
        userService.deleteByPhone(phone);
        return "redirect:/profile/admin";
    }



}
