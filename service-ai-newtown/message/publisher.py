import json

import pika

from db.mongodb.models.conversation import ConversationDetail
from message.client import RabbitMqClient


class ConversationPublisher:

    EXCHANGE = "ai_lab.exchange"
    ROUTING_KEY = "conversation.save"

    def __init__(self):
        self.rabbit = RabbitMqClient()

        self.rabbit.channel.exchange_declare(
            exchange=self.EXCHANGE,
            exchange_type="direct",
            durable=True
        )

    def publish(
            self,
            conversation_id: str,
            detail: ConversationDetail
    ):
        message = {
            "conversation_id": conversation_id,
            "detail": {
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
        }

        self.rabbit.channel.basic_publish(
            exchange=self.EXCHANGE,
            routing_key=self.ROUTING_KEY,
            body=json.dumps(message),
            properties=pika.BasicProperties(
                delivery_mode=pika.DeliveryMode.Persistent
            )
        )