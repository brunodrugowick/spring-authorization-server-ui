package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import dev.drugowick.springauthorizationserverui.web.pages.dto.PasswordChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeController extends BasePageController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("passwordChange")
    public PasswordChange passwordChangeDto() {
        return new PasswordChange();
    }

    @GetMapping("/profile/password")
    public String passwordChange(Principal principal) {
        log.info("User {} is trying to change its password", principal.getName());
        return "password-change-user";
    }

    @GetMapping("/clients/{id}/secret")
    public String passwordChange(@PathVariable Long id) {
        log.info("Requested password change for client {}", id);
        return "password-change-client";
    }

    @PostMapping("/profile/password")
    public String passwordChangePost(@ModelAttribute("passwordChange") @Valid PasswordChange passwordChange,
                                     BindingResult bindingResult,
                                     Principal principal) {
        var userOptional= userRepository.findFirstByEmail(principal.getName());
        if (userOptional.isPresent()) {
            var user= userOptional.get();
            if (!passwordEncoder.matches(passwordChange.getCurrentPassword(), user.getPassword()))
                bindingResult.addError(new ObjectError("currentPassword", "Current password doesn't match"));
            if (bindingResult.hasErrors())
                return "password-change-user";
            String newPassword = passwordChange.getNewPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            log.info("User {} changed its password", user.getEmail());
        }

        return "redirect:/";
    }

    @PostMapping("/clients/{id}/secret")
    public String clientPasswordChangePost(@PathVariable Long id,
                                           @ModelAttribute("passwordChange") @Valid PasswordChange passwordChange,
                                           BindingResult bindingResult,
                                           Principal principal) {
        var clientOptional= clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            var client= clientOptional.get();
            if (!passwordEncoder.matches(passwordChange.getCurrentPassword(), client.getClientSecret()))
                bindingResult.addError(new ObjectError("currentPassword", "Current password doesn't match"));
            if (bindingResult.hasErrors())
                return "password-change-client";
            String newPassword = passwordChange.getNewPassword();
            client.setClientSecret(passwordEncoder.encode(newPassword));
            clientRepository.save(client);
        }

        return "redirect:/";
    }
}
