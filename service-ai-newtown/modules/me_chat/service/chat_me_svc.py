from typing import List

from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from core.message import Message
from dbs.mongodb.repositories.conversation_repository import ConversationRepository
from llms.prompts.context.service.context_service import ContextService
from llms.prompts.intents.service.intents_service import IntentPromptService
from llms.prompts.system.service.system_prompt_service import SystemPromptService
from llms.prompts.template.service.template_service import TemplateService
from modules.me_chat.service.orchestration_svc import OrchestrationService


def _build_contract_lens_message(
        system_prompt_key: str,
        template_key: str
) -> List[Message]:

    context_service = ContextService()
    system_prompt_service = SystemPromptService()
    intent_prompt_service = IntentPromptService()
    template_service = TemplateService()

    # ==================================================
    # CONTEXT
    # ==================================================

    context_contractlens = (
        context_service.get_ai_prompt_architecture()
    )

    # ==================================================
    # INTENT DETECTION COMPONENT
    # ==================================================

    intent_detection_prompt = ''
    intent_glossary = ''

    if system_prompt_key == "INTENT_DETECTION":

        intent_detection_prompt = (
            intent_prompt_service.get_intent_detection_prompt()
        )

        intent_glossary = (
            intent_prompt_service.get_intent_glossary()
        )

    # ==================================================
    # SYSTEM PROMPT
    # ==================================================

    system_prompt = (
        system_prompt_service.get_system_prompt(
            prompt_key=system_prompt_key
        )
    )

    # ==================================================
    # TEMPLATE
    # ==================================================

    template = (
        template_service.get_template(
            prompt_key=template_key
        )
    )

    # ==================================================
    # BUILD SYSTEM MESSAGE
    # ==================================================

    main_content = (
            (context_contractlens or "")
            + (system_prompt or "")
            + (intent_detection_prompt or "")
            + (intent_glossary or "")
            + (template or "")
    )

    return [
        Message(
            role=Role.SYSTEM,
            content=main_content,
            reason=''
        )
    ]


class ChatMeService:

    def __init__(self):
        self.orc = OrchestrationService()
        self.conversation_repository = ConversationRepository()

    # ==================================================
    # TEST
    # ==================================================

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

    # ==================================================
    # INTENT DETECTION
    # ==================================================

    def send_message_contract_lens_intents(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="INTENT_DETECTION",
            template_key="TP_INTENT_DETECTION"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # GREETING FIRST TIMER
    # ==================================================

    def send_message_contract_greeting_first_timer(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="GREETING_FIRST_TIMER",
            template_key="TP_GREETING_FIRST_TIMER"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # UNKNOWN
    # ==================================================

    def send_message_unknown(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="UNKNOWN",
            template_key="TP_UNKNOWN"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # INTRODUCE CONTRACTLENS
    # ==================================================

    def send_introduce_contractlens(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="INTRODUCE_CONTRACTLENS",
            template_key="TP_INTRODUCE_CONTRACTLENS"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # GREETING ALREADY KNOW
    # ==================================================

    def send_greeting_already_known(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="GETTING_ALREADY_KNOW_APP",
            template_key="TP_GREETING_ALREADY_KNOW_APPLICATION"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # TEACH HOW TO USE
    # ==================================================

    def send_teach_how_to_use(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        additional_message = _build_contract_lens_message(
            system_prompt_key="TEACH_HOW_TO_USE_CONTRACTLENS",
            template_key="TP_TEACH_HOW_TO_USE_CONTRACTLENS"
        )

        return self._orchestrate_contract_lens(
            content,
            additional_message
        )

    # ==================================================
    # COMMON ORCHESTRATION
    # ==================================================

    def _orchestrate_contract_lens(
            self,
            content: ChatRequest,
            additional_message: List[Message]
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
                context=additional_message[0].content
            )
            for message in self.orc.orchestrate(
                user_prompt,
                content.conversation_id,
                additional_message
            )
        ]