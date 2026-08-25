package com.contractlens.service.module.chat.service;

import com.contractlens.common.dto.ChatAIRequest;
import com.contractlens.common.dto.ChatAiMessageResponse;
import com.contractlens.common.dto.ChatAiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractLensChatIntentsService {
    private static String GREETING_USER = "GREETING_USER";

    public void createActionButtonsWhenGreeting(ChatAiMessageResponse chatAiMessageResponse){
        if(GREETING_USER.equalsIgnoreCase(chatAiMessageResponse.getIntent())){
            List<ChatAiMessageResponse.ActionButton> actions = new ArrayList<>();
            ChatAiMessageResponse.ActionButton actionGlosary = new ChatAiMessageResponse.ActionButton();
            actionGlosary.setAiHeader("Apa Itu ContractLens?");
            actionGlosary.setAiDetail("Kenalan dengan contractLens dan cara kerjanya");
            actionGlosary.setIntent("GLOSSARY_CONTRACTLENS");
            actions.add(actionGlosary);
            ChatAiMessageResponse.ActionButton actionAnalyze = new ChatAiMessageResponse.ActionButton();
            actionAnalyze.setAiHeader("Mulai Dengan ContractLens");
            actionAnalyze.setAiDetail("Daftar dan Siapkan API mu");
            actionAnalyze.setIntent("ANALYZE_API_CONTRACT");
            actions.add(actionAnalyze);
            ChatAiMessageResponse.ActionButton actionLogin = new ChatAiMessageResponse.ActionButton();
            actionLogin.setAiHeader("Login Ke ContractLens");
            actionLogin.setAiDetail("Masuk untuk melanjutkan apa yang sudah kamu lakukan");
            actionLogin.setIntent("LOGIN_CONTRACTLENS");
            actions.add(actionLogin);
            chatAiMessageResponse.setActions(actions);
        }
    }

}
