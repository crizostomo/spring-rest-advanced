set foreign_key_checks = 0;

lock tables city write, kitchen write, state write, payment write, `group` write, group_permission write, permission write, product write,
restaurant write, restaurant_payment write, restaurant_user_responsible write, `user` write, user_group write, `order` write,
order_item write, product_photo write, oauth_client_details write;

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
delete from restaurant_user_responsible;
delete from `user`;
delete from user_group;
delete from `order`;
delete from order_item;
delete from product_photo;
delete from oauth_client_details;

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
alter table `order` auto_increment = 1;
alter table order_item auto_increment = 1;

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

insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`, address_city_id, address_cep, address_street, address_number, address_neighborhood) values (1, 'Sucao Jundiai', 15, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Main Avenue', '555', 'Downtown');
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`, address_city_id, address_cep, address_street, address_number, address_complement, address_neighborhood) values (2, 'Divino Fogão', 12, 3, utc_timestamp, utc_timestamp, true, true, 1, '13200-555', 'Ozanan Avenue', '400', 'In the Shopping Mall', 'Downtown');
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`) values (3, 'O_Matuto', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`) values (4, 'Outback', 12, 3, utc_timestamp, utc_timestamp, true, true);

insert into payment (id, description, update_date) values (1, 'debit', utc_timestamp);
insert into payment (id, description, update_date) values (2, 'cash', utc_timestamp);
insert into payment (id, description, update_date) values (3, 'credit', utc_timestamp);

insert into `group` (id, name) values (1, 'Gold');
insert into `group` (id, name) values (2, 'Silver');
insert into `group` (id, name) values (3, 'Bronze');
insert into `group` (id, name) values (4, 'Iron');

insert into permission (id, name, description) values (1, 'EDIT_KITCHENS', 'It allows to edit kitchens');
insert into permission (id, name, description) values (2, 'EDIT_PAYMENTS', 'It allows to create or edit payments');
insert into permission (id, name, description) values (3, 'EDIT_CITIES', 'It allows to create or edit cities');
insert into permission (id, name, description) values (4, 'EDIT_STATES', 'It allows to create or edit states');
insert into permission (id, name, description) values (5, 'CONSULT_USERS_GROUPS_PERMISSIONS', 'It allows to consult users');
insert into permission (id, name, description) values (6, 'EDIT_USERS_GROUPS_PERMISSIONS', 'It allows to create or edit users');
insert into permission (id, name, description) values (7, 'EDIT_RESTAURANTS', 'It allows to create, edit or manage restaurants');
insert into permission (id, name, description) values (8, 'CONSULT_ORDERS', 'It allows to consult orders');
insert into permission (id, name, description) values (9, 'MANAGE_ORDERS', 'It allows to manage orders');
insert into permission (id, name, description) values (10, 'GENERATE_REPORTS', 'It allows to manage reports');

insert into restaurant_payment (restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

-- Add permissions in the Gold Group
insert into group_permission (group_id, permission_id)
select 1, id from permission;

-- Add permissions in the Silver Group
insert into group_permission (group_id, permission_id)
select 2, id from permission where name like 'CONSULT_%';

insert into group_permission (group_id, permission_id) values (2, 10);

-- Add permissions in the Bronze Group
insert into group_permission (group_id, permission_id)
select 3, id from permission where name like 'CONSULT_%';

-- Add permissions in the Iron Group
insert into group_permission (group_id, permission_id)
select 4, id from permission where name like '%_RESTAURANTS' or name like '%_PRODUCTS';

insert into product (id, name, description, price, active, restaurant_id) values (1, 'Mix de Frutas', 'Contém 4 Frutas', 10, TRUE, 1);
insert into product (id, name, description, price, active, restaurant_id) values (2, 'Matutinho', 'Feijao, Arroz, Contra File e Batata Frita', 26, TRUE, 2);
insert into product (id, name, description, price, active, restaurant_id) values (3, 'Costelinha', 'Costelinha de Porco Defumada', 85, TRUE, 3);
insert into product (id, name, description, price, active, restaurant_id) values (4, 'Portuguesa', 'Pizza Portuguesa', 75, FALSE, 1);
insert into product (id, name, description, price, active, restaurant_id) values (5, 'Lasanha', 'Lasagna Bolonhesa', 42, TRUE, 2);
insert into product (id, name, description, price, active, restaurant_id) values (6, 'Churrasco completo', 'Mix de churrasco com até 5 carnes diferentes', 75, TRUE, 3);

insert into `user` (id, name, email, password, record_date) values
(1, 'Roronoa Zoro', 'zoro.sword@onepice.com', '$2a$12$/F/zubzseTAGzrC2Y3eFwunTpwcTZe5GOc9oTce/nM3MhTsgB9r/u', utc_timestamp),
(2, 'Hashibira Inosuke', 'inosuke.hashibira@d-slayer.com', '$2a$12$/F/zubzseTAGzrC2Y3eFwunTpwcTZe5GOc9oTce/nM3MhTsgB9r/u', utc_timestamp),
(3, 'Vegeta', 'vegeta.ssj@dragon-ball.com', '$2a$12$/F/zubzseTAGzrC2Y3eFwunTpwcTZe5GOc9oTce/nM3MhTsgB9r/u', utc_timestamp),
(4, 'Diogo', 'test@gmail.com', '$2a$12$/F/zubzseTAGzrC2Y3eFwunTpwcTZe5GOc9oTce/nM3MhTsgB9r/u', utc_timestamp);

insert into user_group (user_id, group_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into restaurant_user_responsible (restaurant_id, user_id) values (1, 2), (2, 3);

insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, subtotal, delivery, total)
values (1, '92f00a93-f0aa-4a1c-b172-e0198d60d1d8', 1, 4, 1, 1, '38400-000', 'Floriano Peixoto Street', '500', 'Apartment 801', 'Brazil',
        'CREATED', utc_timestamp, 298.90, 10, 308.90);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(1, 1, 1, 1, 78.9, 78.9, null),
(2, 1, 2, 2, 110, 220, 'Less spicy, please');


insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, subtotal, delivery, total)
values (2, uuid(), 4, 1, 2, 1, '38400-111', 'Acre Street', '300', 'House 2', 'Downtown',
        'CREATED', utc_timestamp, 79, 0, 79);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(3, 2, 6, 1, 79, 79, 'Well done');


insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, subtotal, delivery, total)
values (3, uuid(), 3, 1, 2, 1, '38400-222', 'Sao Paulo Street', '322', 'Near the Mall', 'Beach Park',
        'CREATED', utc_timestamp, 89, 12, 99);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(4, 2, 4, 1, 89, 99, 'Rare');

insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, confirmation_date, delivery_date, subtotal, delivery, total)
values (4, uuid(), 3, 1, 2, 1, '38400-222', 'Rio de Janeiro Street', '122', null, 'Santana Park',
        'DELIVERED', '2022-10-01 20:34:04', '2022-10-01 20:35:14', '2022-10-01 21:14:17', 89, 12, 99);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(5, 3, 4, 1, 89, 99, 'Extra French Fries');

insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, confirmation_date, delivery_date, subtotal, delivery, total)
values (5, uuid(), 3, 1, 2, 1, '38345-231', 'Amapa Avenue', '532', null, 'Beatriz Square',
        'DELIVERED', '2022-10-02 22:34:04', '2022-10-02 22:35:14', '2022-10-02 22:59:37', 65, 15, 129);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(6, 3, 2, 2, 89, 129, 'Extra sauce');

insert into `order` (id, code, restaurant_id, user_client_id, payment_id, address_city_id, address_cep,
                    address_street, address_number, address_complement, address_neighborhood,
	                status, creation_date, confirmation_date, delivery_date, subtotal, delivery, total)
values (6, uuid(), 3, 1, 2, 1, '18405-321', 'Santa Catarina Road', '222', null, 'Catarina Park',
        'DELIVERED', '2022-10-03 21:14:04', '2022-10-03 21:15:24', '2022-10-03 21:45:47', 79, 10, 149);

insert into order_item (id, order_id, product_id, quantity, unit_price, total, observation) values
(7, 2, 1, 2, 79, 149, 'Extra Bacon');

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'beverage-web', null, '$2a$12$CKD6mNvI74MOLsNAKA4e1.KvPEC2kNo0OyYuEpreRzoXJ3lDVfK8e',
  'READ,WRITE', 'password,authorization_code', 'http://localhost:8080,http://localhost:8080/swagger-ui/oauth2-redirect.html', null,
  60 * 60 * 6, 60 * 24 * 60 * 60, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'beverage-analytics', null, '$2a$12$CKD6mNvI74MOLsNAKA4e1.KvPEC2kNo0OyYuEpreRzoXJ3lDVfK8e',
  'READ,WRITE', 'authorization_code', 'http://www.beverage-analytics.local:8082', null,
  null, null, false
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'invoice', null, '$2a$12$CKD6mNvI74MOLsNAKA4e1.KvPEC2kNo0OyYuEpreRzoXJ3lDVfK8e',
  'READ,WRITE', 'client_credentials', null, 'CONSULT_ORDERS,GENERATE_REPORTS',
  null, null, null
);

unlock tables;