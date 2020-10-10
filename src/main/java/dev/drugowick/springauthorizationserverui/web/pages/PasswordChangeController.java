package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import dev.drugowick.springauthorizationserverui.web.pages.dto.PasswordChange;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class PasswordChangeController extends BasePageController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("passwordChange")
    public PasswordChange passwordChangeDto() {
        return new PasswordChange();
    }

    @GetMapping("/password")
    public String passwordChange() {
        return "password-change";
    }

    @PostMapping("/password")
    public String passwordChangePost(@ModelAttribute("passwordChange") @Valid PasswordChange passwordChange,
                                     BindingResult bindingResult,
                                     Principal principal) {
        var userOptional= userRepository.findFirstByEmail(principal.getName());
        if (userOptional.isPresent()) {
            var user= userOptional.get();
            if (!passwordEncoder.matches(passwordChange.getCurrentPassword(), user.getPassword()))
                bindingResult.addError(new ObjectError("currentPassword", "Current password doesn't match"));
            if (bindingResult.hasErrors())
                return "password-change";
            String newPassword = passwordChange.getNewPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }

        return "redirect:/";
    }
}
