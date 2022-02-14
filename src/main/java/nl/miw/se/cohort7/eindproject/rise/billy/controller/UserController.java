package nl.miw.se.cohort7.eindproject.rise.billy.controller;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Manages all user-related interactions
 */

@Controller
@RequestMapping("/users")
@Secured("ROLE_BAR MANAGER")
public class UserController {

    private UserService userService;
    PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserOverview(Model model) {
        model.addAttribute("allBillyUsers", userService.findAll());
        return "userOverview";
    }

    @GetMapping("/new")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserForm(Model model) {
        model.addAttribute("billyUser", new BillyUser());
        model.addAttribute("headerText", "New User");
        return "userForm";
    }

    @PostMapping("/new")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String saveOrUpdateUser(@Valid @ModelAttribute("billyUser") BillyUser billyUser, BindingResult result) {
        if (result.hasErrors()) {
            return "userForm";
        }
        if (!userService.mayWriteToDB(billyUser)){
            result.rejectValue("username", "error.user", "please insert a unique username");
            return "userForm";
        }
        if (billyUser.getUserRole() == null) {
            billyUser.setUserRole("ROLE_CUSTOMER");
        }
        if (billyUser.getUserRole().equals("ROLE_CUSTOMER")) {
            billyUser.setRandomPassword();
        }
        if (billyUser.getPassword().length() < BillyUser.MINIMUM_PASSWORD_LENGTH) {
            result.rejectValue("password", "error.user"
                    , "Please fill out a password with a minimum of 8 characters.");
            return "userForm";
        }
        if (billyUser.getBirthdate().after(Date.from(Instant.now()))) {
            result.rejectValue("birthdate", "error.birthdate"
                    , "Please fill out a date in the past");
            return "userForm";
        }
        billyUser.setPassword(passwordEncoder.encode(billyUser.getPassword()));
        userService.save(billyUser);
        return "redirect:/users";
    }

    @GetMapping("/update/{billyUserId}")
    protected String showUserForm(@PathVariable("billyUserId") Long userId, Model model) {
        Optional<BillyUser> billyUser = userService.findByUserId(userId);
        model.addAttribute("billyUser", billyUser.get());
        model.addAttribute("headerText", "Edit");
        return "userForm";
    }

    @GetMapping("/details/{billyUserId}")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserDetails(@PathVariable("billyUserId") Long BillyUserId, Model model) {
        Optional<BillyUser> billyUser = userService.findByUserId(BillyUserId);
        model.addAttribute("billyUser", billyUser.get());
        return "userDetails";
    }

    @GetMapping("/delete/{billyUserId}")
    protected String deleteUser(@PathVariable("billyUserId") Long billyUserId) {
        Optional<BillyUser> billyUser = userService.findByUserId(billyUserId);
        userService.delete(billyUser.get());
        return "redirect:/users";
    }

}
