package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.User;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * [omschrijving van code]
 */

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    protected String showUserOverview(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "userOverview";
    }

    @GetMapping("/users/new")
    protected String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("users/new")
    protected String saveOrUpdateUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()){
            return "userForm";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{userId}")
    protected String showUserForm(@PathVariable("userId") Long userId, Model model) {
        Optional<User> user = userService.findByUserId(userId);
        model.addAttribute("user", user.get());
        return "userForm";
    }

    @GetMapping("/user/details/{userId}")
    protected String showUserDetails(@PathVariable("userId") Long userId, Model model) {
        Optional<User> user = userService.findByUserId(userId);
        model.addAttribute("user", user.get());
        return "userDetails";
    }



    @GetMapping("/users/delete/{userId}")
    protected String deleteUser(@PathVariable("userId") Long userId) {
        Optional<User> user = userService.findByUserId(userId);
        userService.delete(user.get());
        return "redirect:/user";
    }
}
