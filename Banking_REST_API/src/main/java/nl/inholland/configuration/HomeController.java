package nl.inholland.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to inholland controller documentation
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        System.out.println("inholland-ui.html");
        return "redirect:inholland-ui.html";
    }
}
