package MINE.controller;

import MINE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    // 회원가입 폼 GET
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";  // resources/templates/signup.html
    }

    // 회원가입 처리 POST
    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        try {
            userService.signUp(username, password);
            return "redirect:/login?signupSuccess";
        } catch (IllegalArgumentException e) {
            // 실패 시 현재 페이지에서 오류 메시지 전달
            model.addAttribute("signupError", true);
            return "signup";
        }
    }
}
