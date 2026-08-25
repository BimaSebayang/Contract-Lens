import {StyleProp, TextStyle, ViewStyle} from "react-native";
import {LlmOrchestrationAction} from "@/core/dto/ChatAiMessageResponse";

export type LlmMessageRole =
    | 'user'
    | 'assistant';

export interface LlmMessageMap{
    message: string;
    styleView?: StyleProp<ViewStyle>;
    styleText?: StyleProp<TextStyle>;
    styleTime?: StyleProp<TextStyle>;
}

export interface LlmMessageConversation {

    role: LlmMessageRole;

    content: LlmMessageMap;

    feedback: boolean | null;

    timestamp: string;

    showFeedback: boolean | null;

    intent?:string;

    actions ?: [LlmOrchestrationAction];
}