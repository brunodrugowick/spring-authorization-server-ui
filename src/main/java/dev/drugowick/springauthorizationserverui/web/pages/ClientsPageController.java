package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.entity.Client;
import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ClientsPageController extends BasePageController {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

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
//            savedClient.setClientSecret(client.getClientSecret());
            savedClient.setGrantTypes(client.getGrantTypes());
            savedClient.setScopes(client.getScopes());
            clientRepository.save(savedClient);
        });

        return "redirect:/";
    }

    @PostMapping(path = "/clients/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/clients/new")
    public String newClient(Model model) {
        model.addAttribute("client", new ClientDto());

        return "client-form";
    }

    @PostMapping(path = "/clients/new")
    public String saveNewClient(@ModelAttribute("client") @Valid ClientsPageController.ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "client-form";

        Client client = new Client();
        client.setClientId(clientDto.getClientId());
//        client.setClientSecret(passwordEncoder.encode(clientDto.getClientSecret()));
        client.setClientSecret(passwordEncoder.encode("password"));
        client.setGrantTypes(clientDto.getGrantTypes());
        client.setScopes(clientDto.getScopes());

        clientRepository.save(client);

        return "redirect:/";
    }

    @Setter
    @Getter
    @NoArgsConstructor
    private class ClientDto {
        private Long id;
        @NotBlank
        private String clientId;
        private String clientSecret;
        @NotBlank private String grantTypes;
        @NotBlank private String scopes;
    }

}
