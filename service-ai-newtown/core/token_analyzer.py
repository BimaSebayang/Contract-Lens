from pydantic import BaseModel


class TokenAnalyzeRequest(BaseModel):
    content: str


class TokenAnalyzeResponse(BaseModel):
    total_characters: int
    total_tokens: int