import axios from 'axios';

import {
    environment,
} from '../../../environment';


import {
    ChatAiMessageRequest,
} from '@/core/dto/ChatAiMessageRequest';

import {
    ChatAiMessageResponse,
} from '@/core/dto/ChatAiMessageResponse';


const chatClient = {

    sendMessage: async (
        request: ChatAiMessageRequest
    ): Promise<ChatAiMessageResponse> => {


        try {
            const response =
                await axios.post<
                    ChatAiMessageResponse
                >(
                    environment.apiUrl +
                    '/v2/chat/contract-lens',

                    request,
                    {
                        timeout: 10000,
                    }
                );
            return response.data;
        } catch (error) {
            throw error;
        }
    },

};


export default chatClient;