from typing import List
import logging
from commons import ai_lab_constants
from core.message import Message
from db.mongodb.models.conversation import ConversationDetail
from db.mongodb.repositories.conversation_repository import ConversationRepository
from llm.models.llm_chat_response import LlmChatResponse
from llm.models.llm_message_response import LlmMessageResponse
from llm.models.llm_usage_response import LlmUsageResponse
from llm.providers.groq.client import GroqClient
from llm.providers.ollama.client import OllamaClient

logger = logging.getLogger(__name__)


class OrchestrationService:
    def __init__(self):
        self.provider = OllamaClient()
        self.conversation_repository = ConversationRepository()

    def orchestrate(
            self,
            user_prompt: Message,
            conversation_id: str,
            additional_messages: List[Message]
    ) -> List[Message]:

        messages: List[Message] = (
            self.conversation_repository
            .get_history(conversation_id)
        )

        messages.extend(additional_messages)
        messages.append(user_prompt)

        for message in messages:
            logger.info("Chat message: %s", message)

        # TODO: Replace provider implementation with configurable LLM provider.
        response: LlmChatResponse = self.provider.chat(
            messages,
            ai_lab_constants.NEMOTRON_3_NANO
        )

        response_messages: List[Message] = []

        # Save user prompt
        self.conversation_repository.save_detail(
            conversation_id,
            ConversationDetail(
                LlmMessageResponse(
                    role=user_prompt.role,
                    content=user_prompt.content,
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

        # Save assistant responses
        for index, message in enumerate(response.messages):
            response_messages.append(
                Message(
                    role=None,
                    content=message.content,
                    reason=message.reasoning
                )
            )

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