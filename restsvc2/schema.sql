
--------------
--simply is just serial
--------------

-- You can use any other integer data type, such as smallint.

-- Example :

-- CREATE SEQUENCE user_id_seq;
-- CREATE TABLE user (
--     user_id smallint NOT NULL DEFAULT nextval('user_id_seq')
-- )

ALTER SEQUENCE user_id_seq OWNED BY user.user_id;

-------------------- 
-------------------- drop formal tables
--------------------
drop table if exists customers;
drop table if exists categorys;
drop table if exists goods;
drop table if exists orders;
drop table if exists orderitems;



---------------------
--------------------- create tables
---------------------
create table customers (
	nid serial primary key,
	cname text not null,
	cpassword text not null,
	ccreate_at timestamp with time zone default now(),
	nrole_id int not null default 0 --- default is 0, but admin is 1
);


create table categorys(
	nid serial primary key,
	cname text not null,
	ncount int default 0
);

create table goods(
	nid serial primary key,
	cname text not null,
	fprice float not null,
	cdesc text not null,
	ncategoryid int references categorys(nid) on delete cascade,
	ncount int default 0
);

create table orders(
	nid serial primary key,
	ccreate_at timestamp with time zone default now(),
	nuserid int references customers(nid),
	total float not null
);

create table orderitems(
	nid serial primary key,
	ngood_id int references goods(nid),
	ncount int not null,
	norder_id int references orders(nid) on delete cascade
);


------------------------
------------------------ create extra index
------------------------
create index i_customers_cname on customers using btree (cname);
create index i_categorys_cname on categorys using btree (cname);
create index i_goods_cname on goods using btree (cname);               -- whether it needs to be unique index ?


--------------------------
------add fake data-------
--------------------------
insert into customers (cname, cpassword) values ('customer1', 'customer1');
insert into customers (cname, cpassword) values ('customer2', 'customer2');
insert into customers (cname, cpassword) values ('customer3', 'customer3');
insert into customers (cname, cpassword) values ('customer4', 'customer4');

insert into categorys (cname, ncount) values ('soup', 0);
insert into categorys (cname, ncount) values ('meat', 0);
insert into categorys (cname, ncount) values ('soup', 0);
insert into categorys (cname, ncount) values ('soup', 0);
