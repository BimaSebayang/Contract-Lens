from abc import ABC, abstractmethod

from core.message import Message
from llm.models.llm_chat_response import LlmChatResponse


class LLMClient(ABC):

    @abstractmethod
    def chat(
            self,
            memory_message: list[Message],
            model:str
    ) -> LlmChatResponse:
        pass