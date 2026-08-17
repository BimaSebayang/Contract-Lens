from typing import Any, Literal
from pydantic import BaseModel


class ContractAnalysisResponse(BaseModel):

    user_intent: str
    http_method: Literal["GET", "POST", "PUT", "PATCH", "DELETE"]
    url: str
    path: str

    query_parameters: dict[str, Any]
    headers: dict[str, str]

    request_body: Any | None
    response_body: Any | None