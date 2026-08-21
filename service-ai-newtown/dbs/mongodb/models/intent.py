from typing import List

from pydantic import BaseModel

class UserMessageExample(BaseModel):
    message: str
    vector: list[float]=[]

class KeywordClassification(BaseModel):
    other_clara_response: List[str]
    user_message_examples: List[UserMessageExample]

class Classification(BaseModel):
    keywords: List[KeywordClassification]
    threshold: float


class Execution(BaseModel):
    required_context: str
    exclude_context: str
    task_context:str


class Response(BaseModel):
    strategy: str
    template: str


class Intent(BaseModel):
    id: str
    name: str

    classification: Classification
    execution: Execution
    response: Response

    enabled: bool
    priority: int

    created_by: str
    updated_by: str
    created_at: str
    updated_at: str