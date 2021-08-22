INSERT INTO USUARIO(nome, email, senha) VALUES('Usuario', 'usuario@alura.com.br', '$2a$10$xwTRU49OYIRgnG8nCjCPMuGSCmjLzzcprsS4B66hVhH7RwKvdgxxy');
INSERT INTO USUARIO(nome, email, senha) VALUES('Admin', 'admin@alura.com.br', '$2a$10$xwTRU49OYIRgnG8nCjCPMuGSCmjLzzcprsS4B66hVhH7RwKvdgxxy');

INSERT INTO PERFIL(id, nome) VALUES(1, 'ROLE_USER');
INSERT INTO PERFIL(id, nome) VALUES(2, 'ROLE_ADMIN');

INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES(1, 1);
INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES(2, 2);

INSERT INTO categorias VALUES (1,'#000000', 'LIVRE');
INSERT INTO videos VALUES (1, 'Primeiro video', 'descricao', 'http://www.site.com.br', 1);