from commons.enums.role import Role

class Message:

    def __init__(self, role: Role | None, content: str):
        self.role = role
        self.content = content

    def __str__(self):
        return f"Message(role={self.role}, content='{self.content}')"
