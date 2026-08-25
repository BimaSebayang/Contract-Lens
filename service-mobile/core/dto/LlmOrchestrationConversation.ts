import {LlmMessageMap} from "@/core/dto/LlmMessageConversation";
import {LlmOrchestrationAction} from "@/core/dto/ChatAiMessageResponse";


export interface LlmOrchestrationConversation {
    feedback: boolean | null;
    content: [LlmMessageMap];
    timestamp:string;
    conversation_id:string;
    intent ?: string;
    actions ?: [LlmOrchestrationAction];
}