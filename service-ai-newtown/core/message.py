from commons.enums.role import Role

class Message:

    def __init__(self, role: Role, content: str):
        self.role = role
        self.content = content

