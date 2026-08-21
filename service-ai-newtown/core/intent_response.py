from typing import List

from pydantic import BaseModel


class IntentResponse(BaseModel):
    intent: str = ""
    other_clara_response: List[str] = []
    example_user_message: List[str] = []
    context: str = ""
