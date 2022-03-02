package nl.miw.se.cohort7.eindproject.rise.billy.controller;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private BarOrderService barOrderService;
    PasswordEncoder passwordEncoder;


    public UserController(UserService userService, BarOrderService barOrderService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.barOrderService = barOrderService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserOverview(Model model) {
        model.addAttribute("allBillyUsers", userService.findAll());
        return "userOverview";
    }

    @GetMapping("/balances")
    @Secured("ROLE_BAR MANAGER")
    protected String showUserBalanceOverview(Model model) {
        model.addAttribute("allBillyUsers", userService.findAll());
        return "userBalanceOverview";
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
        if (!userService.mayWriteToDB(billyUser)) {
            result.rejectValue("username", "error.user", "please insert a unique username");
            return "userForm";
        }
        if (billyUser.getUserRole() == null) {
            billyUser.setUserRole("ROLE_CUSTOMER");
        }
        if (billyUser.getUserRole().equals("ROLE_CUSTOMER") || billyUser.getPassword().equals("")) {
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
        billyUser.setAccountBalance(0.0);
        userService.save(billyUser);
        return "redirect:/users";
    }

    @GetMapping("/update/{billyUserId}")
    protected String showUserForm(@PathVariable("billyUserId") Long userId, Model model) {
        BillyUserDto billyUserDto = userService.findByUserId(userId);
        if (billyUserDto == null) {
            return "redirect:/users";
        }
        model.addAttribute("billyUser", billyUserDto);
        model.addAttribute("headerText", "Edit");
        return "userForm";
    }

    @GetMapping("/details/{billyUserId}")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserDetails(@PathVariable("billyUserId") Long BillyUserId, Model model) {
        BillyUserDto billyUserDto = userService.findByUserId(BillyUserId);
        if (billyUserDto == null) {
            return "redirect:/users";
        }
        model.addAttribute("billyUserDto", billyUserDto);
        return "userDetails";
    }

    @GetMapping("/delete/{billyUserId}")
    protected String deleteUser(@PathVariable("billyUserId") Long billyUserId) {
        BillyUserPrincipal principal = (BillyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.getUserId();
        if (billyUserId.equals(principal.getUserId())){
            return "redirect:/users/details/{billyUserId}";
        } else {
            userService.delete(billyUserId);
            return "redirect:/users";
        }
    }

    @GetMapping("/changePassword/{billyUserId}")
    protected String changePassword(@PathVariable("billyUserId") Long billyUserId, Model model) {

        ChangePassword changePassword = new ChangePassword();
        changePassword.setUserId(billyUserId);

        model.addAttribute("changePassword", changePassword);

        return "changePasswordForm";
    }

    @PostMapping("/changePassword/{billyUserId}")
    protected String changePassword(@Valid @ModelAttribute("changePassword") ChangePassword changePassword,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "changePasswordForm";
        }
        if (!changePassword.confirmNewPassword()) {
            result.rejectValue("newPasswordConfirmation", "error.newPasswordConfirmation",
                    "Passwords should match");
            return "changePasswordForm";
        }
        changePassword.setNewPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userService.updatePassword(changePassword);
        return "redirect:/users";
    }

    @GetMapping("/details/{billyUserId}/orderHistory")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String seeOrderHistory(@PathVariable("billyUserId") Long billyUserId, Model model) {
        model.addAttribute("allBillyUsers", userService.findAll());
        model.addAttribute("OrdersByUser", barOrderService.findAllBarOrderOfUser(billyUserId));
        return "userOrderHistory";
    }

    @GetMapping("/details/{billyUserId}/orderHistory/{orderId}")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String SeeOrderHistoryDetails(@PathVariable ("billyUserId") Long billyUserId,
                                            @PathVariable("orderId") Long barOrderId, Model model) {
        Optional<BarOrderViewDto> optionalBarOrderViewDto = barOrderService.findBarOrderById(barOrderId);
        if (optionalBarOrderViewDto.isPresent()) {
            model.addAttribute("barOrderDetail", optionalBarOrderViewDto.get());
            return "userOrderHistoryDetails";
        }
        return "redirect:/users";
    }

}