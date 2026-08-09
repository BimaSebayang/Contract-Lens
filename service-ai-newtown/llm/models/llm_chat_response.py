from typing import List

from llm.models.llm_message_response import LlmMessageResponse
from llm.models.llm_usage_response import LlmUsageResponse


class LlmChatResponse:
    def __init__(self, messages:List[LlmMessageResponse], latency: float | int, model:str, usage:LlmUsageResponse):
        self.messages = messages
        self.latency = latency
        self.model = model
        self.usage = usage

    def __str__(self):
        messages = ", ".join(str(message) for message in self.messages)

        return (
        f"ChatResponse("
        f"messages=[{messages}], "
        f"latency={self.latency}, "
        f"model='{self.model}', "
        f"usage={self.usage}"
        f")"
    )

    def __repr__(self):
        return str(self)