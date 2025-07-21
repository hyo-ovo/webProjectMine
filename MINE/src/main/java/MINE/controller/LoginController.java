package MINE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "signupSuccess", required = false) String signupSuccess,
                        Model model) {
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", true);
        }
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login"; // templates/login.html
    }
}
