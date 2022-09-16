CREATE TABLE users (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(50),
    balance INT,
    PRIMARY KEY (id)
);

CREATE TABLE user_transaction(
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT,
    amount INT,
    transaction_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO users (name, balance) VALUES ('sam', 1000), ('mike', 1200), ('jake', 800), ('marshal', 2000);