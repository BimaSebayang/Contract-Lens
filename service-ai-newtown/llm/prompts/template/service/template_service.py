from typing import Optional

from db.postgres.client import SessionLocal
from db.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt
from db.postgres.repositories.contractlens_ai_prompt_repository import (
    ContractLensAiPromptRepository,
)


class TemplateService:

    def get_template(
            self,
            prompt_key: str
    ) -> Optional[str]:

        with SessionLocal() as session:

            repository = ContractLensAiPromptRepository(
                session
            )

            prompt: Optional[ContractLensAiPrompt] = (
                repository.find_active_by_prompt_key_and_type(
                    prompt_key=prompt_key,
                    prompt_type="TEMPLATE_PROMPT"
                )
            )

            if prompt is None:
                return None

            print(
                f"get_template results [{prompt_key}] : "
                f"{prompt.content}"
            )

            return prompt.content