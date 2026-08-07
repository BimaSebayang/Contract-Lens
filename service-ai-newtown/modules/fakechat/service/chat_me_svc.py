from commons.enums.role import Role
from core.prompt import Prompt

class ChatMeService:
    def last_chat(self, role : Role)-> str:
        prompt = Prompt()
        prompt.add_message(Role.USER,"This Is Contents")
        prompt.add_message(Role.SYSTEM,"Yes This Is Contents")
        return prompt.get_last_message_by_role(role).content