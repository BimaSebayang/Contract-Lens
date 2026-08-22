from datetime import datetime
from uuid import UUID, uuid4

from pydantic import BaseModel, Field


class ContractLensExample(BaseModel):

    id: UUID = Field(
        default_factory=uuid4
    )

    intent_id: str

    user_prompt: str

    response_llm: str

    is_active: bool = True

    vector: list[float]

    created_by: str

    updated_by: str

    created_at: datetime = Field(
        default_factory=datetime.now
    )

    score: float = 0.0

    updated_at: datetime = Field(
        default_factory=datetime.now
    )