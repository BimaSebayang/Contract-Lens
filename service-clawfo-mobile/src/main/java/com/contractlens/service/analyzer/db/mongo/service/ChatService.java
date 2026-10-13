package com.contractlens.service.analyzer.db.mongo.service;

import com.contractlens.common.dto.ClawfoJwtPayload;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoChatDocument;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoMessageRequest;

public interface ChatService {

    void chatSend(
            ClawfoMessageRequest request,
            ClawfoJwtPayload clawfoJwtPayload,
            String chatId
    );

    ClawfoChatDocument getChat(String chatId);
}
