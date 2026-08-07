from enum import Enum


class Role(str, Enum):

    USER = "USER"
    ASSISTANT = "ASSISTANT"
    SYSTEM = "SYSTEM"

    @property
    def llm_value(self) -> str:
        return {
            Role.USER: "user",
            Role.ASSISTANT: "assistant",
            Role.SYSTEM: "system"
        }[self]

    @property
    def display_name(self) -> str:
        return {
            Role.USER: "User",
            Role.ASSISTANT: "Assistant",
            Role.SYSTEM: "System"
        }[self]

    @property
    def description(self) -> str:
        return {
            Role.USER: "Human Message",
            Role.ASSISTANT: "AI Response",
            Role.SYSTEM: "System Instruction"
        }[self]

    @property
    def order(self) -> int:
        return {
            Role.SYSTEM: 0,
            Role.USER: 1,
            Role.ASSISTANT: 2
        }[self]

