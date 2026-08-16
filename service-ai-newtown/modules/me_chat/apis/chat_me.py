from typing import List

from fastapi import APIRouter

from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from modules.me_chat.service.chat_me_svc import ChatMeService


router = APIRouter(
    prefix="/v1/chat",
    tags=["chat"]
)

chat_service = ChatMeService()

@router.post("/uji-coba-test")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_message_uji_coba(chat_request)

@router.post("/contract-lens/intents")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_message_contract_lens_intents(chat_request)

@router.post("/contract-lens/greeting-first-timer")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_message_contract_greeting_first_timer(chat_request)

@router.post("/contract-lens/unknown")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_message_unknown(chat_request)

@router.post("/contract-lens/greeting-already-know")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_greeting_already_known(chat_request)

@router.post("/contract-lens/introduce-contract")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_introduce_contractlens(chat_request)

@router.post("/contract-lens/how-to-use")
def chat(chat_request: ChatRequest) -> List[ChatResponse]:
    return chat_service.send_teach_how_to_use(chat_request)