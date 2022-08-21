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