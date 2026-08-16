from typing import Optional

from sqlalchemy import select
from sqlalchemy.orm import Session

from db.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt


class ContractLensAiPromptRepository:

    def __init__(self, session: Session):
        self.session = session

    def find_by_prompt_key_and_version(
            self,
            prompt_key: str,
            version: int
    ) -> Optional[ContractLensAiPrompt]:

        stmt = (
            select(ContractLensAiPrompt)
            .where(
                ContractLensAiPrompt.prompt_key == prompt_key,
                ContractLensAiPrompt.version == version
            )
        )

        return self.session.scalar(stmt)

    def find_active_by_prompt_key(
            self,
            prompt_key: str
    ) -> Optional[ContractLensAiPrompt]:

        stmt = (
            select(ContractLensAiPrompt)
            .where(
                ContractLensAiPrompt.prompt_key == prompt_key,
                ContractLensAiPrompt.is_active.is_(True)
            )
            .order_by(
                ContractLensAiPrompt.version.desc()
            )
        )

        return self.session.scalars(stmt).first()

    def find_active_by_prompt_key_and_type(
            self,
            prompt_key: str,
            prompt_type: str
    ) -> Optional[ContractLensAiPrompt]:

        stmt = (
            select(ContractLensAiPrompt)
            .where(
                ContractLensAiPrompt.prompt_key == prompt_key,
                ContractLensAiPrompt.prompt_type == prompt_type,
                ContractLensAiPrompt.is_active.is_(True)
            )
            .order_by(
                ContractLensAiPrompt.version.desc()
            )
        )

        return self.session.scalars(stmt).first()