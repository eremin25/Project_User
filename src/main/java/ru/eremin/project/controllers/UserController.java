package ru.eremin.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.eremin.project.services.UserService;

import java.security.Principal;

//Стартовая страница пользователя.
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    //Информация текущего авторизованного пользователя.
    @GetMapping
    public String infoUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByLogin(principal.getName()));
        return "user/info";
    }

}
