import os

from dotenv import load_dotenv
from openai import OpenAI

from core.message import Message
from llms.models.llm_chat_response import LlmChatResponse
from llms.models.llm_message_response import LlmMessageResponse
from llms.models.llm_usage_response import LlmUsageResponse
from llms.providers.LLM import LLMClient
import time

load_dotenv()


class OllamaClient(LLMClient):

    def __init__(self):
        ollama_api_key = os.getenv("OLLAMA_API_KEY")

        print(ollama_api_key is not None)

        self.client = OpenAI(
            api_key=ollama_api_key,
            base_url="https://ollama.com/v1"
        )

    def chat(
            self,
            memory_message: list[Message],
            model: str
    ) -> LlmChatResponse:

        messages = []

        for mm in memory_message:
            messages.append({
                "role": mm.role.value if mm.role is not None else None,
                "content": mm.content
            })


        start_time = time.perf_counter()

        print(f"Start Send Chat to Ollama {model} | {start_time}")

        response = self.client.chat.completions.create(
            model=model,
            messages=messages
        )

        end_time = time.perf_counter()
        duration = end_time - start_time

        print(f"Finish Send Chat to Ollama {model} | {end_time}")
        print(f"Ollama takes: {duration:.2f} seconds")

        mapped_messages = [
            LlmMessageResponse(
                role=choice.message.role,
                content=choice.message.content,
                reasoning=getattr(
                    choice.message,
                    "reasoning",
                    None
                ),
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
            latency=None,
            model=response.model,
            usage=mapped_usage
        )