package com.contractlens.service.analyzer.module.pesanan.service;

import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoChatDocument;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoMessageRequest;
import com.contractlens.common.enums.TimeFormatType;
import com.contractlens.service.analyzer.db.mongo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoChatService {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    public void chatSend(ClawfoMessageRequest request, ClawfoJwtPayload clawfoJwtPayload, String chatId) {
        chatService.chatSend(request,clawfoJwtPayload,chatId);

        log.info("start to send request {}" , request);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            log.error(
                    "Interrupted while waiting to send chat message",
                    e
            );
        }

        messagingTemplate.convertAndSend(
                "/clawfo/topic/V.1.0.0/chat.receive/" + chatId,
                request
        );

    }


    public ClawfoChatDocument getChat(String deviceId, String ticketId, String chatId) {
        return chatService.getChat(chatId);
    }
}
