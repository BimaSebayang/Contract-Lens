import re
from typing import List

from sentence_transformers import SentenceTransformer

from core.chat_response import ChatResponse
from core.example_synonymous_user_message import (
    ExampleSynonymousUserMessage
)
from dbs.mongodb.repositories.intent_repository import IntentRepository


class EmbeddingService:

    def __init__(self):
        self.model = SentenceTransformer(
            "all-MiniLM-L6-v2"
        )

        self.intent_repository = IntentRepository()

    def _normalize_message(
            self,
            message: str
    ) -> str:

        message = re.sub(
            r"[^a-zA-Z0-9\s]",
            "",
            message
        )

        return re.sub(
            r"\s+",
            " ",
            message
        ).strip()

    def create_vector(
            self,
            message: str
    ) -> ExampleSynonymousUserMessage:

        vector = self.model.encode(
            self._normalize_message(message)
        )

        return ExampleSynonymousUserMessage(
            message=message,
            vector=vector.tolist()
        )

    def create_vectors(
            self,
            messages: list[str]
    ) -> list[ExampleSynonymousUserMessage]:

        normalized_messages = [
            self._normalize_message(message)
            for message in messages
        ]

        vectors = self.model.encode(
            normalized_messages
        )

        return [
            ExampleSynonymousUserMessage(
                message=message,
                vector=vector.tolist()
            )
            for message, vector in zip(
                messages,
                vectors
            )
        ]

    def find_similar_intents(
            self,
            user_prompt: str
    ) -> List[ChatResponse]:

        user_vector = self.create_vector(
            user_prompt
        ).vector

        intents = self.intent_repository.get_by_ids(
            None
        )

        results = []

        for intent in intents:
            highest_score = 0.0
            matched_keyword = None
            for keyword in intent.classification.keywords:
                keyword_highest_score = 0.0
                for example in keyword.user_message_examples:
                    score = self._calculate_similarity(
                        user_vector,
                        example.vector
                    )
                    print(
                        f"User: {user_prompt} | "
                        f"Example: {example.message} | "
                        f"Score: {score}"
                    )
                    if score > keyword_highest_score:
                        keyword_highest_score = score
                if (
                        keyword_highest_score
                        >= intent.classification.threshold
                        and keyword_highest_score > highest_score
                ):
                    highest_score = keyword_highest_score
                    matched_keyword = keyword
            if matched_keyword is not None:
                results.append({
                    "intent": intent,
                    "keyword": matched_keyword,
                    "score": highest_score
                })

        sorted_intents = sorted(
            results,
            key=lambda result: result["score"],
            reverse=True
        )

        chat_response = [
            ChatResponse(
                content=(
                    result["keyword"].other_clara_response[0]
                    if result["keyword"].other_clara_response
                    else ""
                ),
                selected_intent=result["intent"].name,
                reason=(
                    f"find_similar_result = "
                    f"{result['score']}"
                ),
                message_context="",
                intent_context=""
            )
            for result in sorted_intents
        ]

        print(
            f"find_similar_result = "
            f"{chat_response}"
        )

        return chat_response

    @staticmethod
    def _calculate_similarity(
            source_vector: list[float],
            target_vector: list[float]
    ) -> float:
        dot_product = sum(
            source * target
            for source, target in zip(
                source_vector,
                target_vector
            )
        )

        source_magnitude = sum(
            value ** 2
            for value in source_vector
        ) ** 0.5

        target_magnitude = sum(
            value ** 2
            for value in target_vector
        ) ** 0.5

        if source_magnitude == 0 or target_magnitude == 0:
            return 0.0

        return dot_product / (
                source_magnitude * target_magnitude
        )