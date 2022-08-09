insert into kitchen (id, name) values (1, 'Japanese');
insert into kitchen (id, name) values (2, 'Indian');
insert into kitchen (id, name) values (3, 'Brazilian');

insert into restaurant (name, delivery, kitchen_id) values ('Sucao_Jundiai', 15, 1);
insert into restaurant (name, delivery, kitchen_id) values ('O_Matuto', 0, 1);
insert into restaurant (name, delivery, kitchen_id) values ('Outback', 12, 3);

insert into payment (id, description) values (1, 'debit');
insert into payment (id, description) values (2, 'cash');
insert into payment (id, description) values (3, 'credit');

insert into permission (name, description) values ('admin', "All permissions");
insert into permission (name, description) values ('user', "read permissions");

insert into state (id, name) values (1, 'Sao Paulo');
insert into state (id, name) values (2, 'Minas Gerais');
insert into state (id, name) values (3, 'Bahia');

insert into city (name, state_id) values ('Jundiai', 1);
insert into city (name, state_id) values ('Extrema', 2);
insert into city (name, state_id) values ('Salvador', 3);

insert into restaurant_payment (restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);