--
-- File generated with SQLiteStudio v3.3.3 on ven. juil. 15 14:11:32 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Achat
DROP TABLE IF EXISTS Achat;

CREATE TABLE Achat (
    id             BIGINT   NOT NULL,
    creeLe         DATETIME NOT NULL,
    paye           BOOLEAN  NOT NULL,
    totale         INTEGER  NOT NULL,
    fournisseur_id BIGINT   NOT NULL,
    vendeur_id     BIGINT   REFERENCES Vendeur (id),
    PRIMARY KEY (
        id
    )
);


-- Table: Categorie
DROP TABLE IF EXISTS Categorie;

CREATE TABLE Categorie (
    id          BIGINT        NOT NULL,
    creeLe      DATETIME      NOT NULL,
    joursAlerte INTEGER,
    marge       INTEGER       NOT NULL,
    nom         VARCHAR (255) NOT NULL,
    PRIMARY KEY (
        id
    ),
    UNIQUE (
        nom
    )
);

INSERT INTO Categorie (id, creeLe, joursAlerte, marge, nom) VALUES (1, 1657882467056, 25, 15, 'TOTTI');

-- Table: Commande
DROP TABLE IF EXISTS Commande;

CREATE TABLE Commande (
    id         BIGINT        NOT NULL,
    adr        VARCHAR (255),
    client     VARCHAR (255) NOT NULL,
    creeLe     DATETIME      NOT NULL,
    num        VARCHAR (255),
    paye       BOOLEAN       NOT NULL,
    vendeur_id BIGINT        NOT NULL
                             REFERENCES Vendeur (id),
    PRIMARY KEY (
        id
    )
);


-- Table: Fournisseur
DROP TABLE IF EXISTS Fournisseur;

CREATE TABLE Fournisseur (
    id     BIGINT        NOT NULL,
    adr    VARCHAR (255),
    creeLe DATETIME      NOT NULL,
    nom    VARCHAR (255) NOT NULL,
    num1   VARCHAR (255),
    num2   VARCHAR (255),
    prenom VARCHAR (255),
    PRIMARY KEY (
        id
    )
);


-- Table: hibernate_sequence
DROP TABLE IF EXISTS hibernate_sequence;

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);

INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);
INSERT INTO hibernate_sequence (next_val) VALUES (2);

-- Table: LigneAchat
DROP TABLE IF EXISTS LigneAchat;

CREATE TABLE LigneAchat (
    id              BIGINT        NOT NULL,
    creeLe          DATETIME      NOT NULL,
    datePeremption  DATE,
    prixAchatTotale INTEGER       NOT NULL,
    prixAchatUnite  INTEGER       NOT NULL,
    prixVenteTotale INTEGER       NOT NULL,
    prixVenteUnite  INTEGER       NOT NULL,
    qtyTotale       INTEGER       NOT NULL,
    qtyUnite        INTEGER       NOT NULL,
    remarque        VARCHAR (255),
    achat_id        BIGINT        NOT NULL
                                  REFERENCES Achat (id) ON DELETE CASCADE,
    produit_id      BIGINT        NOT NULL
                                  REFERENCES Produit (id) ON DELETE CASCADE,
    PRIMARY KEY (
        id
    )
);


-- Table: LigneCommande
DROP TABLE IF EXISTS LigneCommande;

CREATE TABLE LigneCommande (
    id          BIGINT        NOT NULL,
    creeLe      DATETIME      NOT NULL,
    produit     VARCHAR (255) NOT NULL,
    qty         INTEGER       NOT NULL,
    remarque    VARCHAR (255),
    commande_id BIGINT        NOT NULL
                              REFERENCES Commande (id) ON DELETE CASCADE,
    PRIMARY KEY (
        id
    ),
    UNIQUE (
        produit
    )
);


-- Table: LigneVente
DROP TABLE IF EXISTS LigneVente;

CREATE TABLE LigneVente (
    id              BIGINT   NOT NULL,
    creeLe          DATETIME NOT NULL,
    prixVenteTotale INTEGER  NOT NULL,
    prixVenteUnite  INTEGER  NOT NULL,
    qty             INTEGER  NOT NULL,
    produit_id      BIGINT   NOT NULL
                             REFERENCES Produit (id),
    vente_id        BIGINT   NOT NULL
                             REFERENCES Vente (id) ON DELETE CASCADE,
    PRIMARY KEY (
        id
    )
);


-- Table: Produit
DROP TABLE IF EXISTS Produit;

CREATE TABLE Produit (
    id              BIGINT        NOT NULL,
    codeBarre       VARCHAR (255) UNIQUE,
    creeLe          DATETIME      NOT NULL,
    datePeremption  DATE,
    designation     VARCHAR (255) NOT NULL,
    estPerissable   BOOLEAN       NOT NULL,
    prixAchatTotale INTEGER       NOT NULL,
    prixAchatUnite  INTEGER       NOT NULL,
    prixVenteTotale INTEGER       NOT NULL,
    prixVenteUnite  INTEGER       NOT NULL,
    qtyAlerte       INTEGER,
    qtyTotale       INTEGER       NOT NULL,
    qtyUnite        INTEGER       NOT NULL,
    systemMeasure   VARCHAR (255) NOT NULL,
    categorie_id    BIGINT        NOT NULL
                                  REFERENCES Categorie (id),
    PRIMARY KEY (
        id
    ),
    UNIQUE (
        designation
    )
);


-- Table: Vendeur
DROP TABLE IF EXISTS Vendeur;

CREATE TABLE Vendeur (
    id       BIGINT        NOT NULL,
    creeLe   DATETIME      NOT NULL,
    num      VARCHAR (255),
    password VARCHAR (255) NOT NULL,
    role     VARCHAR (255) NOT NULL,
    username VARCHAR (255) UNIQUE,
    PRIMARY KEY (
        id
    )
);


-- Table: Vente
DROP TABLE IF EXISTS Vente;

CREATE TABLE Vente (
    id         BIGINT        NOT NULL,
    client     VARCHAR (255),
    creeLe     DATETIME      NOT NULL,
    totale     INTEGER       NOT NULL,
    vendeur_id BIGINT        NOT NULL
                             REFERENCES Vendeur (id),
    PRIMARY KEY (
        id
    )
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
