create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint, primary key (id)) engine=InnoDB;
create table groupp (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table groupp_permission (groupp_id bigint not null, permission_id bigint not null) engine=InnoDB;
create table kitchen (id bigint not null auto_increment, name varchar(30) not null, primary key (id)) engine=InnoDB;
create table payment (id bigint not null auto_increment, description varchar(255) not null, primary key (id)) engine=InnoDB;
create table permission (id bigint not null auto_increment, description varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint, primary key (id)) engine=InnoDB;
create table restaurant (id bigint not null auto_increment, address_cep varchar(255), address_complement varchar(255), address_neighborhood varchar(255), address_number varchar(255), address_street varchar(255), delivery decimal(19,2) not null, name varchar(255) not null, record_date datetime not null, updated_date datetime not null, address_city_id bigint, kitchen_id bigint, primary key (id)) engine=InnoDB;
create table restaurant_payment (restaurant_id bigint not null, payment_id bigint not null) engine=InnoDB;
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id);
alter table groupp_permission add constraint FKc9ovp7yhqjed2vkjm2a30iqiy foreign key (permission_id) references permission (id);
alter table groupp_permission add constraint FK4r8ujp83wkphrg9u8gmedg6yl foreign key (groupp_id) references groupp (id);
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id);
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id);
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id);
alter table restaurant_payment add constraint FK1orlck741hv6ova9t9a3hdhd5 foreign key (payment_id) references payment (id);
alter table restaurant_payment add constraint FK2g4321jti3ytjt2i6cesxg49x foreign key (restaurant_id) references restaurant (id);
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
insert into groupp (id, name) values (1, 'Ouro');
insert into groupp (id, name) values (2, 'Prata');
insert into groupp (id, name) values (3, 'Bronze');
insert into permission (name, description) values ('admin', "All permissions");
insert into permission (name, description) values ('user', "read permissions");
insert into permission (name, description) values ('visitor', "read permissions");
insert into restaurant_payment (restaurant_id, payment_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
insert into groupp_permission (groupp_id, permission_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
insert into product (id, name, description, price, active, restaurant_id) values (1, 'Mix de Frutas', 'Contém 4 Frutas', 10, TRUE, 1);
insert into product (id, name, description, price, active, restaurant_id) values (2, 'Matutinho', 'Feijao, Arroz, Contra File e Batata Frita', 26, TRUE, 2);
insert into product (id, name, description, price, active, restaurant_id) values (3, 'Costelinha', 'Costelinha de Porco Defumada', 85, TRUE, 3);
