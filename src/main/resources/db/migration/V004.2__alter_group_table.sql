alter table group_permission add constraint fk_group_permission_group
foreign key (group_id) references `group` (id);

alter table user_group add constraint fk_user_group_group
foreign key (group_id) references `group` (id);

alter table user_group add constraint fk_user_group_user
foreign key (user_id) references `user` (id);