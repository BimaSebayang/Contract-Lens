import {LlmMessageMap} from "@/core/dto/LlmMessageConversation";

export interface LlmOrchestrationConversation {
    feedback: boolean | null;
    content: [LlmMessageMap];
    timestamp:string;
    conversation_id:string;
    intent ?: string;
}