package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private RoleService rs;

    @GetMapping(value = "/admin")
    public String startPageForAdmin() {
        return "admin";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal) {
        User user = us.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin/getUsers")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", us.getAll());
        return "users";
    }

    @GetMapping("/admin/addUser")
    public String showCreateUserForm(Model model) {
        List<Role> roles = rs.getAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("/admin/saveUser")
    public String addUser(@ModelAttribute User user) {
        us.add(user);
        return "redirect:/admin/";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        us.delete(id);
        return "redirect:/admin/getUsers";
    }


    @PostMapping("/admin/editUser")
    public String showEditUserForm(@RequestParam Long id, Model model) {
        User user = us.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUserInfo(@ModelAttribute User user) {
        us.update(user);
        return "redirect:/admin/getUsers";
    }
}
