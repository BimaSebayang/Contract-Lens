from fastapi import APIRouter
from modules.health.service.health_service import HealthService

router = APIRouter(prefix="/v1/health", tags=["health"])


@router.get("/health")
def health()-> str:
    health_service = HealthService()
    return health_service.health_check()
