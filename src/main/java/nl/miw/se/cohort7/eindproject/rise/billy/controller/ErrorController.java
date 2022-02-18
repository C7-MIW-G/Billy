package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * [omschrijving van code]
 */

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "Error status 404 - Page not found");
                model.addAttribute("error", "It looks like you tried to access a page which" +
                        " is non-existent.");
                model.addAttribute("error2", "Please go back to one of the other screens");
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "Error status 500 - Internal server error");
                model.addAttribute("error", "Oopsie... Server died. What now?");
                model.addAttribute("error2", "You might want to go back to " +
                        "one of the other screens");
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "Error status 403 - Access denied");
                model.addAttribute("error", "You are not authorized for this feature!");
                model.addAttribute("error2", "If you feel like you should, please contact " +
                        "your bar manager.");
            } else {
                model.addAttribute("errorCode", "Error status " +
                        HttpStatus.valueOf(statusCode));
                model.addAttribute("error", "For this error code there is no custom " +
                        "error page yet!");
                model.addAttribute("error2","Please let team Billy know.");
            }
        }
        return "error";
    }
}
