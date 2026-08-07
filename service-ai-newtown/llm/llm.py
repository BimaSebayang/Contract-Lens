from abc import ABC, abstractmethod
from core.prompt import Prompt
from core.response import Response

class LLM(ABC):
    @abstractmethod
    def ask(self, prompt: Prompt) -> Response:
        pass