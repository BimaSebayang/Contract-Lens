CONTRACT_ANALYSIS_TEMPLATE = """
Identifikasi dan ekstrak informasi API dari request yang diberikan user.

<Response>

User Intent:
READ_API_CONTRACT

HTTP Method:
{method}

URL:
{url}

Path:
{path}

Query Parameters:
{query}

Headers:
{headers}

Request Body:
{body}

</Response>

Gunakan hanya informasi yang terdapat pada request yang diberikan user.

Jangan mengarang informasi yang tidak tersedia.

Jika informasi tertentu tidak terdapat pada request,
gunakan:

"NOT_AVAILABLE"
"""