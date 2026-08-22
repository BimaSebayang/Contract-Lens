import os
from uuid import uuid4
from datetime import datetime as DateTime

from qdrant_client.models import (
    Distance,
    Filter,
    FieldCondition,
    MatchValue,
    PointStruct,
    VectorParams
)

from dbs.qdrants.client import QdrantClientFactory
from dbs.qdrants.models.contract_lens_example import (
    ContractLensExample
)
from dbs.mongodb.models.intent import UserMessageExample


class ContractLensExampleRepository:

    COLLECTION_NAME = os.getenv("QDRANT_INTENT_COLLECTION")
    VECTOR_SIZE = os.getenv("QDRANT_VECTOR_SIZE")

    def __init__(self):

        self.client = (
            QdrantClientFactory()
            .get_client()
        )

        self._initialize_collection()


    def _initialize_collection(
            self
    ) -> None:

        if self.client.collection_exists(
                self.COLLECTION_NAME
        ):
            return

        self.client.create_collection(
            collection_name=self.COLLECTION_NAME,
            vectors_config=VectorParams(
                size=self.VECTOR_SIZE,
                distance=Distance.COSINE
            )
        )

    def save(
            self,
            intent_id: str,
            user_prompt: UserMessageExample,
            response_llm: str,
            is_active: bool,
            created_by: str,
            updated_by: str
    ) -> None:

        current_time = DateTime.now().isoformat()
        self.client.upsert(
            collection_name=self.COLLECTION_NAME,
            points=[
                PointStruct(
                    id=str(uuid4()),
                    vector=user_prompt.vector,
                    payload={
                        "intent_id": intent_id,
                        "user_prompt": user_prompt.message,
                        "response_llm": response_llm[0],
                        "is_active": is_active,
                        "created_by": created_by,
                        "updated_by": updated_by,
                        "created_at": current_time,
                        "updated_at": current_time
                    }
                )
            ]
        )

    def teach_user_message_examples(
            self,
            intent_id: str,
            clara_response: str,
            user_message_examples: list[UserMessageExample]
    ):

       for user_prompt in user_message_examples:
           self.save(
               intent_id=intent_id,
               user_prompt=user_prompt,
               response_llm=clara_response,
               is_active=True,
               created_by='SYSTEM',
               updated_by='SYSTEM',
           )


    def search_determenistic_wording(
            self,
            user_prompt: UserMessageExample,
            threshold: float
    ) -> list[ContractLensExample]:


        collection_count = self.client.count(
            collection_name=self.COLLECTION_NAME,
            exact=True
        )

        if collection_count.count == 0:
           return []

        response = self.client.query_points_groups(
            collection_name=self.COLLECTION_NAME,
            query=user_prompt.vector,
            group_by="intent_id",
            group_size=1,
            limit=collection_count.count,
            score_threshold=threshold,
            query_filter=Filter(
                must=[
                    FieldCondition(
                        key="is_active",
                        match=MatchValue(
                            value=True
                        )
                    )
                ]
            ),
            with_payload=True
        )

        return [
            ContractLensExample(
                id=result.id,
                vector=[],
                intent_id=result.payload["intent_id"],
                user_prompt=result.payload["user_prompt"],
                response_llm=result.payload["response_llm"],
                is_active=result.payload["is_active"],
                created_by=result.payload["created_by"],
                updated_by=result.payload["updated_by"],
                created_at=result.payload.get(
                    "created_at"
                ),
                updated_at=result.payload.get(
                    "updated_at"
                ),
                score=result.score
            )
            for group in response.groups
            for result in group.hits
        ]