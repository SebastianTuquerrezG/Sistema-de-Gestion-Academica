CREATE USER IF NOT EXISTS 'unicauca_eval'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON unicauca.* TO 'unicauca_eval'@'%';
FLUSH PRIVILEGES;
