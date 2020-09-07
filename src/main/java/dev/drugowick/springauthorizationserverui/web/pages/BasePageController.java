package dev.drugowick.springauthorizationserverui.web.pages;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

public class BasePageController {

    @ModelAttribute("username")
    public String username(Principal principal) { return principal.getName(); }

    @ModelAttribute("logged")
    public boolean logged(Principal principal) { return principal != null; }

}
