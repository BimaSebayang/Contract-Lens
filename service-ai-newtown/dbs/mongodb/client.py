import os

from dotenv import load_dotenv
from pymongo import MongoClient


load_dotenv()

class MongoDbClient:

    def __init__(self):
        self.client = MongoClient(
            host=os.getenv("MONGODB_HOST"),
            port=int(os.getenv("MONGODB_PORT")),
            username=os.getenv("MONGODB_USERNAME"),
            password=os.getenv("MONGODB_PASSWORD"),
            authSource=os.getenv("MONGODB_AUTHENTICATION_DATABASE")
        )

        self.database = self.client[
            os.getenv("MONGODB_DATABASE")
        ]