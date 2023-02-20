create table oauth_code (
  code varchar(255),
  authentication blob

) engine=innodb default charset=utf8;