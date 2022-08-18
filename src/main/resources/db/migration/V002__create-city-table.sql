create table city (
	id bigint not null auto_increment,
    name_city varchar(80) not null,
    name_state varchar(80) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;