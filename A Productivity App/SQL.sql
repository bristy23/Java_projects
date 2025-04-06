CREATE DATABASE IF NOT EXISTS todolist;
USE todolist;
------------------------
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL, 
    username VARCHAR(50) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    dob varchar(10) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);
ALTER TABLE USERS
ADD dob varchar(10);
SELECT * FROM users;
------------------------------

CREATE TABLE tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    due_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
SELECT * FROM tasks;
------------------------------
CREATE TABLE login_info (
    login_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

Select users.fullname, login_info.login_time from users 
Join login_info on users.user_id = login_info.user_id
where users.user_id = 9;

SELECT * FROM login_info;
----------------------------------------------

CREATE TABLE IF NOT EXISTS signup_info (
    signup_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    signup_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
SELECT*FROM signup_info;
-----------------------------------------

CREATE TABLE password_reset (
    reset_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    phone VARCHAR(15),
    new_password VARCHAR(255),
    reset_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);
SELECT * FROM password_reset;








