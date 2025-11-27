CREATE TABLE simple.SIMPLEOBJECT (
    id BIGINT NOT NULL,
    LASTCHECKEDIN DATE,
    name VARCHAR(40) NOT NULL,
    NOTES VARCHAR(4000),
    version BIGINT NOT NULL,
    attachment_bytes BYTEA,
    attachment_mimeType VARCHAR(255),
    attachment_name VARCHAR(255),
    PRIMARY KEY (id)
);

ALTER TABLE simple.SIMPLEOBJECT ADD CONSTRAINT SimpleObject__name__UNQ UNIQUE (name);

CREATE TABLE causewayExtSecman.ApplicationPermission (
    ID BIGINT NOT NULL,
    FEATUREFQN VARCHAR(255) NOT NULL,
    FEATURESORT VARCHAR(255) NOT NULL,
    MODE VARCHAR(255) NOT NULL,
    RULE VARCHAR(255) NOT NULL,
    VERSION BIGINT,
    roleId BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE causewayExtSecman.ApplicationRole (
    ID BIGINT NOT NULL,
    DESCRIPTION VARCHAR(254),
    NAME VARCHAR(120) NOT NULL,
    VERSION BIGINT,
    PRIMARY KEY (ID)
);

CREATE TABLE causewayExtSecman.ApplicationTenancy (
    PATH VARCHAR(255) NOT NULL,
    NAME VARCHAR(120) NOT NULL,
    VERSION BIGINT,
    parentPath VARCHAR(255),
    PRIMARY KEY (PATH)
);

CREATE TABLE causewayExtSecman.ApplicationUser (
    ID BIGINT NOT NULL,
    ACCOUNTTYPE VARCHAR(255) NOT NULL,
    ATPATH VARCHAR(255),
    EMAILADDRESS VARCHAR(120),
    ENCRYPTEDPASSWORD VARCHAR(255),
    FAMILYNAME VARCHAR(120),
    FAXNUMBER VARCHAR(120),
    GIVENNAME VARCHAR(120),
    KNOWNAS VARCHAR(120),
    LANGUAGE VARCHAR(255),
    NUMBERFORMAT VARCHAR(255),
    PHONENUMBER VARCHAR(120),
    STATUS VARCHAR(255) NOT NULL,
    TIMEFORMAT VARCHAR(255),
    USERNAME VARCHAR(120) NOT NULL,
    VERSION BIGINT,
    PRIMARY KEY (ID)
);

CREATE TABLE causewayExtSecman.ApplicationUserRoles (
    roleId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    PRIMARY KEY (roleId, userId)
);

ALTER TABLE causewayExtSecman.ApplicationPermission
    ADD CONSTRAINT ApplicationPermission_role_feature_rule__UNQ UNIQUE (roleId, featureSort, featureFqn, rule);

ALTER TABLE causewayExtSecman.ApplicationRole
    ADD CONSTRAINT ApplicationRole__name__UNQ UNIQUE (name);

ALTER TABLE causewayExtSecman.ApplicationTenancy
    ADD CONSTRAINT ApplicationTenancy__name__UNQ UNIQUE (name);

ALTER TABLE causewayExtSecman.ApplicationUser
    ADD CONSTRAINT ApplicationUser__username__UNQ UNIQUE (username);

ALTER TABLE causewayExtSecman.ApplicationPermission
    ADD CONSTRAINT FK_ApplicationPermission_roleId FOREIGN KEY (roleId)
        REFERENCES causewayExtSecman.ApplicationRole (ID);

ALTER TABLE causewayExtSecman.ApplicationTenancy
    ADD CONSTRAINT FK_ApplicationTenancy_parentPath FOREIGN KEY (parentPath)
        REFERENCES causewayExtSecman.ApplicationTenancy (PATH);

ALTER TABLE causewayExtSecman.ApplicationUserRoles
    ADD CONSTRAINT FK_ApplicationUserRoles_roleId FOREIGN KEY (roleId)
        REFERENCES causewayExtSecman.ApplicationRole (ID);

ALTER TABLE causewayExtSecman.ApplicationUserRoles
    ADD CONSTRAINT FK_ApplicationUserRoles_userId FOREIGN KEY (userId)
        REFERENCES causewayExtSecman.ApplicationUser (ID);

CREATE TABLE public.SEQUENCE (
    SEQ_NAME VARCHAR(50) NOT NULL,
    SEQ_COUNT DECIMAL(38),
    PRIMARY KEY (SEQ_NAME)
);

INSERT INTO public.SEQUENCE(SEQ_NAME, SEQ_COUNT) VALUES ('SEQ_GEN', 0);
