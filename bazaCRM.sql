drop database if exists firma;
create database firma;
use firma;

create table positions(
id_position int(11) primary key not null auto_increment,
position_name varchar(128) not null,
description text not null
);

create table users(
id_user int(11) primary key not null auto_increment,
name varchar(50) not null,
surname varchar(128) not null,
address varchar(255) not null,
zip varchar(6) not null,
place varchar(128) not null,
phone_num int(9) not null UNIQUE,
position_id int(11) references positions.id_position,
login int(11) references login.user_id,
groups int(11) not null
);

create table login(
id_login int(11) primary key not null auto_increment,
user_id int(11) not null UNIQUE,
token varchar(250) not null,
email varchar(128) not null UNIQUE,
password varchar(255) not null
);

create table tasks_history(
id_task_history int(11) primary key not null auto_increment UNIQUE,
start_date DATE not null,
end_date DATE not null,
tasks_id int(11) UNIQUE references tasks.id_task,
planenned_end DATE not null
);

/** NA TYLE PRIORYTETOW ILE BEDA MIELI
create table priorities(
id_priority int(1) primary key not null auto_increment,
priority_name varchar(50) not null,
description_priority text not null
);
**/

create table statuses(
id_status int(1) primary key not null auto_increment,
name varchar(128) not null
);

create table tasks(
id_task int(11) primary key not null auto_increment,
title VARCHAR(50) not null,
description MEDIUMTEXT not null,
status_id int(1) references statuses.id_status,
user_id int(11) references user.id_user
);

alter table tasks add constraint user_id FOREIGN KEY (user_id) references users(id_user);
alter table tasks_history add constraint tasks_id FOREIGN KEY (tasks_id) references tasks(id_task);
alter table users add constraint position_id FOREIGN KEY (position_id) references positions(id_position);
alter table users add constraint login FOREIGN KEY (login) references login(user_id);
alter table tasks add constraint status_id FOREIGN KEY (status_id) references statuses(id_status);


insert into statuses values (1,"Zrealizowano");
insert into statuses values (2,"Do realizacji");
insert into statuses values (3,"W trakcie");
insert into statuses values (4,"Po terminie");

insert into positions values (1,"Administrator","Posiada uprawnienia do zarządzania pracownikami, do dodawania zadań oraz może generować raporty");
insert into positions values (2,"Kierownik","Przydziela pracowników do zadań, może generować raporty");
insert into positions values (3,"Pracownik","Posiada uprawnienia do zmiany statusu zadań");