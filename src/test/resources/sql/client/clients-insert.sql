insert into tb_users (id, username, password, role) values (100, 'ana@email.com', '$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa', 'ROLE_ADMIN');
insert into tb_users (id, username, password, role) values (101, 'bia@email.com', '$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa', 'ROLE_CLIENT');
insert into tb_users (id, username, password, role) values (102, 'bob@email.com', '$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa', 'ROLE_CLIENT');
insert into tb_users (id, username, password, role) values (103, 'tob@email.com', '$2a$12$VQSVztr/M8tcwzcy9jJgFebpqybKpF4FVwM3zilhOAI4yL1iW3rJa', 'ROLE_CLIENT');


insert into tb_clients(id, name, cpf, id_user) values (10, 'Bianca Silva', '17350027044', 101);
insert into tb_clients(id, name, cpf, id_user) values (20, 'Roberto Gomes', '06516269009', 102);

