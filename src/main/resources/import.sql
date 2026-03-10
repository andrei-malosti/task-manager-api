INSERT INTO user (name, email, password,created_at) VALUES ('Guilherme','guilherme@email.com','123456',NOW());
INSERT INTO user (name, email, password,created_at) VALUES ('Admin','admin@taskmanager.com','admin123',NOW());

INSERT INTO task (title, description, status,created_at, due_date, user_id) VALUES ('Estudar para prova','Estudar para prova de ciencia','PENDING',NOW(),'2026-12-31 23:59:50', 1);
INSERT INTO task (title, description, status,created_at, due_date, user_id) VALUES ('Realizar tarefa de casa','Finalizar tarefa de casa de matematica','IN_PROGRESS',NOW(),'2026-03-15 10:00:00', 1);
INSERT INTO task (title, description, status,created_at, due_date, user_id) VALUES ('Alimentar o gato', 'Colocar ração para o gat','COMPLETED',NOW(),'2026-03-10 18:00:00', 2);