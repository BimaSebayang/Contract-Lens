package com.contractlens.service.analyzer.infrastructure;


import com.contractlens.common.dto.ClawfoJwtPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class AppCredentialFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(AppCredentialFilter.class);

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final ClawfoJwtService clawfoJwtService;

    private static final List<String> EXCLUDED_URLS = List.of(
            "/**/mobile/login",
            "/**/mobile/login/refresh",
            "/**/mobile/logout",
            "/**/registration-lapak/send-otp",
            "/**/registration-lapak/validate-otp",
            "/**/registration-lapak/create-account"
    );

    private static final String APPLICATION_CONTEXT = "APPLICATION_CONTEXT";


    public AppCredentialFilter(ClawfoJwtService clawfoJwtService) {
        this.clawfoJwtService = clawfoJwtService;
    }


    @SuppressWarnings("java:S2259")
    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        String token = null;

        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            } else {
                token = authorization;
            }
        }

        String uuid = UUID.randomUUID().toString();
        String url = request.getRequestURI();

        MDC.put("uuid", uuid);
        MDC.put("user", "ANONYMOUS");
        MDC.put("method", request.getMethod());
        MDC.put("url", url);

        log.info("Incoming Clawfo request");

        boolean isValidRequest = true;

        boolean isExcludedUrl = EXCLUDED_URLS.stream()
                .anyMatch(pattern -> PATH_MATCHER.match(pattern, url));

        if (!isExcludedUrl) {

            if (token == null || token.isBlank()) {

                isValidRequest = false;

                log.warn("Clawfo request rejected: authorization token is missing");

            } else {

                ClawfoJwtPayload clawfoJwtPayload =
                        clawfoJwtService.getTokenPayload(token);

                if (Objects.isNull(clawfoJwtPayload)) {

                    isValidRequest = false;

                    log.warn("Clawfo request rejected: invalid authorization token");

                } else {

                    MDC.put("user", clawfoJwtPayload.getEmail());

                    log.info(
                            "Clawfo authentication successful"
                    );
                }
            }

        } else {

            log.info("Clawfo authentication skipped: excluded endpoint");
        }

        if (isValidRequest) {

            try {
                filterChain.doFilter(request, response);
            } finally {

                MDC.clear();
                SecurityContextHolder.clearContext();
            }

        } else {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            log.warn(
                    "Clawfo request unauthorized with status={}",
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.getWriter().write("""
                {
                    "message": "Unauthorized"
                }
                """);

            MDC.clear();
            SecurityContextHolder.clearContext();
        }
    }

}
