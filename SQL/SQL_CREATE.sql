create database contracteasy;

create table contracts (
	id int not null auto_increment, 
	userid int,
	status int, 
	reference varchar(255),
	clientref varchar(255),
	type varchar(255),
	counterparty varchar(255),
	start date,
	termination date,
	escalation date,
	renewal date,
	noticeperioddays int,
	description varchar(255),
	primary key (id)
);

create table users (
	usid int not null auto_increment, 
    username varchar(255), 
    password varchar(255), 
    status int, 
	lastlogin timestamp,
	primary key (usid)
);
