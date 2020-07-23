DROP TABLE IF EXISTS preference;
DROP TABLE IF EXISTS rental;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS bookcase;

CREATE TABLE account
(
    account_id    BIGINT AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,
    github_token  VARCHAR(256) NOT NULL,
    admin_account BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (account_id)
);

CREATE TABLE bookcase
(
    bookcase_id SMALLINT AUTO_INCREMENT,
    name        VARCHAR(64)  NOT NULL,
    location    VARCHAR(128) NOT NULL,
    PRIMARY KEY (bookcase_id)
);

CREATE TABLE category
(
    category_id SMALLINT AUTO_INCREMENT,
    title       VARCHAR(128) NOT NULL,
    bookcase_id SMALLINT,
    PRIMARY KEY (category_id),
    FOREIGN KEY (bookcase_id) REFERENCES bookcase (bookcase_id)
);

CREATE TABLE book
(
    book_id          BIGINT AUTO_INCREMENT,
    title            VARCHAR(256) NOT NULL,
    description      VARCHAR(2048),
    author           VARCHAR(128),
    publisher        VARCHAR(128),
    publication_date DATE,
    total_quantity   SMALLINT     NOT NULL,
    current_quantity SMALLINT,
    preference_count INT DEFAULT 0,
    category_id      SMALLINT,
    PRIMARY KEY (book_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);


CREATE TABLE rental
(
    rental_id        BIGINT AUTO_INCREMENT,
    rented_date_time DATETIME DEFAULT NOW(),
    begin_date       DATE,
    return_date      DATE,
    account_id       BIGINT,
    book_id          BIGINT,
    PRIMARY KEY (rental_id),
    FOREIGN KEY (account_id) REFERENCES account (account_id),
    FOREIGN KEY (book_id) REFERENCES book (book_id)
);

CREATE TABLE preference
(
    preference_id BIGINT AUTO_INCREMENT,
    account_id    BIGINT,
    book_id       BIGINT,
    PRIMARY KEY (preference_id),
    FOREIGN KEY (account_id) REFERENCES account (account_id),
    FOREIGN KEY (book_id) REFERENCES book (book_id)
);
