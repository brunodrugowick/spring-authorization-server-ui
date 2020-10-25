package dev.drugowick.springauthorizationserverui.web.filter;

import dev.drugowick.springauthorizationserverui.web.socket.HttpRequestLoggerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientLoggingFilter implements Filter {

    private final HttpRequestLoggerHelper httpRequestLoggerHelper;

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest)
                || ((HttpServletRequest) request).getServletPath().contains("webjars")) {
            log.debug("Request for irrelevant content. Not invoking log helper and continuing with chain.");
        } else {
            log.debug("Request must be logged to client via websocket. Invoking log helper.");
            httpRequestLoggerHelper.log((HttpServletRequest) request);
        }


        // Go ahead with other filters...
        chain.doFilter(request, response);
    }
}
