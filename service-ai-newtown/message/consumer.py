import json

from db.mongodb.repositories.conversation_repository import ConversationRepository
from message.client import RabbitMqClient


class ConversationConsumer:

    QUEUE = "ai_lab.conversation.save"

    EXCHANGE = "ai_lab.exchange"
    ROUTING_KEY = "conversation.save"

    def __init__(self):
        self.rabbit = RabbitMqClient()
        self.repository = ConversationRepository()

        self.rabbit.channel.exchange_declare(
            exchange=self.EXCHANGE,
            exchange_type="direct",
            durable=True
        )

        self.rabbit.channel.queue_declare(
            queue=self.QUEUE,
            durable=True
        )

        self.rabbit.channel.queue_bind(
            exchange=self.EXCHANGE,
            queue=self.QUEUE,
            routing_key=self.ROUTING_KEY
        )

    def start(self):

        self.rabbit.channel.basic_consume(
            queue=self.QUEUE,
            on_message_callback=self._consume,
            auto_ack=False
        )

        print(
            f"Conversation consumer listening on queue: {self.QUEUE}"
        )

        self.rabbit.channel.start_consuming()

    def _consume(
            self,
            channel,
            method,
            properties,
            body
    ):
        try:
            message = json.loads(body)

            conversation_id = message["conversation_id"]
            detail = message["detail"]

            self.repository.save_detail(
                conversation_id,
                detail
            )

            channel.basic_ack(
                delivery_tag=method.delivery_tag
            )

        except Exception as exception:
            print(
                f"Failed to consume conversation event: {exception}"
            )

            channel.basic_nack(
                delivery_tag=method.delivery_tag,
                requeue=True
            )