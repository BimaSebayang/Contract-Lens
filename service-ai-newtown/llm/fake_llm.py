from llm.llm import LLM

from core.prompt import Prompt
from core.response import Response

class FakeLLM(LLM):
    def ask(self, prompt: Prompt) -> Response:
        return Response(
            f"You Said : {prompt.messages[-1].content}"
        )