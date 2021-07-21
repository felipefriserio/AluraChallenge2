DROP TABLE IF EXISTS videos;

CREATE TABLE videos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    url VARCHAR(255) DEFAULT NULL
);


INSERT INTO videos
VALUES (1, 'Titulo', 'descricao', 'http://link.com.br');