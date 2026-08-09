import logging
from typing import List

from commons import ai_lab_constants
from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from core.message import Message
from core.prompt import Prompt
from db.mongodb.models.conversation import ConversationDetail
from db.mongodb.repositories.conversation_repository import ConversationRepository
from llm.models.llm_chat_response import LlmChatResponse
from llm.models.llm_message_response import LlmMessageResponse
from llm.models.llm_usage_response import LlmUsageResponse
from llm.providers.groq.client import GroqClient

logger = logging.getLogger(__name__)


class ChatMeService:

    def __init__(self):
        self.groq = GroqClient()
        self.conversation_repository = ConversationRepository()

    def last_chat(self, role: Role) -> str:
        prompt = Prompt()

        prompt.add_message(
            Role.USER,
            "This Is Contents"
        )

        prompt.add_message(
            Role.SYSTEM,
            "Yes This Is Contents"
        )

        content = prompt.get_last_message_by_role(role).content

        logger.info(
            "Last chat content: %s",
            content
        )

        return content

    def send_message(
            self,
            content: ChatRequest
    ) -> List[ChatResponse]:

        user_message = Message(
            role=Role.USER,
            content=content.message
        )

        return [
            ChatResponse(
                content=message.content
            )
            for message in self.orchestrate(
                user_message,
                content.conversation_id
            )
        ]

    def orchestrate(
            self,
            user_message: Message,
            conversation_id: str
    ) -> List[Message]:

        messages: List[Message] = (
            self.conversation_repository
            .get_history(conversation_id)
        )

        messages.append(user_message)

        print(f"Chat Messages is: {messages}")

        response: LlmChatResponse = self.groq.chat(
            messages,
            ai_lab_constants.GPT_OSS_120b
        )

        response_messages: List[Message] = []

        self.conversation_repository.save_detail(
            conversation_id,
            ConversationDetail(
                LlmMessageResponse(
                    role=user_message.role,
                    content=user_message.content,
                    reasoning=self.create_user_reasoning(
                        user_message,
                        conversation_id
                    ),
                    finishing_reason=self.create_finishing_user_reasoning(
                        user_message,
                        conversation_id
                    ),
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
                    content=message.content
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

    def create_user_reasoning(
            self,
            user_message: Message,
            conversation_id: str
    ) -> str:
        # Kayaknya bakal ada logic buat reasoning user
        return (
                "user hanya ingin mengatakan "
                + user_message.content
        )

    def create_finishing_user_reasoning(
            self,
            user_message: Message,
            conversation_id: str
    ) -> str:
        # Kayaknya bakal ada logic buat reasoning user
        return (
                "user mungkin akan mengakhiri "
                + user_message.content
        )