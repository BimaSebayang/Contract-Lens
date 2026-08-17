import tiktoken

from core.token_analyzer import (
    TokenAnalyzeRequest,
    TokenAnalyzeResponse
)


class TokenAnalyzerService:

    def __init__(self):
        self.encoding = tiktoken.get_encoding("cl100k_base")

    def analyze(
            self,
            request: TokenAnalyzeRequest
    ) -> TokenAnalyzeResponse:

        tokens = self.encoding.encode(request.content)

        return TokenAnalyzeResponse(
            total_characters=len(request.content),
            total_tokens=len(tokens)
        )