package dev.drugowick.springauthorizationserverui.web.pages;

import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController extends BasePageController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @RequestMapping(path = "/")
    public String index(Model model) {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());

        return "index";
    }

}
