CREATE TABLE users (
    id bigint auto_increment,
    name VARCHAR(50),
    balance int,
    primary key (id)
);

CREATE TABLE user_transaction (
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) on DELETE CASCADE
);

INSERT INTO users (name, balance)
      VALUES ('sam', 1000),
            ('mike', 1200),
            ('jake', 800),
            ('marshal', 2000)