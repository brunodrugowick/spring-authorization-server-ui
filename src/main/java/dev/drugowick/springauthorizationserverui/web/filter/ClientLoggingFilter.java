package dev.drugowick.springauthorizationserverui.web.filter;

import dev.drugowick.springauthorizationserverui.web.socket.HttpRequestLoggerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ClientLoggingFilter implements Filter {

    private final HttpRequestLoggerHelper httpRequestLoggerHelper;

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            httpRequestLoggerHelper.log((HttpServletRequest) request);
        }

        // Go ahead with other filters...
        chain.doFilter(request, response);
    }
}
