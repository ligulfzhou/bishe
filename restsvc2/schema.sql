
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
insert into tbusers (nid, cname, cpassword) values (1, 'user1', 'user1');
insert into tbusers (nid, cname, cpassword) values (2, 'user2', 'user2');
insert into tbusers (nid, cname, cpassword) values (3, 'user3', 'user3');
insert into tbusers (nid, cname, cpassword) values (4, 'user4', 'user4');

insert into tbcategories (nid, cname) values (1, 'books', 0);
insert into tbcategories (nid, cname) values (2, 'mobile phones', 0);
insert into tbcategories (nid, cname) values (3, 'computers', 0);
insert into tbcategories (nid, cname) values (4, 'condem', 0);

insert into tbcategories (nid, cname) values (1, 'books', 0);
insert into tbcategories (nid, cname) values (2, 'mobile phones', 0);
insert into tbcategories (nid, cname) values (3, 'computers', 0);
insert into tbcategories (nid, cname) values (4, 'condem', 0);