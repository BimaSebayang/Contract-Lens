import os

import pika
from dotenv import load_dotenv


load_dotenv()


class RabbitMqClient:

    def __init__(self):
        self.connection = pika.BlockingConnection(
            pika.ConnectionParameters(
                host=os.getenv("RABBITMQ_HOST"),
                port=int(os.getenv("RABBITMQ_PORT")),
                virtual_host=os.getenv("RABBITMQ_VIRTUAL_HOST"),
                credentials=pika.PlainCredentials(
                    username=os.getenv("RABBITMQ_USERNAME"),
                    password=os.getenv("RABBITMQ_PASSWORD")
                )
            )
        )

        self.channel = self.connection.channel()