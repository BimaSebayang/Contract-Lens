from pydantic import BaseModel


class ChatResponse(BaseModel):
    content: str
    selected_intent: str = ""
    reason: str
    message_context: str
    intent_context: str