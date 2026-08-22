from fastapi import APIRouter

from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from modules.me_chat.service.chat_revoke_svc import ChatOrchestrationService

router = APIRouter(
    prefix="/v2/chat",
    tags=["chat"]
)

chat_service = ChatOrchestrationService()

@router.post("")
def chat(chat_request: ChatRequest) -> ChatResponse:
    return chat_service.create_message(chat_request.message,chat_request.conversation_id)
