package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.entity.User;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsersPageController extends BasePageController {

    private final UserRepository userRepository;

    @RequestMapping(path = "/users/{id}")
    public String getUser(Model model, @PathVariable Long id) throws Exception {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(
                () -> new Exception("Could not find user " + id)));

        return "user-form";
    }

    @PostMapping(path = "/users/{id}")
    public String editUser(@PathVariable Long id, User user) {
        Optional<User> optionalSavedUser = userRepository.findById(id);
        optionalSavedUser.ifPresent(savedUser -> {
            savedUser.setRoles(user.getRoles());
            savedUser.setEnabled(user.isEnabled());
            userRepository.save(savedUser);
        });

        return "redirect:/";
    }

    @PostMapping(path = "/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());

        return "user-form";
    }

    @PostMapping(path = "/users/new")
    public String saveNewUser(@ModelAttribute("user") @Valid UsersPageController.UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user-form";

        User user = new User();
        user.setEnabled(userDto.isEnabled());
        user.setRoles(userDto.getRoles());
        user.setEmail(userDto.getEmail());
        user.setPassword("password");

        userRepository.save(user);

        return "redirect:/";
    }

    @Setter
    @Getter
    @NoArgsConstructor
    private class UserDto {
        private Long id;
        @Email
        @NotBlank
        private String email;
        @NotBlank private String roles;
        private boolean enabled;
    }
}
