CONTRACT_ANALYSIS_TEMPLATE = """
Identifikasi dan ekstrak informasi API dari request yang diberikan user.

Hasilkan struktur berikut:

{
    "userRequest": {
        "method": "{method}",
        "url": "{url}",
        "path": "{path}",
        "queryParameters": {query},
        "headers": {headers},
        "requestBody": {request_body}
    },

    "userIntent": "{user_intent}",

    "contractLensResponse": {contract_lens_response}
}

ATURAN:

1. userRequest hanya berisi informasi yang diperoleh
   dari request user.

2. userIntent harus menggunakan hasil dari Intent Detection.

3. contractLensResponse harus berisi response yang diperoleh
   dari API ContractLens yang dipanggil berdasarkan intent.

4. Jangan mengubah atau mengarang contractLensResponse.

5. Jangan menambahkan informasi yang tidak terdapat
   dalam response ContractLens.

6. Jika API ContractLens tidak memberikan data yang diperlukan,
   gunakan:

"NOT_AVAILABLE"

7. Semua field pada userRequest wajib ditampilkan.

8. Jika informasi userRequest tidak tersedia, gunakan:

"NOT_AVAILABLE"

9. Output harus berupa JSON yang valid.

10. Tujuan Kita menyampaikan apa yang sudah diterima
"""