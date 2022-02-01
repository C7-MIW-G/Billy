package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 01/02/2022
 * Dit is wat het programma doet.
 */

@Controller
public class WelcomeController {

    @GetMapping("/")
    protected String showHomepage() {
        return "homepage";
    }
}
