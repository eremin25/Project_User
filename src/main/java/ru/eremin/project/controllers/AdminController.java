package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.eremin.project.models.User;
import ru.eremin.project.services.RoleService;
import ru.eremin.project.services.UserService;

import java.util.Set;

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

    //Информация о всех пользователях из базы данных
    @GetMapping
    public String infoAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/infoAllUsers";
    }

    //Форма создания нового пользователя
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.allRoles());
        return "admin/newUser";
    }

    //Получение данных из формы и добавление пользователя в базу данных
    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("allRoles") Set<String> roles) {
        user.setRoles(roleService.convertingSetOfStringsToSetOfRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //Удаление пользователя из базы данных
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
