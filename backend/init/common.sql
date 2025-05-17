CREATE USER IF NOT EXISTS 'common_user'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON unicauca.* TO 'common_user'@'%';
FLUSH PRIVILEGES;
