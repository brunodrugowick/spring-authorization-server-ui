package dev.drugowick.springauthorizationserverui.web.socket;

import dev.drugowick.springauthorizationserverui.domain.service.auth.UsernameFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class HttpRequestLoggerHelper {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UsernameFacade user;

    public void log(HttpServletRequest request) {
        var httpRequest = (HttpServletRequest) request;
        var uri = httpRequest.getRequestURI();
        var method = httpRequest.getMethod();
        var auth = httpRequest.getHeader("Authorization");

        String logMessage = method + " " + uri;
        LogMessageModel messageToSend = new LogMessageModel(OffsetDateTime.now(), user.getUsername(), logMessage);
        messageToSend.getDetails().put("Authorization", auth);

        log.info("Logging '{}' to websocket client at /log", logMessage);
        simpMessagingTemplate.convertAndSend("/topic/log", messageToSend);
    }

    @Data
    static class LogMessageModel {
        private final OffsetDateTime dateTime;
        private final String username;
        private final String message;
        private Map<String, Object> details = new HashMap<>();
    }
}
