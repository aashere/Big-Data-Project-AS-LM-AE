use as12366;
drop table if exists joined;
drop table if exists joined_with_header;
create external table joined_with_header (--table for all state data joined together with headers
	state string, 
	year int, 
	period string, 
	label string, 
	value decimal(30, 1))
row format delimited fields terminated by ','
location '/user/as12366/project/cleaned_data';

create table joined (--table for all state data joined together with header rows removed
	state string, 
	year int, 
	period string, 
	label string, 
	value decimal(30, 1));

insert into joined 
select * from joined_with_header 
where state<>"State";

exit;
