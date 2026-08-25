export interface LlmOrchestrationAction{
    intent?: string | null;
    ai_header?: string | null;
    ai_detail?: string | null;
}

export interface ChatAiMessageResponse {
    ai_response: string;
    intent: string;
    actions?: [LlmOrchestrationAction]
}