insert into kitchen (id, name) values (1, 'Japanese');
insert into kitchen (id, name) values (2, 'Indian');

insert into restaurant (name, delivery, kitchen_id) values ('Sucao_Jundiai', 15, 1);
insert into restaurant (name, delivery, kitchen_id) values ('O_Matuto', 10, 1);

insert into payment (id, description) values (1, 'debit');
insert into payment (id, description) values (2, 'cash');

insert into permission (name, description) values ('admin', "All permissions");
insert into permission (name, description) values ('user', "read permissions");

insert into state (id, name) values (1, 'Sao_Paulo');
insert into state (id, name) values (2, 'Minas_Gerais');

insert into city (name, state_id) values ('Jundiai', 1);
insert into city (name, state_id) values ('Extrema', 2);