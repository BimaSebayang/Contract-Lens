import os

from dotenv import load_dotenv
from openai import OpenAI

from core.message import Message
from llms.models.llm_chat_response import LlmChatResponse
from llms.models.llm_message_response import LlmMessageResponse
from llms.models.llm_usage_response import LlmUsageResponse
from llms.providers.LLM import LLMClient

load_dotenv()


class GroqClient(LLMClient):
    def __init__(self):
        groq_api_key = os.getenv("GROQ_API_KEY")
        print(groq_api_key is not None)

        self.client = OpenAI(
                    api_key=groq_api_key,
                    base_url="https://api.groq.com/openai/v1"
                )

    def chat(self, memory_message:list[Message], model:str)->LlmChatResponse:

        messages = []
        for mm in memory_message:
            messages.append({
                "role": mm.role.value if mm.role is not None else None,
                "content": mm.content
            })

        response = self.client.chat.completions.create(
            model=model,
            messages=messages
        )

        mapped_messages = [
            LlmMessageResponse(
                role=choice.message.role,
                content=choice.message.content,
                reasoning=choice.message.reasoning,
                finishing_reason=choice.finish_reason,
                approve=None
            )
            for choice in response.choices
        ]

        mapped_usage = LlmUsageResponse(
            prompt_tokens=response.usage.prompt_tokens,
            completion_tokens=response.usage.completion_tokens,
            total_tokens=response.usage.total_tokens
        )

        return LlmChatResponse(
            messages=mapped_messages,
            latency=response.usage.total_time,
            model=response.model,
            usage=mapped_usage
        )

