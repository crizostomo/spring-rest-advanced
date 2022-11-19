alter table payment add update_date datetime null;
update payment set update_date = utc_timestamp;
alter table payment modify update_date datetime not null;
