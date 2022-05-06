package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.eremin.project.models.Role;
import ru.eremin.project.models.User;
import ru.eremin.project.services.RoleService;
import ru.eremin.project.services.UserService;

import java.util.Set;

@Controller
public class AuthorizationController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AuthorizationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("/registration/new")
    public String saveUser(@ModelAttribute("user") User user) {
        Set<Role> role = user.getRoles();
        role.add(roleService.findRoleByName("USER"));
        user.setRoles(role);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
