import chatClient from '../clients/chat.client';

import {ChatAiMessageRequest,} from '@/core/dto/ChatAiMessageRequest';

import {ChatAiMessageResponse,} from '@/core/dto/ChatAiMessageResponse';

import {LlmOrchestrationConversation,} from '@/core/dto/LlmOrchestrationConversation';


const chatService = {

    sendMessage: async (
        conversationId: string,
        userMessage: string,
    ): Promise<LlmOrchestrationConversation> => {

        const request:
            ChatAiMessageRequest = {
            message:userMessage,
            conversation_id: conversationId,
        };


        const response:ChatAiMessageResponse =
            await chatClient.sendMessage(
                request
            );

        return {
            intent: response.intent,
            feedback: null,
            actions:response.actions,
            content: [{
                message: response.ai_response
            }],

            timestamp:
                new Date()
                    .toLocaleTimeString(
                        [],
                        {
                            hour: '2-digit',
                            minute: '2-digit',
                        }
                    ),
            conversation_id: conversationId
        };
    },

};


export default chatService;