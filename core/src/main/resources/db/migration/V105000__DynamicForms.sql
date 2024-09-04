CREATE TABLE DF_FORMS (
    ID VARCHAR(32) PRIMARY KEY,
    OWNER_ID VARCHAR(32) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT,
    STATUS ENUM('ACTIVE', 'INACTIVE', 'DELETED') NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE DF_FORM_CONFIG (
    ID VARCHAR(32) PRIMARY KEY,
    FORM_ID VARCHAR(32) NOT NULL,
    CONFIG_NAME VARCHAR(255) NOT NULL,
    CONFIG_VALUE TEXT NOT NULL,
    FOREIGN KEY (FORM_ID) REFERENCES DF_FORMS(ID)
);

CREATE TABLE DF_FORM_FIELDS (
    ID VARCHAR(32) PRIMARY KEY,
    USAGE_TYPE ENUM('VIRTUAL', 'EMBEDDABLE', 'STANDALONE') NOT NULL,
    ELEMENT_TYPE ENUM('TEXT', 'NUMBER', 'SELECT', 'RADIO', 'CHECKBOX', 'TEXTAREA', 'DATE', 'EMAIL', 'URL', 'VIRTUAL') NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    LABEL VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT,
    STATUS ENUM('ACTIVE', 'INACTIVE', 'DELETED') NOT NULL
);

CREATE TABLE DF_FIELD_SELF_MAPPING (
    P_ID VARCHAR(32) NOT NULL,
    C_ID VARCHAR(32) NOT NULL,
    SEQUENCE_ORDER INT,
    PRIMARY KEY (P_ID, C_ID),
    FOREIGN KEY (P_ID) REFERENCES DF_FORM_FIELDS(ID),
    FOREIGN KEY (C_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_FIELD_MAPPING (
    FORM_ID VARCHAR(32) NOT NULL,
    FIELD_ID VARCHAR(32) NOT NULL,
    FOREIGN KEY (FORM_ID) REFERENCES DF_FORMS(ID),
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_FIELD_DEFAULT_VALUES (
    ID VARCHAR(32) PRIMARY KEY,
    FORM_ID VARCHAR(32) NOT NULL,
    FIELD_ID VARCHAR(32) NOT NULL,
    DEFAULT_VALUE TEXT NOT NULL,
    FOREIGN KEY (FORM_ID) REFERENCES DF_FORMS(ID),
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_FIELD_OPTIONS (
    ID VARCHAR(32) PRIMARY KEY,
    FIELD_ID VARCHAR(32) NOT NULL,
    OPTION_VALUE VARCHAR(255) NOT NULL,
    OPTION_LABEL VARCHAR(255) NOT NULL,
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_FIELD_ATTRIBUTES (
    ID VARCHAR(32) PRIMARY KEY,
    FIELD_ID VARCHAR(32) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    `VALUE` TEXT NOT NULL,
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_FIELD_CONFIG (
    ID VARCHAR(32) PRIMARY KEY,
    FIELD_ID VARCHAR(32) NOT NULL,
    CONFIG_NAME VARCHAR(255) NOT NULL,
    CONFIG_VALUE TEXT NOT NULL,
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);

CREATE TABLE DF_FORM_ENTRIES (
    ID VARCHAR(32) PRIMARY KEY,
    FORM_ID VARCHAR(32) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FORM_ID) REFERENCES DF_FORMS(ID)
);

CREATE TABLE DF_FORM_FIELD_ENTRIES (
    ID VARCHAR(32) PRIMARY KEY,
    FORM_ENTRY_ID VARCHAR(32) NOT NULL,
    FIELD_ID VARCHAR(32) NOT NULL,
    `VALUE` TEXT NOT NULL,
    FOREIGN KEY (FORM_ENTRY_ID) REFERENCES DF_FORM_ENTRIES(ID),
    FOREIGN KEY (FIELD_ID) REFERENCES DF_FORM_FIELDS(ID)
);