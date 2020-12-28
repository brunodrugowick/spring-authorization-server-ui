package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A test controller for Stimulus on the front-end.
 *
 * I'm experimenting...
 */
@Controller
@RequiredArgsConstructor
public class StimulusHomeController extends BasePageController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @RequestMapping(path = "/stimulus/header")
    public String getAppHeader() {
        return "fragments/header";
    }

    @RequestMapping(path = "/stimulus/users")
    public String getUsersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "stimulus/users-list";
    }

    @RequestMapping(path = "/stimulus/clients")
    public String getClientsList(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "stimulus/clients-list";
    }
}
