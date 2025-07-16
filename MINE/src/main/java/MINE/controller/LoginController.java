// src/main/java/MINE/controller/LoginController.java

package MINE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "signupSuccess", required = false) String signupSuccess,
                        Model model) {
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", true); // 템플릿에서 표시할 플래그
        }
        return "login"; // templates/login.html 반환
    }
}
