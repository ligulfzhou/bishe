drop table if exists image;
create table image(
	imgid varchar(80) not null primary key,
	name varchar(80) not null,
	remote_file_id varchar(80) not null,
	size int not null,
	length int not null,
	width int  not null,
	fmat varchar(80) not null
);
