from commons.enums.role import Role
from core.message import Message

from dbs.mongodb.client import MongoDbClient
from dbs.mongodb.models.conversation import (
    Conversation,
    ConversationDetail,
    LlmMessageResponse,
    LlmUsageResponse
)


def _to_conversation(
        document: dict
) -> Conversation:

    conversation_details: list[ConversationDetail] = []

    for detail in document.get(
            "conversation_details",
            []
    ):

        message_data = detail["llm_message_response"]

        message_response = LlmMessageResponse(
            role=Role(message_data["role"]),
            content=message_data["content"],
            reasoning=message_data.get("reasoning"),
            finishing_reason=message_data.get("finishing_reason"),
            approve=message_data.get("approve")
        )

        usage_data = detail["llm_usage_response"]

        usage_response = LlmUsageResponse(
            prompt_tokens=usage_data["prompt_tokens"],
            completion_tokens=usage_data["completion_tokens"],
            total_tokens=usage_data["total_tokens"]
        )

        conversation_details.append(
            ConversationDetail(
                llm_message_response=message_response,
                llm_usage_response=usage_response
            )
        )

    return Conversation(
        conversation_id=document["conversation_id"],
        conversation_details=conversation_details
    )


class ConversationRepository:

    def __init__(self):
        mongo_client = MongoDbClient()

        self.collection = mongo_client.database[
            "conversations_user_document"
        ]

    def save_detail(
            self,
            conversation_id: str,
            detail: ConversationDetail
    ):
        document = {
            "llm_message_response": {
                "role": detail.message_response.role,
                "content": detail.message_response.content,
                "reasoning": detail.message_response.reasoning,
                "finishing_reason": detail.message_response.finishing_reason,
                "approve": detail.message_response.approve
            },
            "llm_usage_response": {
                "prompt_tokens": detail.usage_response.prompt_tokens,
                "completion_tokens": detail.usage_response.completion_tokens,
                "total_tokens": detail.usage_response.total_tokens
            }
        }

        self.collection.update_one(
            {
                "conversation_id": conversation_id
            },
            {
                "$push": {
                    "conversation_details": document
                }
            },
            upsert=True
        )

    def get_history(
            self,
            conversation_id: str
    ) -> list[Message]:

        document = self.collection.find_one(
            {
                "conversation_id": conversation_id
            }
        )

        if document is None:
            return []

        conversation = _to_conversation(document)

        messages: list[Message] = []

        for detail in conversation.conversation_details:

            if detail.message_response.approve is True:
                messages.append(
                    Message(
                        role=detail.message_response.role,
                        content=detail.message_response.content,
                        reason=detail.message_response.reasoning
                    )
                )

        return messages

