package com.contractlens.service.analyzer.db.mongo.service.impl;

import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.common.enums.TimeFormatType;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoChatDocument;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoMessageRequest;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoChatRepository;
import com.contractlens.service.analyzer.db.mongo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoPesananChatService implements ChatService {

    private final ClawfoChatRepository clawfoChatRepository;

    @Override
    public void chatSend(
            ClawfoMessageRequest request,
            ClawfoJwtPayload clawfoJwtPayload,
            String chatId
    ) {

        LocalDateTime now = LocalDateTime.now();

        request.setTime(
                now.format(
                        DateTimeFormatter.ofPattern(
                                TimeFormatType.YYYYMMDDHHMMSS.getPattern()
                        )
                )
        );


        request.setType(clawfoJwtPayload.getUserType());
        request.setName(clawfoJwtPayload.getUsername());

        request.setMessageId(UUID.randomUUID().toString());

        request.setCreatedBy(
                clawfoJwtPayload.getEmail()
        );

        request.setCreatedDate(now);

        request.setUpdatedBy(
                clawfoJwtPayload.getEmail()
        );

        request.setUpdatedDate(now);

        ClawfoChatDocument chatDocument = getChat(chatId);

        chatDocument.getMessages().add(request);

        chatDocument.setUpdatedBy(
                clawfoJwtPayload.getEmail()
        );

        chatDocument.setUpdatedDate(now);

        if (chatDocument.getCreatedDate() == null) {

            chatDocument.setCreatedBy(
                    clawfoJwtPayload.getEmail()
            );

            chatDocument.setCreatedDate(now);
        }

        clawfoChatRepository.save(chatDocument);
    }

    @Override
    public ClawfoChatDocument getChat(String chatId) {
        return  clawfoChatRepository.findById(chatId)
                        .orElseGet(() ->
                                ClawfoChatDocument.builder()
                                        .chatId(chatId)
                                        .messages(new ArrayList<>())
                                        .build()
                        );
    }
}
