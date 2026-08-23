from bson import ObjectId

from dbs.mongodb.client import MongoDbClient
from dbs.mongodb.models.intent import (
    Intent,
    Classification,
    Execution,
    Response
)
from dbs.mongodb.models.intent import UserMessageExample

# Intent yang harus dibuat
# GREETING_USER
# GLOSSARY_CONTRACTLENS
# REGISTER_API
# LOGIN_CONTRACTLENS
# ANALYZE_API_CONTRACT
# UNKNOWN

class IntentRepository:

    def __init__(self):
        mongo_client = MongoDbClient()
        self.collection = mongo_client.database["contract_lens_intents"]

    def initialize(self):
        self._initialize_greeting()
        self._initialize_glosary()
        self._initialize_onboarding()

    def _initialize_greeting(self):
        intent = Intent(
            id="GREETING_USER",
            name="GREETING_USER",

            classification=Classification(
                keywords=[],
                threshold=0.9
            ),

            execution=Execution(
                required_context=(
                    "Intent ini digunakan ketika tujuan utama pesan pengguna adalah memberikan sapaan, "
                    "memulai percakapan, atau menyapa CLAra tanpa disertai pertanyaan, permintaan, "
                    "atau topik spesifik lain yang menjadi maksud utama pengguna."
                ),
                exclude_context = (
                    "Jangan klasifikasikan sebagai GREETING_USER jika kata sapaan "
                    "digunakan bersamaan dengan pertanyaan, permintaan, instruksi, "
                    "atau konteks lain yang memiliki tujuan utama selain menyapa. "
                    "Jika pengguna menyebutkan topik seperti ContractLens, API, kontrak, "
                    "breaking change, compatibility, analisis, onboarding, token, atau "
                    "topik spesifik lainnya setelah atau bersamaan dengan sapaan, "
                    "anggap sapaan tersebut hanya sebagai pembuka dan bukan maksud utama pengguna."
                ),
                task_context=(f"""
Gunakan bahasa Indonesia yang santai, natural, dan profesional.
Respons maksimal 300 karakter dan keluarkan hanya pesan dalam bentuk string.
Ucapkan Kata Sapaan kepada user lalu tawarkan pilihan bantuan untuk mulai menggunakan ContractLens.
Contoh gaya respons:
"Nice to meet you, SobatCLara! 👋 Aku CLAra, AI Assistant dari ContractLens. Mau mulai dari mana? 👉 Kenalan dulu sama ContractLens 👉 Mulai daftar API 👉 Langsung bahas API"
                    """
                )
            ),

            response=Response(
                strategy="JSON",
                template="""
{
  "intent": "GREETING_USER",
  "other_clara_response": [
    "<alternative_response>"
  ],
  "user_message_examples": [
    "<example_user_message>"
  ]
}
"""
            ),

            enabled=True,
            priority=1,

            created_by="SYSTEM",
            updated_by="SYSTEM",
            created_at="2026-08-19T00:00:00Z",
            updated_at="2026-08-19T00:00:00Z"
        )

        existing = self.collection.find_one({
            "_id": intent.id
        })

        if existing is None:
            document = intent.model_dump()

            document["_id"] = document.pop("id")

            self.collection.insert_one(document)


    def _initialize_glosary(self):
        intent = Intent(
            id="GLOSSARY_CONTRACTLENS",
            name="GLOSSARY_CONTRACTLENS",

            classification=Classification(
                keywords=[],
                threshold=0.9
            ),

            execution=Execution(
                required_context=(
                    "Intent ini digunakan ketika pengguna ingin mengetahui, memahami, "
                    "atau meminta penjelasan mengenai istilah, konsep, fitur, komponen, "
                    "atau terminologi yang berkaitan dengan ContractLens. "
                    "Termasuk pertanyaan tentang ContractLens, API Contract, Contract Snapshot, "
                    "Breaking Change, Compatibility, Request atau Response Contract, "
                    "Gateway, Analyzer, Token, serta konsep lain yang digunakan dalam ContractLens."
                ),
                exclude_context=(
                    "Jangan gunakan intent ini jika pengguna meminta untuk melakukan tindakan, "
                    "menjalankan proses, membuat akun, membuat atau mendapatkan token, "
                    "melakukan konfigurasi, atau memulai onboarding. "
                    "Jangan gunakan intent ini jika pertanyaan tentang istilah atau konsep "
                    "hanya menjadi bagian dari permintaan utama yang lebih spesifik."
                ),
                task_context=f"""
Gunakan bahasa Indonesia yang santai, natural, dan profesional.
Respons maksimal 1000 karakter dan keluarkan hanya pesan dalam bentuk string.

Tugas kamu adalah menjelaskan istilah, konsep, fitur, atau komponen yang
berkaitan dengan ContractLens sesuai dengan pertanyaan pengguna.

ContractLens adalah platform untuk membantu memantau, menangkap, membandingkan,
dan menganalisis API Contract serta mendeteksi perubahan yang dapat memengaruhi
compatibility.

Komponen dan konsep ContractLens yang dapat dijelaskan:

Contoh:

1. ContractLens

ContractLens adalah platform yang digunakan untuk memantau, menangkap,
membandingkan, dan menganalisis perubahan API Contract.

ContractLens terdiri dari beberapa konsep dan komponen utama, yaitu:

- API Contract
- Request Contract
- Response Contract
- Contract Snapshot
- Contract Comparison
- Contract Change
- Breaking Change
- Compatibility
- Compatibility Plan
- API Gateway
- Analyzer
- API Token
- Onboarding

Komponen-komponen tersebut bekerja bersama untuk membantu pengguna memantau
perubahan API dan mengidentifikasi dampaknya terhadap compatibility.

2. API Contract
Struktur atau kesepakatan komunikasi antara client dan API, seperti request,
response, parameter, header, dan struktur data.

Contoh:
User: "API Contract itu apa?"
CLAra: "API Contract adalah kesepakatan struktur komunikasi antara client dan API, seperti format request, response, parameter, header, dan data yang digunakan."

3. Contract Snapshot
Rekaman atau representasi struktur API Contract pada suatu waktu yang digunakan
sebagai acuan untuk perbandingan.

Contoh:
User: "Apa itu Contract Snapshot?"
CLAra: "Contract Snapshot adalah rekaman struktur API pada suatu waktu tertentu. Di ContractLens, snapshot digunakan sebagai acuan untuk membandingkan perubahan contract."

4. Contract Comparison
Proses membandingkan dua atau lebih Contract Snapshot untuk menemukan perbedaan
pada struktur atau karakteristik API.

Contoh:
User: "Apa fungsi Contract Comparison?"
CLAra: "Contract Comparison digunakan untuk membandingkan perubahan antara API Contract sebelumnya dan versi terbaru agar perbedaan pada struktur API dapat terdeteksi."

5. Contract Change
Perubahan yang terjadi pada struktur atau karakteristik API Contract.

Contoh:
User: "Apa yang dimaksud Contract Change?"
CLAra: "Contract Change adalah perubahan pada API Contract, misalnya perubahan field, tipe data, parameter, header, request, atau response."

6. Breaking Change
Perubahan API yang dapat menyebabkan client atau consumer yang menggunakan
contract sebelumnya tidak lagi kompatibel.

Contoh:
User: "Breaking Change itu apa?"
CLAra: "Breaking Change adalah perubahan pada API yang dapat membuat client lama tidak lagi kompatibel. Contohnya menghapus field yang sebelumnya wajib digunakan oleh client."

7. Compatibility
Kemampuan API versi terbaru untuk tetap bekerja dengan client atau consumer
yang menggunakan contract sebelumnya.

Contoh:
User: "Apa itu Compatibility?"
CLAra: "Compatibility adalah kemampuan perubahan API untuk tetap bekerja dengan client yang menggunakan contract sebelumnya tanpa menyebabkan gangguan pada integrasi."

8. Compatibility Plan
Informasi atau strategi untuk membantu menangani perubahan contract dan menjaga
atau meningkatkan compatibility antar versi.

Contoh:
User: "Apa itu Compatibility Plan?"
CLAra: "Compatibility Plan adalah rencana atau informasi yang membantu menentukan cara menangani perubahan API agar dampaknya terhadap client dan integrasi dapat diminimalkan."

9. API Gateway
Komponen yang menerima dan meneruskan request API serta menangkap informasi
transaksi yang diperlukan oleh ContractLens.

Contoh:
User: "Gateway di ContractLens buat apa?"
CLAra: "API Gateway menerima dan meneruskan request API, sekaligus menangkap informasi transaksi yang diperlukan ContractLens untuk membentuk dan menganalisis API Contract."

10. Analyzer
Komponen yang memproses data contract atau transaksi untuk menganalisis struktur
API dan mendeteksi perubahan.

Contoh:
User: "Analyzer itu apa?"
CLAra: "Analyzer adalah komponen yang memproses data transaksi dan contract untuk menganalisis struktur API serta mendeteksi perubahan yang terjadi."

11. Request Contract
Struktur dan aturan data yang dikirim client ke API, termasuk method, parameter,
header, dan request body.

Contoh:
User: "Apa itu Request Contract?"
CLAra: "Request Contract adalah struktur request yang dikirim client ke API, seperti HTTP method, parameter, header, dan request body."

12. Response Contract
Struktur dan aturan data yang dikembalikan API kepada client, termasuk status
code, header, dan response body.

Contoh:
User: "Response Contract itu apa?"
CLAra: "Response Contract adalah struktur respons yang dikembalikan API, seperti status code, header, dan format data pada response body."

13. API Token
Token yang digunakan untuk mengidentifikasi atau menghubungkan API dengan
ContractLens sesuai mekanisme yang tersedia.

Contoh:
User: "API Token itu buat apa?"
CLAra: "API Token digunakan untuk mengidentifikasi atau menghubungkan API dengan ContractLens sehingga aktivitas API dapat dikaitkan dengan konfigurasi yang sesuai."

14. Onboarding
Proses awal untuk mulai menggunakan ContractLens, termasuk pembuatan atau
pengaturan kebutuhan awal seperti API Token.

Contoh:
User: "Onboarding ContractLens itu apa?"
CLAra: "Onboarding adalah proses awal untuk mulai menggunakan ContractLens, termasuk menyiapkan kebutuhan yang diperlukan agar API dapat terhubung dan mulai dipantau."

Jawab hanya berdasarkan istilah atau komponen yang ditanyakan pengguna.
Jangan menjelaskan semua komponen sekaligus jika pengguna hanya menanyakan
satu istilah.

Jika relevan, jelaskan fungsi komponen tersebut dan hubungannya dengan
ContractLens atau komponen lainnya.

"""
            ),

            response=Response(
                strategy="JSON",
                template="""
{
  "intent": "GLOSSARY_CONTRACTLENS",
  "other_clara_response": [
    "<alternative_response>"
  ],
  "user_message_examples": [
    "<example_user_message>"
  ]
}
"""
            ),

            enabled=True,
            priority=1,

            created_by="SYSTEM",
            updated_by="SYSTEM",
            created_at="2026-08-19T00:00:00Z",
            updated_at="2026-08-19T00:00:00Z"
        )

        existing = self.collection.find_one({
            "_id": intent.id
        })

        if existing is None:
            document = intent.model_dump()

            document["_id"] = document.pop("id")

            self.collection.insert_one(document)

    def _initialize_onboarding(self):
        intent = Intent(
            id="ONBOARDING_CONTRACTLENS",
            name="ONBOARDING_CONTRACTLENS",

            classification=Classification(
                keywords=[],
                threshold=0.9
            ),

            execution=Execution(
                required_context=(
                    "Intent ini digunakan ketika pengguna ingin memulai penggunaan ContractLens, "
                    "melakukan onboarding, mendaftar, membuat atau mendapatkan API Token, "
                    "menghubungkan API, atau menanyakan langkah awal yang diperlukan untuk "
                    "mulai menggunakan ContractLens."
                ),
                exclude_context=(
                    "Jangan gunakan intent ini jika pengguna hanya meminta penjelasan, definisi, "
                    "atau informasi mengenai ContractLens, API Token, onboarding, API Contract, "
                    "atau komponen lainnya tanpa bermaksud melakukan proses atau tindakan. "
                    "Jangan gunakan intent ini jika permintaan utama pengguna adalah melakukan "
                    "analisis, membandingkan contract, mendeteksi perubahan, atau membahas "
                    "masalah teknis API yang sudah terhubung."
                ),
                task_context=f"""
Gunakan bahasa Indonesia yang santai, natural, dan profesional.
Respons maksimal 300 karakter dan keluarkan hanya pesan dalam bentuk string.

Tugas kamu adalah membantu pengguna memulai penggunaan ContractLens dan
mengarahkan pengguna ke proses onboarding yang sesuai.

ContractLens menyediakan proses onboarding untuk mempersiapkan pengguna agar
dapat mulai menghubungkan dan menggunakan API bersama ContractLens.

Proses onboarding dapat mencakup:

1. Memulai Penggunaan ContractLens
Digunakan ketika pengguna ingin mulai menggunakan ContractLens tetapi belum
menentukan langkah yang ingin dilakukan.

Contoh:
User: "Saya mau mulai pakai ContractLens"
CLAra: "Siap! 👋 Kita bisa mulai dari mengenal ContractLens atau langsung menyiapkan API. Kamu mau mulai dari mana?"

2. Registrasi atau Pembuatan Akun
Digunakan ketika pengguna ingin mendaftar atau membuat akun untuk mulai
menggunakan ContractLens.

Contoh:
User: "Saya mau daftar ContractLens"
CLAra: "Siap! Untuk mulai menggunakan ContractLens, kita bisa mulai dari proses registrasi terlebih dahulu. Setelah itu kamu bisa menyiapkan API yang ingin digunakan."

3. Pembuatan atau Mendapatkan API Token
Digunakan ketika pengguna ingin membuat, mendapatkan, atau menyiapkan token
yang diperlukan untuk menghubungkan API dengan ContractLens.

Contoh:
User: "Bagaimana cara membuat API Token?"
CLAra: "Kamu bisa memulai dengan membuat API Token terlebih dahulu. Token ini digunakan untuk menghubungkan dan mengidentifikasi API yang akan digunakan di ContractLens."

4. Menghubungkan atau Menyiapkan API
Digunakan ketika pengguna ingin mulai menghubungkan API atau menyiapkan
integrasi dengan ContractLens.

Contoh:
User: "Saya mau menghubungkan API saya"
CLAra: "Siap! Setelah kebutuhan awal seperti API Token tersedia, kamu bisa melanjutkan ke proses konfigurasi dan menghubungkan API ke ContractLens."

Jika pengguna belum menentukan langkah onboarding yang diinginkan, jangan
langsung mengasumsikan proses yang harus dilakukan. Berikan arahan singkat
dan tawarkan pilihan langkah yang relevan.

Jika pengguna sudah menyebutkan tindakan yang jelas, langsung arahkan ke
proses tersebut.

Contoh:
"Saya mau mulai menggunakan ContractLens"
→ Arahkan pengguna untuk memilih langkah awal.

"Saya mau daftar"
→ Arahkan ke proses registrasi.

"Saya mau membuat API Token"
→ Arahkan ke proses pembuatan token.

"Saya mau menghubungkan API"
→ Arahkan ke proses konfigurasi atau integrasi API.

Jangan memberikan penjelasan glossary yang panjang jika pengguna sebenarnya
ingin melakukan tindakan atau memulai proses.

Jawaban harus singkat, jelas, membantu pengguna mengetahui langkah berikutnya,
dan tidak melebihi 300 karakter.
"""),

            response=Response(
                strategy="JSON",
                template="""
{
  "intent": "ONBOARDING_CONTRACTLENS",
  "other_clara_response": [
    "<alternative_response>"
  ],
  "user_message_examples": [
    "<example_user_message>"
  ]
}
"""
            ),

            enabled=True,
            priority=1,

            created_by="SYSTEM",
            updated_by="SYSTEM",
            created_at="2026-08-19T00:00:00Z",
            updated_at="2026-08-19T00:00:00Z"
        )

        existing = self.collection.find_one({
            "_id": intent.id
        })

        if existing is None:
            document = intent.model_dump()

            document["_id"] = document.pop("id")

            self.collection.insert_one(document)



    def get_by_ids(
            self,
            intent_ids: list[str]
    ) -> list[Intent]:

        query = {
            "enabled": True
        }

        if intent_ids:
            query["_id"] = {
                "$in": [
                    ObjectId(intent_id)
                    for intent_id in intent_ids
                ]
            }

        documents = self.collection.find(query)

        intents = []

        for docu in documents:
            docu["id"] = str(docu.pop("_id"))

            intents.append(
                Intent(**docu)
            )

        return intents

    def get_by_id(
            self,
            intent_id: str
        ) -> Intent | None:

        document = self.collection.find_one({
            "_id": intent_id,
            "enabled": True
        })

        if document is None:
            return None

        document["id"] = document.pop("_id")

        return Intent(**document)

    def teach_user_message_examples(
            self,
            intent_id: str,
            other_clara_response: list[str],
            user_message_examples: list[UserMessageExample]
    ):

        self.collection.find_one_and_update(
        {
            "_id": intent_id,
            "enabled": True
        },
        {
            "$addToSet": {
                "classification.keywords": {
                    "other_clara_response": other_clara_response,
                    "user_message_examples": [
                        example.model_dump(exclude='vector')
                        for example in user_message_examples
                    ]
                }
            }
        },
        return_document=True
    )