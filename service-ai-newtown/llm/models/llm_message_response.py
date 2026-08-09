from commons.enums.role import Role


class LlmMessageResponse:
    def __init__(self, role:Role, content:str,reasoning:str,finishing_reason:str, approve:bool|None):
        self.role = role
        self.content = content
        self.reasoning = reasoning
        self.finishing_reason = finishing_reason
        self.approve = approve

    def __str__(self):
        return (
            f"Message("
            f"role={self.role}, "
            f"content='{self.content}', "
            f"reasoning='{self.reasoning}', "
            f"finishing_reason='{self.finishing_reason}'"
            f")"
        )