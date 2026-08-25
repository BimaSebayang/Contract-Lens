import {
    LlmMessageConversation,
} from '@/core/dto/LlmMessageConversation';


export type InteractionIntentProps = {

    conversation:
        LlmMessageConversation;

    index: number;

    onFeedback: (
        index: number,
        feedback: boolean
    ) => void;

};