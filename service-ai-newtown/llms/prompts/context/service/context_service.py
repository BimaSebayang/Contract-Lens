from typing import Optional

from dbs.postgres.client import SessionLocal
from dbs.postgres.repositories.contractlens_ai_prompt_repository import (
    ContractLensAiPromptRepository,
)


from dbs.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt


class ContextService:

    def get_ai_prompt_architecture(
            self
    ) -> Optional[str]:

        with SessionLocal() as session:

            repository = ContractLensAiPromptRepository(
                session
            )

            prompt : Optional[ContractLensAiPrompt] = repository.find_active_by_prompt_key(
                prompt_key="AI_PROMPT_ARCHITECTURE"
            )

            if prompt is None:
                return None
            return prompt.content