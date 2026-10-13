package com.contractlens.service.analyzer.module.pesanan.controller;

import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoMessageRequest;
import com.contractlens.service.analyzer.infrastructure.ClawfoJwtService;
import com.contractlens.service.analyzer.module.pesanan.service.ClawfoChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ClawfoChatWsController {

    private final ClawfoChatService chatService;
    private final ClawfoJwtService clawfoJwtService;

    @MessageMapping("/V.1.0.0/chat.send/{chatId}")
    public void send(
            @DestinationVariable String chatId,
            ClawfoMessageRequest request,
            Principal principal
    ) {
        ClawfoJwtPayload clawfoJwtPayload = clawfoJwtService.getCurrentUser(principal);
        chatService.chatSend(request,clawfoJwtPayload,chatId);
    }

}
