from fastapi import FastAPI

from modules.health.apis.health_controller import router as health_router
from modules.me_chat.apis.chat_me import router as fake_chat_router
from modules.token.apis.token_me import router as token_me_router

app = FastAPI()

app.include_router(health_router)
app.include_router(fake_chat_router)
app.include_router(token_me_router)