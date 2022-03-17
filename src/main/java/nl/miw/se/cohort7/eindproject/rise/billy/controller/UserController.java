package nl.miw.se.cohort7.eindproject.rise.billy.controller;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.*;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    @GetMapping("/update/balance/{billyUserId}")
    @Secured({"ROLE_BAR MANAGER", "ROLE_BARTENDER"})
    protected String updateUserBalance(@PathVariable("billyUserId") Long userId, Model model) {
        BillyUserDto billyuserDto = userService.findByUserId(userId);
        if (billyuserDto == null) {
            return "redirect:/users";
        }
        AddCreditDto addCredit = new AddCreditDto();
        model.addAttribute("billyuserDto", billyuserDto);
        model.addAttribute("addCredit", addCredit);
        return "adjustCreditForm";
    }

    @PostMapping("/update/balance/{billyUserId}")
    protected String updateUserBalance(@Valid @ModelAttribute("addCredit") AddCreditDto addCredit, BindingResult result,
    @PathVariable("billyUserId") Long userId){
        if (result.hasErrors()) {
            return "adjustCreditForm";
        }
        BillyUserDto billyUserDto = userService.findByUserId(userId);
            if (billyUserDto != null){
                billyUserDto.calculateNewCredit(addCredit.getValue());
                userService.updateUser(billyUserDto);
                System.out.println(addCredit);
            }
        return "redirect:/users";
    }

    @GetMapping("/new")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserForm(Model model) {
        model.addAttribute("billyUserDto", new BillyUserDto());
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

        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(billyUserId);

        model.addAttribute("passwordDto", passwordDto);

        return "changePasswordForm";
    }

    @PostMapping("/changePassword/{billyUserId}")
    protected String changePassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "changePasswordForm";
        }
        userService.updatePassword(passwordDto);
        return "redirect:/users";
    }

    @GetMapping("/details/{billyUserId}/orderHistory")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String seeOrderHistory(@PathVariable("billyUserId") Long billyUserId, Model model) {
        List <BarOrderViewDto> barOrderViewDtoList = barOrderService.findAllBarOrderOfUser(billyUserId);
        if (barOrderViewDtoList.isEmpty()) {
            model.addAttribute("userDto", userService.findByUserId(billyUserId));
            return "noOrderHistory";
        } else {
            Collections.sort(barOrderViewDtoList);
            model.addAttribute("allBillyUsers", userService.findAll());
            model.addAttribute("OrdersByUser", barOrderViewDtoList);
            return "userOrderHistory";
        }
    }
}