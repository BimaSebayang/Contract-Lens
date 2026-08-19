class LlmUsageResponse:
    def __init__(self,completion_tokens:int, prompt_tokens:int,total_tokens:int):
        self.completion_tokens = completion_tokens
        self.prompt_tokens = prompt_tokens
        self.total_tokens = total_tokens

    def __str__(self):
        return (
        f"Usage("
        f"completion_tokens={self.completion_tokens}, "
        f"prompt_tokens={self.prompt_tokens}, "
        f"total_tokens={self.total_tokens}"
        f")"
    )