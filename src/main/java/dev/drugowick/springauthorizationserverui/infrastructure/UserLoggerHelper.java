package dev.drugowick.springauthorizationserverui.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserLoggerHelper {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void log(String message) {
        log.info("Logging '{}' to websocket client at /log", message);
        String messageToSend = LocalDateTime.now().toString() + ": " + message;
        simpMessagingTemplate.convertAndSend("/topic/log", messageToSend);
    }
}
