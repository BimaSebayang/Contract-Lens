CREATE TABLE gateway_route
(
    id           UUID PRIMARY KEY,

    token        VARCHAR(255) NOT NULL UNIQUE,

    target_url   TEXT NOT NULL,

    description  VARCHAR(255),

    active       BOOLEAN NOT NULL DEFAULT TRUE,

    created_by   VARCHAR(255) NOT NULL,

    updated_by   VARCHAR(255) NOT NULL,

    created_at   TIMESTAMP NOT NULL,

    updated_at   TIMESTAMP NOT NULL
);

COMMENT ON TABLE gateway_route IS 'Stores gateway routing configuration';

COMMENT ON COLUMN gateway_route.id IS 'Primary Key';
COMMENT ON COLUMN gateway_route.token IS 'Unique Gateway Token';
COMMENT ON COLUMN gateway_route.target_url IS 'Destination URL';
COMMENT ON COLUMN gateway_route.description IS 'Gateway Route Description';
COMMENT ON COLUMN gateway_route.active IS 'Route Status';
COMMENT ON COLUMN gateway_route.created_by IS 'Created By';
COMMENT ON COLUMN gateway_route.updated_by IS 'Updated By';
COMMENT ON COLUMN gateway_route.created_at IS 'Created Timestamp';
COMMENT ON COLUMN gateway_route.updated_at IS 'Updated Timestamp';