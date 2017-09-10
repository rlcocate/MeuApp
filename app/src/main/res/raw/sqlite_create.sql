CREATE TABLE Login (
    id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    user    VARCHAR(9)  NULL,
    fblogin VARCHAR(50) NULL, -- e-mail / telefone
    pass    VARCHAR(15) NULL
);

CREATE TABLE Region (
    id          INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    initials    CHAR(2)     NOT NULL,
    name        VARCHAR(10) NOT NULL -- ZN, ZS, ZL, ZO
);

INSERT INTO Region (initials, name) VALUES ('ZN', 'North Zone');
INSERT INTO Region (initials, name) VALUES ('ZS', 'South Zone');
INSERT INTO Region (initials, name) VALUES ('ZL', 'East Zone');
INSERT INTO Region (initials, name) VALUES ('ZO', 'West Zone');

CREATE TABLE Beer (
    id          INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR(10) NOT NULL
);

INSERT INTO Beer (name) VALUES ('Brahma');
INSERT INTO Beer (name) VALUES ('Skol');
INSERT INTO Beer (name) VALUES ('Itaipava');
INSERT INTO Beer (name) VALUES ('Antarctica');
INSERT INTO Beer (name) VALUES ('Original');
INSERT INTO Beer (name) VALUES ('Budweiser');
INSERT INTO Beer (name) VALUES ('Heineken');
INSERT INTO Beer (name) VALUES ('Eisenbahn');

CREATE TABLE Store (

    id          INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR(30) NULL,   -- nome do estabelecimento
    regionId    INTEGER     NULL,   -- região
    beerId      INTEGER     NULL,   -- cerveja
    beerValue   DECIMAL     NULL,   -- valor

    FOREIGN KEY(regionId) REFERENCES Region (id),
    FOREIGN KEY(beerId) REFERENCES Beer (id)
);