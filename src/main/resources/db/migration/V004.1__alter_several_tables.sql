alter table group_permission add constraint fk_group_permission_permission
foreign key (permission_id) references permission (id);

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
