create table `order` (
  id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  delivery decimal(10,2) not null,
  total decimal(10,2) not null,

  restaurant_id bigint not null,
  user_client_id bigint not null,
  payment_id bigint not null,

  address_city_id bigint,
  address_cep varchar(9),
  address_street varchar(100),
  address_number varchar(20),
  address_complement varchar(60),
  address_neighborhood varchar(60),

  status varchar(10) not null,
  creation_date datetime not null,
  confirmation_date datetime null,
  cancelled_date datetime null,
  delivery_date datetime null,

  primary key (id),

  constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
  constraint fk_order_user_client foreign key (user_client_id) references `user` (id),
  constraint fk_order_payment foreign key (payment_id) references payment (id)
) engine=InnoDB default charset=utf8;

create table order_item (
  id bigint not null auto_increment,
  quantity smallint(6) not null,
  unit_price decimal(10,2) not null,
  total decimal(10,2) not null,
  observation varchar(255) null,
  order_id bigint not null,
  product_id bigint not null,

  primary key (id),
  unique key uk_item_order_product (order_id, product_id),

  constraint fk_item_order_order foreign key (order_id) references `order` (id),
  constraint fk_item_order_product foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;