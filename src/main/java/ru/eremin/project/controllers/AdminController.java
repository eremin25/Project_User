package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.eremin.project.models.User;
import ru.eremin.project.services.RoleService;
import ru.eremin.project.services.UserService;

import java.security.Principal;
import java.util.Set;

//Стартовая страница администратора.
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

    //Информация о всех пользователях из базы данных.
    @GetMapping
    public String infoAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", userService.findUserByLogin(principal.getName()));
        return "admin/pageAdmin";
    }

    //Форма создания нового пользователя.
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.allRoles());
        return "admin/newUser";
    }

    //Получение данных из формы и добавление пользователя в базу данных.
    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("allRoles") Set<String> roles) {
        user.setRoles(roleService.convertingSetOfStringsToSetOfRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //Удаление пользователя из базы данных.
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    //Форма изменения пользователя.
    @GetMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, Model model) {
        model.addAttribute(userService.findUserById(id));
        model.addAttribute("allRoles", roleService.allRoles());
        return "admin/updateUser";
    }


    //Получение данных из формы и добавление измененного пользователя в базу данных.
    @PatchMapping("/{id}")
    public String saveUpdatedUser(@ModelAttribute("user") User user, @RequestParam("allRoles") Set<String> roles) {
        user.setRoles(roleService.convertingSetOfStringsToSetOfRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
