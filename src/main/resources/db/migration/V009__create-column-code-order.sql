alter table `order` add code varchar(36) not null after id;
update `order` set code = uuid();
alter table `order` add constraint uk_order_code unique (code);

--alter table `order` drop column code;