from typing import List

from fastapi import APIRouter

from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from core.intent_response import IntentResponse
from modules.me_chat.service.chat_me_svc import ChatMeService
from modules.me_chat.service.chat_revoke_svc import ChatOrchestrationService

router = APIRouter(
    prefix="/v2/chat",
    tags=["chat"]
)

chat_service = ChatOrchestrationService()

@router.post("/check-intent")
def check_intent(chat_request: ChatRequest) -> IntentResponse:
    return chat_service.create_intent(chat_request.message,chat_request.conversation_id)

@router.post("")
def chat(chat_request: ChatRequest) -> ChatResponse:
    return chat_service.create_message(chat_request.message,chat_request.conversation_id)
