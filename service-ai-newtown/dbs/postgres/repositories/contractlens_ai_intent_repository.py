from typing import Optional

from sqlalchemy import select
from sqlalchemy.orm import Session

from dbs.postgres.models.contractlens_ai_intent import ContractLensAiIntent


class ContractLensAiIntentRepository:

    def __init__(self, session: Session):
        self.session = session

    def find_by_intent_code_and_version(
            self,
            intent_code: str,
            version: int
    ) -> Optional[ContractLensAiIntent]:

        stmt = (
            select(ContractLensAiIntent)
            .where(
                ContractLensAiIntent.intent_code == intent_code,
                ContractLensAiIntent.version == version
            )
        )

        return self.session.scalar(stmt)

    def find_active_by_intent_code(
            self,
            intent_code: str
    ) -> Optional[ContractLensAiIntent]:

        stmt = (
            select(ContractLensAiIntent)
            .where(
                ContractLensAiIntent.intent_code == intent_code,
                ContractLensAiIntent.is_active.is_(True)
            )
            .order_by(
                ContractLensAiIntent.version.desc()
            )
        )

        return self.session.scalars(stmt).first()

    def find_all_active(self) -> list[ContractLensAiIntent]:

        stmt = (
            select(ContractLensAiIntent)
            .where(
                ContractLensAiIntent.is_active.is_(True)
            )
            .order_by(
                ContractLensAiIntent.priority.desc()
            )
        )

        return list(self.session.scalars(stmt).all())