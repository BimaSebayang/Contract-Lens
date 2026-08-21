from fastapi import FastAPI

from modules.health.apis.health_controller import router as health_router
from modules.me_chat.apis.chat_me import router as fake_chat_router
from modules.token.apis.token_me import router as token_me_router
from modules.token.lifes.life_span import lifespan
from modules.me_chat.apis.chat_me_V2 import router as chat_me_v2_router

app = FastAPI( lifespan=lifespan)

app.include_router(health_router)
app.include_router(fake_chat_router)
app.include_router(token_me_router)
app.include_router(chat_me_v2_router)
