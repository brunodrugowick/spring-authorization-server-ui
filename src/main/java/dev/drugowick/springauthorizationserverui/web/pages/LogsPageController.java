package dev.drugowick.springauthorizationserverui.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogsPageController {

    @GetMapping(path = "/logs")
    public String logsPage() {
        return "log";
    }

}
