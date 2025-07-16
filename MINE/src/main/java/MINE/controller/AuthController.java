package MINE.controller;

import MINE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password) {
        userService.signUp(username, password);
        return "redirect:/login?signupSuccess";
    }
}
