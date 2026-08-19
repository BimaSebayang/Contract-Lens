INTENT_DETECTION_TEMPLATE = """
Identifikasi intent utama dari pesan user.


Hasilkan struktur JSON berikut:

{
    "intent": "INTENT_NAME",
    "confidence": 0.0,
    "reason": "Alasan singkat pemilihan intent"
}

ATURAN:

1. Pilih tepat satu intent.

2. Gunakan tujuan utama user untuk menentukan intent.

3. Confidence harus berada antara 0.0 sampai 1.0.

4. Reason harus menjelaskan secara singkat
   mengapa pesan user dikategorikan ke intent tersebut.

5. Reason harus berdasarkan informasi yang terdapat
   pada user message.

6. Jangan memberikan chain-of-thought,
   reasoning internal, atau proses berpikir internal.

7. Reason maksimal 1-2 kalimat.

8. Jangan menjawab pertanyaan user.

9. Jika intent tidak dapat ditentukan dengan cukup jelas,
   gunakan:

   "UNKNOWN"

10. Jangan menambahkan field lain.

11. Output harus berupa JSON yang valid.


"""