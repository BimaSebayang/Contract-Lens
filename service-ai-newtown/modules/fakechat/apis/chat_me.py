from fastapi import APIRouter

from commons.enums.role import Role
from modules.fakechat.service.chat_me_svc import ChatMeService

router = APIRouter(prefix="/v1/chat", tags=["chat"])

@router.get("/test")
def test(role:Role)-> str:
    chat = ChatMeService()
    return chat.last_chat(role)