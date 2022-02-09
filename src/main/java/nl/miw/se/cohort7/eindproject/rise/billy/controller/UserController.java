package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.model.User;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Manages all user-related interactions
 */

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String showUserOverview(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "userOverview";
    }

    @GetMapping("/new")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/new")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String saveOrUpdateUser(@Valid @ModelAttribute("newUser") User user, BindingResult result) {
        if (result.hasErrors()){
            return "userForm";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/update/{userId}")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String showUserForm(@PathVariable("userId") Long userId, Model model) {
        Optional<User> user = userService.findByUserId(userId);
        model.addAttribute("user", user.get());
        return "userForm";
    }

    @GetMapping("/details/{userId}")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String showUserDetails(@PathVariable("userId") Long userId, Model model) {
        Optional<User> user = userService.findByUserId(userId);
        model.addAttribute("user", user.get());
        return "userDetails";
    }

    @GetMapping("/delete/{userId}")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String deleteUser(@PathVariable("userId") Long userId) {
        Optional<User> user = userService.findByUserId(userId);
        userService.delete(user.get());
        return "redirect:/users";
    }
}
