from pydantic import BaseModel


class SystemContext(BaseModel):
    context_key: str
    context_name: str
    content: str
    category: str

    enabled: bool

    created_by: str
    updated_by: str
    created_at: str
    updated_at: str