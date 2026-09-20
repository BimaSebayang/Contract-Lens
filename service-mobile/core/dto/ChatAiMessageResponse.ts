export interface LlmOrchestrationAction{
    intent: string ;
    ai_header: string ;
    ai_detail: string ;
}

export interface ChatAiMessageResponse {
    ai_response: string;
    intent: string;
    actions?: [LlmOrchestrationAction]
}