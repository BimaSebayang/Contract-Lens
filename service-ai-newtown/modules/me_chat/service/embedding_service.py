from sentence_transformers import SentenceTransformer

from core.example_synonymous_user_message import (
    ExampleSynonymousUserMessage
)


class EmbeddingService:

    def __init__(self):
        self.model = SentenceTransformer(
            "all-MiniLM-L6-v2"
        )

    def create_vectors(
            self,
            messages: list[str]
    ) -> list[ExampleSynonymousUserMessage]:

        vectors = self.model.encode(
            messages
        )

        return [
            ExampleSynonymousUserMessage(
                message=message,
                vector=vector.tolist()
            )
            for message, vector in zip(messages, vectors)
        ]