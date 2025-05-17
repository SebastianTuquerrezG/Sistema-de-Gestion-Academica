CREATE USER IF NOT EXISTS 'rubric_user'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON unicauca.* TO 'rubric_user'@'%';
FLUSH PRIVILEGES;