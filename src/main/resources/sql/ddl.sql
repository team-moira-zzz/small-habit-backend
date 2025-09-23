CREATE TABLE user
(
    id            VARCHAR(36) PRIMARY KEY NOT NULL,
    email         VARCHAR(255) UNIQUE     NOT NULL,
    password      VARCHAR(255),
    name          VARCHAR(100)            NOT NULL,
    nickname      VARCHAR(100) UNIQUE     NOT NULL,
    phone         VARCHAR(100) UNIQUE     NOT NULL,
    role          ENUM('ADMIN', 'USER')   NOT NULL DEFAULT 'USER',
    status        ENUM('ACTIVE', 'BANNED', 'DELETED') NOT NULL DEFAULT 'ACTIVE',
    rtk           TEXT,
    last_login_at TIMESTAMP,
    created_at    TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_login_history
(
    id          VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id     VARCHAR(36)             NOT NULL,
    ip_address  VARCHAR(45)             NOT NULL,
    user_agent  VARCHAR(255)            NOT NULL,
    device_id   VARCHAR(255),
    login_at    TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE account_book_group
(
    id            VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id       VARCHAR(36)             NOT NULL,
    name          VARCHAR(100)            NOT NULL,
    code          VARCHAR(10) UNIQUE      NOT NULL,
    created_at    TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE account_book_group_user
(
    group_id      VARCHAR(36)             NOT NULL,
    user_id       VARCHAR(36)             NOT NULL,
    joined_at     TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    left_at       TIMESTAMP,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES account_book_group (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
