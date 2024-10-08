CREATE TABLE SECURITY_USERS_ROLES
(
    USER_ID VARCHAR(32) NOT NULL,
    ROLE_ID VARCHAR(32) NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES SECURITY_USERS(ID),
    FOREIGN KEY (ROLE_ID) REFERENCES SECURITY_ROLES(ID)
);

INSERT INTO SECURITY_USERS_ROLES (USER_ID, ROLE_ID)
VALUES ('ADMIN-1', 'ROLE-ADMIN');
