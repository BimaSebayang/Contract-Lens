from typing import List

from llm.models.llm_message_response import LlmMessageResponse
from llm.models.llm_usage_response import LlmUsageResponse

class ConversationDetail:
    def __init__(self,
                 llm_message_response: LlmMessageResponse,
                 llm_usage_response:LlmUsageResponse):
        self.message_response = llm_message_response
        self.usage_response = llm_usage_response

class Conversation:
    def __init__(self,
                 conversation_id: str,
                 conversation_details: List[ConversationDetail]
                 ):
        self.conversation_id = conversation_id
        self.conversation_details = conversation_details