import {
    LlmMessageConversation,
} from '@/core/dto/LlmMessageConversation';
import {LlmOrchestrationAction} from "@/core/dto/ChatAiMessageResponse";


export type InteractionIntentProps = {

    conversation:
        LlmMessageConversation;

    index: number;

    onFeedback: (
        index: number,
        feedback: boolean
    ) => void;

    handleAction: (action:LlmOrchestrationAction)=>void;
};