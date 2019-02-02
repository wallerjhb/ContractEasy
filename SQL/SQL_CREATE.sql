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
	package int,
	primary key (usid)
);

create table notices (
	id int not null auto_increment, 
	status int,
	contractid int,
	reference varchar(255),
	description varchar(255),
    nature varchar(255),
	datesent date,
	content varchar(255),
	primary key (id)
);

create table alerts (
	id int not null auto_increment,
	status int,
	contractid int,
	nature varchar(255),
	duedate date,
    senddate date,
    resolved boolean,
	primary key (id)
);

create table files (
	id int not null auto_increment,
	status int,
	contractid int,
	name varchar(255),
	location varchar(255),
	primary key (id)
);

create table packages (
	id int not null auto_increment,
	name varchar(255),
	max int,
	price int,
	primary key (id)
);

create table companydet (
	user int,
	name varchar(255),
	contact varchar(255),
	email varchar(255),
	address varchar(255),
	primary key (user)
);

create table bankdet (
	user int,
	account varchar(255),
	branch varchar(255),
	name varchar(255),
	primary key (user)
);