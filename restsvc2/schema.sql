
--------------
--simply is just serial
--------------

-- You can use any other integer data type, such as smallint.

-- Example :

-- CREATE SEQUENCE user_id_seq;
-- CREATE TABLE user (
--     user_id smallint NOT NULL DEFAULT nextval('user_id_seq')
-- )

-- ALTER SEQUENCE user_id_seq OWNED BY user.user_id;

-------------------- 
-------------------- drop formal tables
--------------------
drop table if exists tbusers;
drop table if exists tbcategories;
drop table if exists tbgoods;
drop table if exists tborders;
drop table if exists tborderitems;



---------------------
--------------------- create tables
---------------------
create table tbusers (
	nid serial primary key,
	cname text not null,
	cpassword text not null,
	dcreate_at timestamp with time zone default now(),
	nrole int not null default 0 --- default is 0, but admin is 1
);


create table tbcategories(
	nid serial primary key,
	cname text not null
--	ncount int default 0
);

create table tbgoods(
	nid serial primary key,
	cname text not null,
	fprice float not null,
	cdesc text not null,
	ncategoryid int references tbcategories(nid) on delete cascade,
	ncount int default 0
);

create table tborders(
	nid serial primary key,
	ncreate_at timestamp with time zone default now(),
	nuser_id int references tbusers(nid),
	ftotal float not null,
	nhandlered int default 0
);

create table tborderitems(
	nid serial primary key,
	ngood_id int references tbgoods(nid),
	ncount int not null,
	norder_id int references tborders(nid) on delete cascade
);


------------------------
------------------------ create extra index
------------------------
create index i_tbusers_cname on tbusers using btree (cname);
create index i_tbcategories_cname on tbcategories using btree (cname);
create index i_tbgoods_cname on tbgoods using btree (cname);               -- whether it needs to be unique index ?


--------------------------
------add fake data-------
--------------------------
-- 1: fake users
insert into tbusers (nid, cname, cpassword) values (1, 'user1', 'user1');
insert into tbusers (nid, cname, cpassword) values (2, 'user2', 'user2');
insert into tbusers (nid, cname, cpassword) values (3, 'user3', 'user3');
insert into tbusers (nid, cname, cpassword) values (4, 'user4', 'user4');

-- 2: fake categories
insert into tbcategories (nid, cname) values (1, 'books');
insert into tbcategories (nid, cname) values (2, 'mobile phones');
insert into tbcategories (nid, cname) values (3, 'computers');
insert into tbcategories (nid, cname) values (4, 'condem');

-- 3: fake goods
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (1, 'java in action', 20, 'it is about java language', 1, 1000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (2, 'erlang otp in action', 30, 'it is about erlang language and otp framework', 1, 2000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (3, 'definitive guide to cocos2d-x', 40, 'it talks about the cocos2d-x game engine', 1, 3000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (4, 'introduction to tornado', 50, 'about python/tornado framework', 1, 4000);

insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (5, 'nokia 5250', 800, 'cheap, last long', 2, 1000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (6, 'iphone 6', 6000, 'amazing, apple tech', 2, 2000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (7, 'huawei c353', 1200, 'lalala', 2, 3000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (8, 'samsung z7', 1700, 'so so', 2, 4000);

insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (9, 'nokia 5250', 800, 'cheap, last long', 3, 1000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (10, 'iphone 6', 6000, 'amazing, apple tech', 3, 2000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (11, 'huawei c353', 1200, 'lalala', 3, 3000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (12, 'samsung z7', 1700, 'so so', 3, 4000);

insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (13, 'lenovo v470', 5000, 'cheap, last long', 4, 1000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (14, 'mac pro', 12000, 'fantastic', 4, 2000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (15, 'mac air', 8000, 'awesome', 4, 3000);
insert into tbgoods (nid, cname, fprice, cdesc, ncategoryid, ncount) values (16, 'chromium', 3000, 'cheap', 4, 4000);
