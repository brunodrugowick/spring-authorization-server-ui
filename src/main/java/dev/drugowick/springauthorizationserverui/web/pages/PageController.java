package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.entity.Client;
import dev.drugowick.springauthorizationserverui.domain.entity.User;
import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import dev.drugowick.springauthorizationserverui.domain.service.MyClientDetailsService;
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
public class PageController extends BasePageController {

    // TODO Separate into Users and Clients controllers

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final MyClientDetailsService clientDetailsService;

    @RequestMapping(path = "/")
    public String index(Model model) {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());

        return "index";
    }

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

    @RequestMapping(path = "/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());

        return "user-form";
    }

    @PostMapping(path = "/users/new")
    public String saveNewUser(@ModelAttribute("user") @Valid PageController.UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user-form";

        User user = new User();
        user.setEnabled(userDto.isEnabled());
        user.setRoles(userDto.getRoles());
        user.setEmail(userDto.getEmail());
        user.setPassword("password");

        userRepository.save(user);

        return "redirect:/";
    }

    @Setter @Getter @NoArgsConstructor
    private class UserDto {
        private Long id;
        @Email @NotBlank private String email;
        @NotBlank private String roles;
        private boolean enabled;
    }


    ////////////////////////////// CLIENTS ////////////////////////////////

    @RequestMapping(path = "/clients/{id}")
    public String getClient(Model model, @PathVariable Long id) throws Exception {
        model.addAttribute("client", clientRepository.findById(id).orElseThrow(
                () -> new Exception("Could not find client " + id)));

        return "client-form";
    }

    @PostMapping(path = "/clients/{id}")
    public String editClient(@PathVariable Long id, Client client) {
        Optional<Client> optionalSavedClient = clientRepository.findById(id);
        optionalSavedClient.ifPresent(savedClient -> {
            savedClient.setClientSecret(client.getClientSecret());
            savedClient.setGrantTypes(client.getGrantTypes());
            savedClient.setScopes(client.getScopes());
            clientRepository.save(savedClient);
        });

        return "redirect:/";
    }

    @RequestMapping(path = "/clients/new")
    public String newClient(Model model) {
        model.addAttribute("client", new ClientDto());

        return "client-form";
    }

    @PostMapping(path = "/clients/new")
    public String saveNewClient(@ModelAttribute("client") @Valid PageController.ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "client-form";

        Client client = new Client();
        client.setClientId(clientDto.getClientId());
        client.setClientSecret(clientDto.getClientSecret());
        client.setGrantTypes(clientDto.getGrantTypes());
        client.setScopes(clientDto.getScopes());

        clientRepository.save(client);

        return "redirect:/";
    }

    @Setter @Getter @NoArgsConstructor
    private class ClientDto {
        private Long id;
        @NotBlank private String clientId;
        @NotBlank private String clientSecret;
        @NotBlank private String grantTypes;
        @NotBlank private String scopes;
    }


}
