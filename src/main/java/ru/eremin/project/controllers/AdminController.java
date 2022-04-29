package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.eremin.project.models.User;
import ru.eremin.project.services.RoleService;
import ru.eremin.project.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String infoAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/infoAllUsers";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.allRoles());
        return "admin/newUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") String[] roles) {
        for (String role : roles) {
            user.getRoles().add(roleService.findRoleByName(role));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
