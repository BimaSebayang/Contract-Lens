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
            conversation_id: str,
            intent_selection:List[str]
    ) -> IntentResponse:


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
            self.intent_repository.get_by_ids(intent_selection)
        )

        print(f"Total intents: {len(all_intents)}")

        final_context = f"""
Task: {content_intent_task}

Dengan Semua Intent Yaitu:
"""

        for index, intent in enumerate(all_intents):
            final_context += f"""
Intent: {intent.name}
Threshold: {intent.classification.threshold}
Required Context: {intent.execution.required_context}
Exclude Context: {intent.execution.exclude_context}
"""


        final_context += f"""
Response Intent Strategy: JSON
Response Intent Format:
{{
    "intent": "{intent.name}",
    "example_user_message": [
        "<variasi pesan 1>",
        "<variasi pesan 2>",
        "<variasi pesan 3>",
        "<variasi pesan 4>",
        "<variasi pesan 5>"
    ]
}}

Buat tepat 5 variasi pesan pengguna dalam bahasa Indonesia yang memiliki
maksud dan konteks spesifik yang sama dengan pesan pengguna.

Setiap contoh harus memiliki variasi yang jelas dalam pilihan kata, struktur
kalimat, gaya bahasa, tingkat formalitas, dan cara pengguna menyampaikan maksud.

Pertahankan atribut penting dari pesan asli. Jangan mengubah informasi spesifik
yang membedakan makna atau konteks pesan.

Khusus untuk salam berdasarkan waktu, setiap zona waktu dianggap sebagai konteks
yang berbeda dan BUKAN sinonim. Jangan mengganti atau mencampurkan "pagi",
"siang", "sore", atau "malam" dalam satu kelompok variasi.

Contoh:
"Selamat pagi" hanya boleh divariasikan menjadi salam dengan konteks pagi.
"Selamat siang" hanya boleh divariasikan menjadi salam dengan konteks siang.
"Selamat sore" hanya boleh divariasikan menjadi salam dengan konteks sore.
"Selamat malam" hanya boleh divariasikan menjadi salam dengan konteks malam.

Jangan menghasilkan contoh yang identik atau hanya mengganti satu atau dua kata.
Pastikan setiap variasi tetap mempertahankan maksud dan konteks spesifik pesan asli.
"""


        messages: List[Message] = self._revoke_intent(
            user_prompt,
            conversation_id,
            final_context
        )

        print(f"create intent message is {messages[0].content}")

        intent_response = IntentResponse.model_validate_json(
            messages[0].content
        )

        intent_response.context = final_context

        return intent_response

    def create_message(
            self,
            user_prompt: str,
            conversation_id: str
    ) -> ChatResponse:

        # =========== Pembatas Dari Vector
        chat_responses:List[ChatResponse]=self.embedding_service.find_similar_intents(user_prompt=user_prompt)

        if not chat_responses or len(chat_responses) > 2:
            intent_datas = []
            for chat_r in chat_responses:
                intent_datas.append(chat_r.selected_intent)
        else:
            return ChatResponse(
                content=chat_responses[0].content,
                selected_intent=chat_responses[0].selected_intent,
                reason=chat_responses[0].reason,
                message_context=chat_responses[0].message_context,
                intent_context=chat_responses[0].intent_context
            )

        # =========== Pembatas Dari LLM

        intent_response = self.create_intent(
            user_prompt,
            conversation_id,
            intent_datas
        )

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


        message_result: List[Message] = self._orchestrate(
            user_prompt,
            conversation_id,
            final_context,
            True,
            ai_lab_constants.GPT_OSS_20b,
            "medium"
        )

        self.update_keywords(
            intent_response,
            user_prompt,
            message_result[0]
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

        message_result: List[Message] = self._orchestrate(
            user_prompt,
            conversation_id,
            final_context,
            True,
            ai_lab_constants.GPT_OSS_20b,
            "none"
        )

        return ChatResponse(
            content=message_result[0].content,
            selected_intent="UNKNOWN",
            reason=message_result[0].reason,
            message_context=final_context,
            intent_context=intent_response.context
        )

    def update_keywords(
            self,
            intent_response: IntentResponse,
            user_prompt:str,
            message_response: Message
    ):
        intent_id = intent_response.intent


        other_clara_response = [message_response.content]

        example_synonymous_user_messages = (
            self.embedding_service.create_vectors(
                intent_response.example_user_message
            )
        )

        example_synonymous_user_messages.append(
            self.embedding_service.create_vector(user_prompt)
        )

        user_message_examples: List[UserMessageExample] = [
            UserMessageExample(
                message=example_user_message.message,
                vector=example_user_message.vector
            )
            for example_user_message in example_synonymous_user_messages
        ]

        self.intent_repository.update_user_message_examples(
            intent_id=intent_id,
            other_clara_response=other_clara_response,
            user_message_examples=user_message_examples
        )

    def _revoke_intent(
            self,
            user_prompt: str,
            conversation_id: str,
            context: str,
    ) -> List[Message]:

        return self._orchestrate(
            user_prompt,
            conversation_id,
            context,
            False,
            ai_lab_constants.GPT_OSS_120b,
            "medium"
        )

    def _orchestrate(
            self,
            user_prompt: str,
            conversation_id: str,
            system_message: str,
            need_save: bool,
            bot_model:str,
            effort:str
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
            bot_model,
            effort
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