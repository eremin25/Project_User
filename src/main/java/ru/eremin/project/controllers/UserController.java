package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.eremin.project.services.RoleService;
import ru.eremin.project.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String infoUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByLogin(principal.getName()));
        return "user/info";
    }

}
