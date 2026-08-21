from typing import List

from commons import ai_lab_constants
from commons.enums.role import Role
from core.chat_response import ChatResponse
from core.intent_response import IntentResponse
from core.message import Message
from dbs.mongodb.models.conversation import ConversationDetail
from dbs.mongodb.models.intent import UserMessageExample
from dbs.mongodb.repositories.conversation_repository import ConversationRepository
from dbs.mongodb.repositories.intent_repository import IntentRepository
from dbs.postgres.client import SessionLocal
from dbs.postgres.repositories import ContractLensAiPromptRepository
from llms.models.llm_chat_response import LlmChatResponse
from llms.models.llm_message_response import LlmMessageResponse
from llms.models.llm_usage_response import LlmUsageResponse
from llms.providers.ollama.client import OllamaClient
from modules.me_chat.service.embedding_service import EmbeddingService


class ChatOrchestrationService:
    def __init__(self):
        self.provider = OllamaClient()
        self.conversation_repository = ConversationRepository()
        self.intent_repository = IntentRepository()
        self.embedding_service = EmbeddingService()

        with SessionLocal() as session:
            self.contract_ai_prompt_repository = ContractLensAiPromptRepository(session)

    def create_intent(
            self,
            user_prompt: str,
            conversation_id: str
    ) -> IntentResponse:

        print(
            f"Message use for {user_prompt} {conversation_id}"
        )

        messages: List[Message] = self._revoke_intent(
            user_prompt,
            conversation_id
        )

        # for index, message in enumerate(messages):
        #     print(
        #         f"Message create_intent [{index}] | "
        #         f"role={message.role} | "
        #         f"content={message.content} | "
        #         f"reason={message.reason}"
        #     )

        intent_response = IntentResponse.model_validate_json(
            messages[0].content
        )

        intent_response.context = messages[0].reason

        return intent_response

    def create_message(
            self,
            user_prompt: str,
            conversation_id: str
    ) -> ChatResponse:

        clara_identity = (
            self.contract_ai_prompt_repository
            .find_active_by_prompt_key("CLARA_IDENTITY")
        )

        clara_identity_context = (
            clara_identity.content
            if clara_identity
            else None
        )

        clx_overview = (
            self.contract_ai_prompt_repository
            .find_active_by_prompt_key("CONTRACTLENS_OVERVIEW")
        )

        clx_overview_context = (
            clx_overview.content
            if clx_overview
            else None
        )

        intent_response = self.create_intent(
            user_prompt,
            conversation_id
        )

        intent_task = self.intent_repository.get_by_id(
            intent_response.intent
        )

        if intent_task is None:
            return self._create_unknown_message(
                user_prompt=user_prompt,
                conversation_id=conversation_id,
                clara_identity_context=clara_identity_context,
                clx_overview_context=clx_overview_context,
                intent_response=intent_response
            )

        final_context = f"""
Clara Identity: {clara_identity_context}
ContractLens Overview: {clx_overview_context}

Task:
{intent_task.execution.task_context}
"""

        print(f"final_context for _create_{intent_task.id}_message adalah : {final_context}")


        message_result: List[Message] = self._orchestrate(
            user_prompt,
            conversation_id,
            final_context,
            True,
            ai_lab_constants.GPT_OSS_20b
        )

        self.update_keywords(
            intent_response
        )

        return ChatResponse(
            content=message_result[0].content,
            reason=message_result[0].reason,
            selected_intent=intent_task.name,
            message_context=final_context,
            intent_context=intent_response.context
        )

    def _create_unknown_message(
            self,
            user_prompt: str,
            conversation_id: str,
            clara_identity_context: str,
            clx_overview_context: str,
            intent_response: IntentResponse
    ) -> ChatResponse:

        final_context = f"""
Clara Identity: {clara_identity_context}
ContractLens Overview: {clx_overview_context}

Task:
Intent pesan ini adalah UNKNOWN.
Tanggapi statement atau pertanyaan pengguna secara singkat berdasarkan informasi
yang benar-benar diberikan pengguna, tanpa berpura-pura memiliki keinginan,
perasaan, aktivitas, atau kemampuan yang tidak dimiliki CLAra.
Jika pengguna mengajak, menawarkan, atau membahas aktivitas di luar konteks
ContractLens, tanggapi secara natural sesuai identitas CLAra sebagai AI Assistant,
kemudian arahkan percakapan ke tujuan utama CLAra, yaitu membantu pengguna
mengenal dan menggunakan ContractLens.
Jika maksud pengguna ambigu atau tidak cukup jelas, nyatakan bahwa CLAra belum
sepenuhnya memahami maksudnya dan minta pengguna menjelaskan kembali.
Jangan mengarang, menebak, atau memberikan jawaban penuh untuk topik di luar
ContractLens.
Gunakan emoji seperti 🤔 atau ❓ secara natural agar pesan lebih menarik.
"""

        print(f"final_context for _create_unknown_message adalah : {final_context}")

        message_result: List[Message] = self._orchestrate(
            user_prompt,
            conversation_id,
            final_context,
            True,
            ai_lab_constants.GPT_OSS_20b
        )

        return ChatResponse(
            content=message_result[0].content,
            selected_intent="UNKNOWN",
            reason="intent undetected",
            message_context="none",
            intent_context=intent_response.context
        )

    def update_keywords(
            self,
            intent_response: IntentResponse
    ):

        intent_id = intent_response.intent

        other_clara_response = (
            intent_response.other_clara_response
        )

        example_synonymous_user_messages = (
            self.embedding_service.create_vectors(
                intent_response.example_user_message
            )
        )

        user_message_examples: List[UserMessageExample] = [
            UserMessageExample(
                message=example_user_message.message,
                vector=example_user_message.vector
            )
            for example_user_message
            in example_synonymous_user_messages
        ]

        self.intent_repository.update_user_message_examples(
            intent_id=intent_id,
            other_clara_response=other_clara_response,
            user_message_examples=user_message_examples
        )

    def _revoke_intent(
            self,
            user_prompt: str,
            conversation_id: str
    ) -> List[Message]:

        intent_task = (
            self.contract_ai_prompt_repository
            .find_active_by_prompt_key("INTENTS_TASK")
        )

        content_intent_task = (
            intent_task.content
            if intent_task
            else None
        )

        all_intents = (
            self.intent_repository.get_all_active()
        )

        final_context = f"""
Task: {content_intent_task}

Dengan Semua Intent Yaitu:
"""

        for index, intent in enumerate(all_intents):
            print(f"LOOP INTENT [{index}]: {intent}")

            final_context += f"""
Intent: {intent.name}
Threshold: {intent.classification.threshold}
Required Context: {intent.execution.required_context}
Exclude Context: {intent.execution.exclude_context}
Response Strategy: {intent.response.strategy}
Response Format: {intent.response.template}
"""

        print(f"final_context for _revoke_intent adalah : {final_context}")

        return self._orchestrate(
            user_prompt,
            conversation_id,
            final_context,
            False,
            ai_lab_constants.GPT_OSS_120b
        )

    def _orchestrate(
            self,
            user_prompt: str,
            conversation_id: str,
            system_message: str,
            need_save: bool,
            bot_model:str
    ) -> List[Message]:

        history = (
                self.conversation_repository.get_history(
                    conversation_id
                )
                or []
        )

        messages: List[Message] = [
            Message(
                role=Role.SYSTEM,
                content=system_message,
                reason=""
            ),
            *history,
            Message(
                role=Role.USER,
                content=user_prompt,
                reason=""
            )
        ]

        # for index, message in enumerate(messages):
        #     print(
        #         f"Chat message [{index}] | "
        #         f"role={message.role} | "
        #         f"content={message.content} | "
        #         f"reason={message.reason}"
        #     )

        response: LlmChatResponse = self.provider.chat(
            messages,
            bot_model
        )

        response_messages: List[Message] = []

        if need_save:
            self.conversation_repository.save_detail(
                conversation_id,
                ConversationDetail(
                    LlmMessageResponse(
                        role=Role.USER,
                        content=user_prompt,
                        reasoning="",
                        finishing_reason="",
                        approve=True
                    ),
                    LlmUsageResponse(
                        completion_tokens=response.usage.completion_tokens,
                        prompt_tokens=response.usage.prompt_tokens,
                        total_tokens=response.usage.total_tokens
                    )
                )
            )

        for index, message in enumerate(response.messages):

            response_messages.append(
                Message(
                    role=None,
                    content=message.content,
                    reason=message.reasoning
                )
            )

            if need_save:
                self.conversation_repository.save_detail(
                    conversation_id,
                    ConversationDetail(
                        LlmMessageResponse(
                            role=message.role,
                            content=message.content,
                            reasoning=message.reasoning,
                            finishing_reason=message.finishing_reason,
                            approve=index == 0
                        ),
                        LlmUsageResponse(
                            completion_tokens=response.usage.completion_tokens,
                            prompt_tokens=response.usage.prompt_tokens,
                            total_tokens=response.usage.total_tokens
                        )
                    )
                )

        return response_messages