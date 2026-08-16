CREATE TABLE if not exists contractlens_ai_prompt (
                           id BIGSERIAL PRIMARY KEY,

                           prompt_key VARCHAR(150) NOT NULL,
                           prompt_type VARCHAR(50) NOT NULL,
                           content TEXT NOT NULL,

                           version INTEGER NOT NULL,
                           is_active BOOLEAN NOT NULL DEFAULT FALSE,

                           created_by VARCHAR(100),
                           updated_by VARCHAR(100),
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT uk_ai_prompt_key_version
                               UNIQUE (prompt_key, version)
);

CREATE UNIQUE INDEX if not exists uk_ai_prompt_active_version
    ON contractlens_ai_prompt (prompt_key)
    WHERE is_active = TRUE;


CREATE TABLE if not exists contractlens_ai_intent (
                           id BIGSERIAL PRIMARY KEY,

                           intent_code VARCHAR(100) NOT NULL,
                           description TEXT,
                           route VARCHAR(255),

                           priority INTEGER NOT NULL DEFAULT 0,
                           is_active BOOLEAN NOT NULL DEFAULT TRUE,
                           version INTEGER NOT NULL,
                           created_by VARCHAR(100),
                           updated_by VARCHAR(100),
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT uk_ai_intent_code_version
                               UNIQUE (intent_code,version)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_ai_intent_active_version
    ON contractlens_ai_intent (intent_code)
    WHERE is_active = TRUE;