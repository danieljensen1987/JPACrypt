drop table Users;

create table Users(
id varchar(32) primary key,
password varchar(64) not null,
-- role varchar(32) references Roles(rolename)
rolle varchar(32) not null
);