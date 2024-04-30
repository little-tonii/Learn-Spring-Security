CREATE TABLE
    `user` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `username` VARCHAR(255) NOT NULL,
        `password` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `role` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `user_role` (
        `user_id` INT NOT NULL,
        `role_id` INT NOT NULL,
        PRIMARY KEY (user_id, role_id),
        CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (id),
        CONSTRAINT FOREIGN KEY (`role_id`) REFERENCES `role` (id)
    );