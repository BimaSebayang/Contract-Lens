from commons.ai_lab_exceptions import MessageNotFound
from core.message import Message
from commons.enums.role import Role
from typing import List
from commons.ai_lab_constants import MESSAGE_NOT_FOUND_BY_ROLE


class Prompt:

    def __init__(self):
        self.messages: List[Message] = []

    def add_message(self, role: Role, content: str):
        self.messages.append(Message(role, content))

    def get_last_message_by_role(self, role: Role) -> Message:
        for message in reversed(self.messages):
            if message.role == role:
                return message

        raise MessageNotFound(f"{MESSAGE_NOT_FOUND_BY_ROLE} '{role.value}' role not found.'")