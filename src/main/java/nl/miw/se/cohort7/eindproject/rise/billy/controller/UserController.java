package nl.miw.se.cohort7.eindproject.rise.billy.controller;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
@Secured("ROLE_BAR MANAGER")
public class UserController {

    private UserService userService;
    private BarOrderService barOrderService;


    public UserController(UserService userService, BarOrderService barOrderService) {
        this.userService = userService;
        this.barOrderService = barOrderService;
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
        model.addAttribute("billyUserDto", new BillyUserDto());
        model.addAttribute("headerText", "New User");
        return "userForm";
    }

    @PostMapping("/new")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String saveOrUpdateUser(@Valid @ModelAttribute("billyUserDto") BillyUserDto billyUserDto, BindingResult result) {
        BillyUserPrincipal principal = (BillyUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        principal.getUserId();
        if (result.hasErrors()) {
            return "userForm";
        }

        if (billyUserDto.getUserId() == null) {
            userService.saveNewUser(billyUserDto);
        } else {
            userService.updateUser(billyUserDto);
        }

        return "redirect:/users";
    }

    @GetMapping("/update/{billyUserId}")
    protected String showUserForm(@PathVariable("billyUserId") Long userId, Model model) {
        BillyUserDto billyUserDto = userService.findByUserId(userId);
        if (billyUserDto == null) {
            return "redirect:/users";
        }
        model.addAttribute("billyUserDto", billyUserDto);
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
        BillyUserPrincipal principal = (BillyUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
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
        userService.updatePassword(changePassword);
        return "redirect:/users";
    }

    @GetMapping("/details/{billyUserId}/orderHistory")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String seeOrderHistory(@PathVariable("billyUserId") Long billyUserId, Model model) {
        if (barOrderService.findAllBarOrderOfUser(billyUserId).isEmpty()) {
            return "noOrderHistory";
        } else {
            model.addAttribute("allBillyUsers", userService.findAll());
            model.addAttribute("OrdersByUser", barOrderService.findAllBarOrderOfUser(billyUserId));
            return "userOrderHistory";
        }
    }

    @GetMapping("/details/{billyUserId}/orderHistory/{orderId}")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String seeOrderHistoryDetails(@PathVariable("orderId") Long barOrderId, Model model,
                                            @PathVariable String billyUserId) {
        Optional<BarOrderViewDto> optionalBarOrderViewDto = barOrderService.findBarOrderById(barOrderId);
        if (optionalBarOrderViewDto.isPresent()) {
            model.addAttribute("barOrderDetail", optionalBarOrderViewDto.get());
            return "userOrderHistoryDetails";
        }
        return "redirect:/users";
    }
}