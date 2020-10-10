package dev.drugowick.springauthorizationserverui.infrastructure;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserLoggerHelper {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AuthenticationFacade user;

    public void log(String message) {
        log.info("Logging '{}' to websocket client at /log", message);
        LogMessageModel messageToSend = new LogMessageModel(OffsetDateTime.now(), user.getAuthentication().getName(), message);
        simpMessagingTemplate.convertAndSend("/topic/log", messageToSend);
    }

    @Data
    static class LogMessageModel {
        private final OffsetDateTime dateTime;
        private final String username;
        private final String message;
    }
}
