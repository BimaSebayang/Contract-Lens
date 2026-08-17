from fastapi import APIRouter

from core.token_analyzer import (
    TokenAnalyzeRequest,
    TokenAnalyzeResponse
)
from modules.token.service.token_analyzer_service import TokenAnalyzerService


router = APIRouter(
    prefix="/v1/token-analyzer",
    tags=["Token Analyzer"]
)

service = TokenAnalyzerService()


@router.post(
    "/analyze",
    response_model=TokenAnalyzeResponse
)
def analyze_token(
        request: TokenAnalyzeRequest
):
    return service.analyze(request)