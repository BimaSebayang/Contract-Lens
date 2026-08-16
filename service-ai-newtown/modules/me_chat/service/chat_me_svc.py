from modules.me_chat.service.orchestration_svc import OrchestrationService

import logging
from typing import List

from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from core.message import Message
from db.mongodb.repositories.conversation_repository import ConversationRepository
from llm.prompts.context.service.context_service import ContextService

from llm.prompts.intents.service.intents_service import IntentPromptService
from llm.prompts.system.service.system_prompt_service import SystemPromptService
from llm.prompts.template.service.template_service import TemplateService


logger = logging.getLogger(__name__)


class ChatMeService:

    def __init__(self):
        self.orc = OrchestrationService()
        self.conversation_repository = ConversationRepository()

    def send_message_uji_coba(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        user_prompt = Message(
            role=Role.USER,
            content=content.message,
            reason=''
        )

        return [
            ChatResponse(
                content=message.content,
                reason=message.reason,
                context=''
            )
            for message in self.orc.orchestrate(
                user_prompt,
                content.conversation_id,
                []
            )
        ]

    def send_message_contract_lens_intents(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        context_service = ContextService()
        system_prompt_service = SystemPromptService()
        intent_prompt_service = IntentPromptService()
        template_service = TemplateService()

        user_prompt = Message(
            role=Role.USER,
            content=content.message,
            reason=''
        )

        context_contractlens = (
            context_service.get_ai_prompt_architecture()
        )

        system_prompt_creator = (
            system_prompt_service.get_system_prompt(
                prompt_key="SYSTEM_PROMPT_CREATOR"
            )
        )

        system_prompt_global = (
            system_prompt_service.get_system_prompt(
                prompt_key="SYSTEM_PROMPT_GLOBAL"
            )
        )

        intent_detection_prompt = (
            intent_prompt_service.get_intent_detection_prompt()
        )

        intent_glossary = (
            intent_prompt_service.get_intent_glossary()
        )

        intent_detection_template = (
            template_service.get_template(
                prompt_key="TP_INTENT_DETECTION"
            )
        )
        main_content = (
                (context_contractlens or "")
                + (system_prompt_creator or "")
                + (system_prompt_global or "")
                + (intent_detection_prompt or "")
                + (intent_glossary or "")
                + (intent_detection_template or "")
        )

        additional_message: List[Message] = [
            Message(
                role=Role.SYSTEM,
                content=main_content,
                reason=''
            )
        ]

        return [
            ChatResponse(
                content=message.content,
                reason=message.reason,
                context=main_content
            )
            for message in self.orc.orchestrate(
                user_prompt,
                content.conversation_id,
                additional_message
            )
        ]

    def send_message_contract_greeting_first_timer(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        context_service = ContextService()
        system_prompt_service = SystemPromptService()
        template_service = TemplateService()

        user_prompt = Message(
            role=Role.USER,
            content=content.message,
            reason=''
        )

        context_contractlens = (
            context_service.get_ai_prompt_architecture()
        )

        system_prompt_creator = (
            system_prompt_service.get_system_prompt(
                prompt_key="SYSTEM_PROMPT_CREATOR"
            )
        )

        greeting_first_timer_prompt = (
            system_prompt_service.get_system_prompt(
                prompt_key="GREETING_FIRST_TIMER"
            )
        )

        greeting_first_timer_template = (
            template_service.get_template(
                prompt_key="TP_GREETING_FIRST_TIMER"
            )
        )

        main_content = (
                (context_contractlens or "")
                + (system_prompt_creator or "")
                + (greeting_first_timer_prompt or "")
                + (greeting_first_timer_template or "")
        )

        additional_message: List[Message] = [
            Message(
                role=Role.SYSTEM,
                content=main_content,
                reason=''
            )
        ]

        return [
            ChatResponse(
                content=message.content,
                reason=message.reason,
                context=main_content
            )
            for message in self.orc.orchestrate(
                user_prompt,
                content.conversation_id,
                additional_message
            )
        ]