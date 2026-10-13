package com.contractlens.service.analyzer.infrastructure;

import com.contractlens.common.dto.ClawfoJwtPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClawfoWebSocketAuthInterceptor
        implements ChannelInterceptor {

    private final ClawfoJwtService clawfoJwtService;

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel
    ) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        if (accessor == null) {
            return message;
        }

        String uuid = UUID.randomUUID().toString();

        MDC.put("uuid", uuid);
        MDC.put("user", "ANONYMOUS");

        try {

            StompCommand command =
                    accessor.getCommand();

            MDC.put(
                    "method",
                    command != null
                            ? command.name()
                            : "UNKNOWN"
            );

            MDC.put(
                    "url",
                    accessor.getDestination() != null
                            ? accessor.getDestination()
                            : "/ws"
            );

            log.info(
                    "Incoming Clawfo WebSocket request"
            );

            if (StompCommand.CONNECT.equals(command)) {

                String authorization =
                        accessor.getFirstNativeHeader(
                                "Authorization"
                        );


                if (authorization != null) {
                    if (authorization.startsWith("Bearer ")) {
                        authorization = authorization.substring(7);
                    } else {
                        authorization = authorization;
                    }
                }

                ClawfoJwtPayload payload =
                        clawfoJwtService.getTokenPayload(
                                authorization
                        );

                if (payload == null) {

                    log.warn(
                            "Clawfo WebSocket authentication failed"
                    );

                    throw new BadCredentialsException(
                            "Invalid authorization token"
                    );
                }

                MDC.put(
                        "user",
                        payload.getEmail()
                );

                log.info(
                        "Clawfo WebSocket authentication successful"
                );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                payload,
                                null,
                                Collections.emptyList()
                        );

                accessor.setUser(authentication);
            }

            return message;

        } finally {

            MDC.clear();
        }
    }
}