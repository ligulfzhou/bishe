

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
	nid int primary key,
	cname text not null,
	ccreate_at timestamp with time zone default now(),
	nrole_id int not null default 0
);


create table categorys(
	nid int primary key,
	cname text not null,
	ncount int default 0
);

create table goods(
	nid int primary key,
	cname text not null,
	fprice float not null,
	cdesc text not null,
	ncategoryid int references categorys(nid) on delete cascade,
	ncount int default 0
);

create table orders(
	nid int primary key,
	ccreate_at timestamp with time zone default now(),
	total float not null
);

create table orderitems(
	nid int primary key,
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

