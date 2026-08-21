from pydantic import BaseModel, Field


class ExampleSynonymousUserMessage(BaseModel):
    message: str
    vector: list[float] = Field(default_factory=list)