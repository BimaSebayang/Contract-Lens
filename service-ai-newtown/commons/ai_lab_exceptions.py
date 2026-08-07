class AILabException(Exception):

    def __init__(self, message: str):
        super().__init__(message)


class MessageNotFound(AILabException):
    def __init__(self, message: str):
        super().__init__(message)