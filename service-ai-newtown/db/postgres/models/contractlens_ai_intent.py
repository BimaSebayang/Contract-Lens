from datetime import datetime

from sqlalchemy import Boolean, DateTime, Integer, String, Text
from sqlalchemy.orm import Mapped, mapped_column

from db.postgres.client import Base


class ContractLensAiIntent(Base):

    __tablename__ = "contractlens_ai_intent"

    id: Mapped[int] = mapped_column(
        Integer,
        primary_key=True,
        autoincrement=True
    )

    intent_code: Mapped[str] = mapped_column(
        String(100),
        nullable=False
    )

    route: Mapped[str] = mapped_column(
        String(255),
        nullable=False
    )

    description: Mapped[str] = mapped_column(
        Text,
        nullable=False
    )

    version: Mapped[int] = mapped_column(
        Integer,
        nullable=False
    )

    priority: Mapped[int] = mapped_column(
        Integer,
        nullable=False
    )

    is_active: Mapped[bool] = mapped_column(
        Boolean,
        nullable=False
    )

    created_by: Mapped[str | None] = mapped_column(
        String(100),
        nullable=True
    )

    updated_by: Mapped[str | None] = mapped_column(
        String(100),
        nullable=True
    )

    created_at: Mapped[datetime] = mapped_column(
        DateTime,
        nullable=False
    )

    updated_at: Mapped[datetime] = mapped_column(
        DateTime,
        nullable=False
    )