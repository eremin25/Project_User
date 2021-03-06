package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    //Информация о всех пользователях из базы данных.
    @GetMapping
    public String infoAllUsers(@ModelAttribute("newUser") User user, Model model, Principal principal) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("currentUser", userService.findUserByLogin(principal.getName()));
        model.addAttribute("allRoles", roleService.allRoles());
        return "admin/pageAdmin";
    }

    //Получение данных из формы и добавление пользователя в базу данных.
    @PostMapping("/new")
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam("allRoles") Set<String> roles,
                             @RequestParam("password") String password) {
        user.setRoles(roleService.convertingSetOfStringsToSetOfRoles(roles));
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //Удаление пользователя из базы данных.
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    //Получение данных из формы и добавление измененного пользователя в базу данных.
    @PutMapping("/{id}")
    public String saveUpdatedUser(@ModelAttribute("user") User user,
                                  @RequestParam("allRoles") Set<String> roles,
                                  @RequestParam("password") String password) {
        user.setRoles(roleService.convertingSetOfStringsToSetOfRoles(roles));
        if (!userService.findUserByLogin(user.getLogin()).getPassword().equals(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
