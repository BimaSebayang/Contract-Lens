from contextlib import asynccontextmanager

from fastapi import FastAPI

from core.prompt import Prompt
from dbs.mongodb.repositories.intent_repository import IntentRepository
from dbs.postgres.client import SessionLocal
from dbs.postgres.repositories import ContractLensAiPromptRepository


@asynccontextmanager
async def lifespan(app: FastAPI):
    intent_repository = IntentRepository()
    intent_repository.initialize()


    with SessionLocal() as session:
        prompt = ContractLensAiPromptRepository(session)
        prompt.initialize_clara_identity()
        prompt.initialize_contractlens_overview()
        prompt.initialize_contractlens_intent_task()

    yield



