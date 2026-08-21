from typing import Optional

from datetime import datetime
from sqlalchemy import select
from sqlalchemy.orm import Session

from dbs.postgres.models.contractlens_ai_prompt import ContractLensAiPrompt


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

    def initialize_clara_identity(self):
        self.insert_if_not_exists(
            prompt_key="CLARA_IDENTITY",
            prompt_type="SYSTEM_CONTEXT",
            content="""
CLAra adalah AI assistant yang digunakan untuk membantu pengguna berinteraksi dengan ContractLens.
Jangan mengarang, mengganti, atau menggunakan nama AI lain.
Gunakan bahasa Indonesia yang santai, natural, dan profesional.
Respons maksimal 400 karakter dan keluarkan hanya pesan dalam bentuk string.
Gunakan nama "CLAra" ketika merujuk pada diri sendiri.
Jangan gunakan kata "aku", "saya", "kami", atau "kita" untuk merujuk pada CLAra.
Selalu panggil pengguna sebagai "SobatCLAra".
Jangan gunakan kata ganti atau panggilan lain untuk pengguna, termasuk "kamu", "anda", "Anda", "kau", atau "lu".
""".strip()
        )

    def initialize_contractlens_overview(self):
        self.insert_if_not_exists(
            prompt_key="CONTRACTLENS_OVERVIEW",
            prompt_type="SYSTEM_CONTEXT",
            content="""
ContractLens adalah platform untuk memantau, menganalisis, dan mendeteksi perubahan pada kontrak API.
ContractLens membantu pengguna mengidentifikasi perubahan API, compatibility, dan potensi breaking changes.
""".strip()
        )

    def initialize_contractlens_intent_task(self):
        self.insert_if_not_exists(
            prompt_key="INTENTS_TASK",
            prompt_type="SYSTEM_CONTEXT",
            content="""
Tugas utama adalah menentukan tepat satu intent berdasarkan maksud utama pesan pengguna.
Evaluasi seluruh intent yang tersedia berdasarkan:
1. Maksud utama dan konteks pesan pengguna.
2. Required Context dan Exclude Context dari setiap intent.
Pilih hanya satu intent dengan tingkat kesesuaian tertinggi yang memenuhi threshold.
Jangan memilih intent hanya berdasarkan kata, frasa, atau topik yang muncul dalam pesan jika maksud utama pengguna lebih sesuai dengan intent lain.
Jika tidak ada intent yang memenuhi Required Context, melanggar Exclude Context, atau tidak memiliki tingkat kesesuaian yang memenuhi threshold, pilih UNKNOWN.
Jangan menghasilkan respons kepada pengguna. Hanya lakukan klasifikasi intent sesuai dengan Response Strategy dan Response Format yang ditentukan.
Jika hasil klasifikasi adalah UNKNOWN, gunakan format respons berikut:
{
    "intent": "UNKNOWN"
}
""".strip()
        )



    def insert_if_not_exists(
            self,
            prompt_key: str,
            prompt_type: str,
            content: str
    ):

        existing = self.session.query(
            ContractLensAiPrompt
        ).filter(
            ContractLensAiPrompt.prompt_key == prompt_key,
            ContractLensAiPrompt.is_active.is_(True)
        ).first()

        if existing is not None:
            return

        now = datetime.now()

        prompt = ContractLensAiPrompt(
            prompt_key=prompt_key,
            prompt_type=prompt_type,
            content=content,
            version=1,
            is_active=True,
            created_by="SYSTEM",
            updated_by="SYSTEM",
            created_at=now,
            updated_at=now
        )

        self.session.add(prompt)
        self.session.commit()