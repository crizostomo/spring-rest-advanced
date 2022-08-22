set foreign_key_checks = 0;

delete from city;
delete from kitchen;
delete from state;
delete from payment;
delete from `group`;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment;
delete from `user`;
delete from user_group;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment auto_increment = 1;
alter table `group` auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table `user` auto_increment = 1;

insert into kitchen (id, name) values (1, 'Japanese');
insert into kitchen (id, name) values (2, 'Indian');
insert into kitchen (id, name) values (3, 'Brazilian');

insert into state (id, name) values (1, 'Sao Paulo');
insert into state (id, name) values (2, 'Minas Gerais');
insert into state (id, name) values (3, 'Bahia');
insert into state (id, name) values (4, 'Rio de Janeiro');

insert into city (id, name, state_id) values (1, 'Jundiai', 1);
insert into city (id, name, state_id) values (2, 'Extrema', 2);
insert into city (id, name, state_id) values (3, 'Salvador', 3);
insert into city (id, name, state_id) values (4, 'Rio de Janeiro', 4);

insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, address_city_id, address_cep, address_street, address_number, address_neighborhood) values (1, 'Sucao_Jundiai', 15, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Main Avenue', '555', 'Downtown');
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date) values (2, 'O_Matuto', 0, 1, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date) values (3, 'Outback', 12, 3, utc_timestamp, utc_timestamp);

insert into payment (id, description) values (1, 'debit');
insert into payment (id, description) values (2, 'cash');
insert into payment (id, description) values (3, 'credit');

insert into `group` (id, name) values (1, 'Ouro');
insert into `group` (id, name) values (2, 'Prata');
insert into `group` (id, name) values (3, 'Bronze');

insert into permission (name, description) values ('admin', "All permissions");
insert into permission (name, description) values ('user', "read permissions");
insert into permission (name, description) values ('visitor', "read permissions");

insert into restaurant_payment (restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into group_permission (group_id, permission_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into product (id, name, description, price, active, restaurant_id) values (1, 'Mix de Frutas', 'Contém 4 Frutas', 10, TRUE, 1);
insert into product (id, name, description, price, active, restaurant_id) values (2, 'Matutinho', 'Feijao, Arroz, Contra File e Batata Frita', 26, TRUE, 2);
insert into product (id, name, description, price, active, restaurant_id) values (3, 'Costelinha', 'Costelinha de Porco Defumada', 85, TRUE, 3);

