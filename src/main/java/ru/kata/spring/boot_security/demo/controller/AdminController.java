package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String viewUsers(Model model, Principal principal) {
        final String currentUser = principal.getName();

        User admin = userService.findByUserName(principal.getName());
        model.addAttribute("admin", admin);

        model.addAttribute("users", userService.getAllUserList());
        model.addAttribute("authUser", userService.findByUserName(currentUser));
        model.addAttribute("userRoles", roleService.getListRoles());
        model.addAttribute("userInfo", userService.findByUserName(principal.getName()));

        model.addAttribute("new_user", new User());
        model.addAttribute("edit_user", new User());
        model.addAttribute("roles", roleService.getListRoles());
        return "admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/{id}")
    public String Update(@ModelAttribute("user") User updatedUser,
                         @PathVariable("id") Long id) {
        User existingUser = userService.getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setRole(updatedUser.getRole());
        userService.update(id, updatedUser);
        return "redirect:/admin";
    }

}