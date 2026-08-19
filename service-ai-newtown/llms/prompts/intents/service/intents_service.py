from typing import Optional

from dbs.postgres.client import SessionLocal
from dbs.postgres.models.contractlens_ai_intent import ContractLensAiIntent
from dbs.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt
from dbs.postgres.repositories import ContractLensAiIntentRepository
from dbs.postgres.repositories.contractlens_ai_prompt_repository import (
    ContractLensAiPromptRepository,
)


class IntentPromptService:
    def _build_intent_list(
            self,
            intents: list[ContractLensAiIntent]
    ) -> str:

        return "\n\n".join(
            intent.description
            for intent in intents
        )

    def get_intent_detail(
        self,
        template_key: str
    ) -> Optional[str]:
        with SessionLocal() as session:
            intent_repository = ContractLensAiIntentRepository(
                session
            )

        intent: Optional[ContractLensAiIntent] = (
            intent_repository.find_active_by_intent_code(template_key)
        )

        return intent.description if intent else None

    def get_intent_glossary(
            self
    ) -> Optional[str]:

        with SessionLocal() as session:

            repository = ContractLensAiPromptRepository(
                session
            )


            intent_repository = ContractLensAiIntentRepository(
                session
            )

            prompt: Optional[ContractLensAiPrompt] = (
                repository.find_active_by_prompt_key(
                    prompt_key="INTENT_GLOSARY"
                )
            )



            if prompt is None:
                return None


            intents: list[ContractLensAiIntent] = (
                intent_repository.find_all_active()
            )

            intent_list = self._build_intent_list(
                intents
            )

            content = prompt.content.replace(
                "{intent-list-xiii}",
                intent_list
            )

            print(
                f"get_intent_glossary results : {content}"
            )

            return content

    def get_intent_detection_prompt(
            self
    ) -> Optional[str]:

        with SessionLocal() as session:

            repository = ContractLensAiPromptRepository(
                session
            )

            prompt: Optional[ContractLensAiPrompt] = (
                repository.find_active_by_prompt_key(
                    prompt_key="INTENT_DETECTION_PROMPT"
                )
            )

            if prompt is None:
                return None

            return prompt.content