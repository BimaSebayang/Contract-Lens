from typing import List

from fastapi import APIRouter

from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from modules.fakechat.service.chat_me_svc import ChatMeService


router = APIRouter(
    prefix="/v1/chat",
    tags=["chat"]
)

chat_service = ChatMeService()


@router.get("/test")
def test(role: Role) -> str:
    return chat_service.last_chat(role)


@router.post("/")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_message(chat_request)