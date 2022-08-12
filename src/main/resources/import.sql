insert into kitchen (id, name) values (1, 'Japanese');
insert into kitchen (id, name) values (2, 'Indian');
insert into kitchen (id, name) values (3, 'Brazilian');

insert into state (id, name) values (1, 'Sao Paulo');
insert into state (id, name) values (2, 'Minas Gerais');
insert into state (id, name) values (3, 'Bahia');

insert into city (id, name, state_id) values (1, 'Jundiai', 1);
insert into city (id, name, state_id) values (2, 'Extrema', 2);
insert into city (id, name, state_id) values (3, 'Salvador', 3);

insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, address_city_id, address_cep, address_street, address_number, address_neighborhood) values (1, 'Sucao_Jundiai', 15, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Main Avenue', '555', 'Downtown');
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date) values (2, 'O_Matuto', 0, 1, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date) values (3, 'Outback', 12, 3, utc_timestamp, utc_timestamp);

insert into payment (id, description) values (1, 'debit');
insert into payment (id, description) values (2, 'cash');
insert into payment (id, description) values (3, 'credit');

insert into permission (name, description) values ('admin', "All permissions");
insert into permission (name, description) values ('user', "read permissions");

insert into restaurant_payment (restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);