CREATE TABLE SECURITY_USERS
(
    ID       VARCHAR(32) NOT NULL UNIQUE,
    PROVIDER VARCHAR(32),
    EMAIL    VARCHAR(64),
    `NAME`    VARCHAR(64),
    PASSWORD VARCHAR(128),
    ENABLED  BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO SECURITY_USERS (`ID`, PROVIDER, EMAIL, `NAME`, PASSWORD, ENABLED)
VALUES ('ADMIN-1', 'LOCAL', 'admin@localhost', 'ADMIN', '$2a$10$eF9BK9TajRVWGFRxa9y2HuX46Sq8ZchiL0Vts408ing2UVc6rtCtm', 1);