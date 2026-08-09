import logging
from typing import List

from commons import ai_lab_constants
from commons.enums.role import Role
from core.chat_request import ChatRequest
from core.chat_response import ChatResponse
from core.message import Message
from core.prompt import Prompt
from llm.models.llm_chat_response import LlmChatResponse
from llm.providers.groq.client import GroqClient


logger = logging.getLogger(__name__)


class ChatMeService:

    def last_chat(self, role: Role) -> str:
        prompt = Prompt()

        prompt.add_message(Role.USER, "This Is Contents")
        prompt.add_message(Role.SYSTEM, "Yes This Is Contents")

        content = prompt.get_last_message_by_role(role).content

        logger.info("Last chat content: %s", content)

        return content

    def send_message(self, content: ChatRequest) -> List[ChatResponse]:
        user_message = Message(
            role=Role.USER,
            content=content.message
        )

        chatResponse : List[LlmChatResponse] = []

        for cr in self.orchestrate(user_message):
            chatResponse.append(ChatResponse(
                content=cr.content
            ))

        return chatResponse

    def orchestrate(self, user_message: Message) -> List[Message]:
        groq = GroqClient()

        # TODO: Retrieve conversation history from MongoDB
        messages: List[Message] = [
            user_message
        ]

        response: LlmChatResponse = groq.chat(
            messages,
            ai_lab_constants.GPT_OSS_120b
        )

        return [
            Message(
                role=None,
                content=message.content
            )
            for message in response.messages
        ]