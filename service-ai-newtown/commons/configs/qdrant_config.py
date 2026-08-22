import os


class QdrantConfig:

    HOST = os.getenv(
        "QDRANT_HOST",
        "localhost"
    )

    PORT = int(
        os.getenv(
            "QDRANT_PORT",
            "6333"
        )
    )

    INTENT_COLLECTION = os.getenv(
        "QDRANT_INTENT_COLLECTION",
        "contractlens_intent_vectors"
    )

    VECTOR_SIZE = int(
        os.getenv(
            "QDRANT_VECTOR_SIZE",
            "384"
        )
    )