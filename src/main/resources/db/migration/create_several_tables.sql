create table payment (
	id bigint not null auto_increment,
	description varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table `group` (
	id bigint not null auto_increment,
	name varchar(60) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table group_permission (
	group_id bigint not null,
	permission_id bigint not null,

	primary key (group_id, permission_id)
) engine=InnoDB default charset=utf8;

create table permission (
	id bigint not null auto_increment,
	description varchar(60) not null,
	name varchar(100) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table product (
	id bigint not null auto_increment,
	restaurant_id bigint not null,
	name varchar(80) not null,
	description text not null,
	price decimal(10,2) not null,
	active tinyint(1) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (
	id bigint not null auto_increment,
	kitchen_id bigint not null,
	name varchar(80) not null,
	delivery decimal(10,2) not null,
	updated_date datetime not null,
	record_date datetime not null,

	address_city_id bigint,
	address_cep varchar(9),
	address_street varchar(100),
	address_number varchar(20),
	address_complement varchar(60),
	address_neighborhood varchar(60),

	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment (
	restaurant_id bigint not null,
	payment_id bigint not null,

	primary key (restaurant_id, payment_id)
) engine=InnoDB default charset=utf8;

create table `user` (
	id bigint not null auto_increment,
	name varchar(80) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	record_date datetime not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table user_group (
	user_id bigint not null,
	group_id bigint not null,

	primary key (user_id, group_id)
) engine=InnoDB default charset=utf8;


alter table group_permission add constraint fk_group_permission_permission
foreign key (permission_id) references permission (id);

alter table group_permission add constraint fk_group_permission_group
foreign key (group_id) references `group` (id);

alter table product add constraint fk_product_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_kitchen
foreign key (kitchen_id) references kitchen (id);

alter table restaurant add constraint fk_restaurant_city
foreign key (address_city_id) references city (id);

alter table restaurant_payment add constraint fk_rest_payment_payment
foreign key (payment_id) references payment (id);

alter table restaurant_payment add constraint fk_rest_payment_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table user_group add constraint fk_user_group_group
foreign key (`group`) references `group` (id);

alter table user_group add constraint fk_user_group_user
foreign key (user_id) references `user` (id);