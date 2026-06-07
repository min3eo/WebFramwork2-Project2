package kr.ac.hansung.controller;

import kr.ac.hansung.dto.PasswordChangeDto;
import kr.ac.hansung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/password")
    public String passwordForm(Model model) {
        model.addAttribute("passwordChange", new PasswordChangeDto());
        return "user/password";
    }

    @PostMapping("/password")
    public String changePassword(@ModelAttribute("passwordChange") PasswordChangeDto dto,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        try {
            userService.changePassword(authentication.getName(), dto);
            redirectAttributes.addFlashAttribute("passwordChanged", true);
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/password";
        }
    }
}
