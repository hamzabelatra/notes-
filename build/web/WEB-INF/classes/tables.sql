create user yournotes identified by yournotes;
grant connect, resource to yournotes;

create sequence noteid_seq nocache;

create table users 
( email     varchar2(30) primary key,
  password  varchar2(10) not null
);

create table notes 
( 
  id   number(5) primary key,
  email    varchar2(30) references users(email),
  title    varchar2(100) not null,
  text     varchar2(2000) not null,
  updated  date
);

insert into users values ('a@a.com','a');
commit;

