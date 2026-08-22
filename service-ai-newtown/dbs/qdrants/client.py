from qdrant_client import QdrantClient

from commons.configs.qdrant_config import QdrantConfig


class QdrantClientFactory:

    def __init__(self):

        self.client = QdrantClient(
            host=QdrantConfig.HOST,
            port=QdrantConfig.PORT
        )

    def get_client(self) -> QdrantClient:

        return self.client