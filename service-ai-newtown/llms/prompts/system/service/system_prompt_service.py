from typing import Optional

from dbs.postgres.client import SessionLocal
from dbs.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt
from dbs.postgres.repositories.contractlens_ai_prompt_repository import (
    ContractLensAiPromptRepository,
)


class SystemPromptService:

    def get_system_prompt(
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
                    prompt_type="SYSTEM_PROMPT"
                )
            )

            if prompt is None:
                return None

            print(
                f"get_system_prompt results [{prompt_key}] : "
                f"{prompt.content}"
            )

            return prompt.content