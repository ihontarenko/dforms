CREATE TABLE SECURITY_ROLES_PRIVILEGES
(
    ROLE_ID VARCHAR(32) NOT NULL,
    PRIVILEGE_ID VARCHAR(32) NOT NULL,
    FOREIGN KEY (ROLE_ID) REFERENCES SECURITY_ROLES(ID),
    FOREIGN KEY (PRIVILEGE_ID) REFERENCES SECURITY_PRIVILEGES(ID)
);

INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-ADMIN', 'PVL-READ');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-ADMIN', 'PVL-CREATE');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-ADMIN', 'PVL-UPDATE');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-ADMIN', 'PVL-DELETE');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-ADMIN', 'PVL-SYS');

INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-USER', 'PVL-READ');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-USER', 'PVL-CREATE');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-USER', 'PVL-UPDATE');

INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-OAUTH2-USER', 'PVL-READ');
INSERT INTO SECURITY_ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID)
VALUES ('ROLE-OAUTH2-USER', 'PVL-CREATE');