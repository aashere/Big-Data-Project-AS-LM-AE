use as12366;
drop table if exists joined;
drop table if exists joined_with_header;
create external table joined_with_header (--table for all state data files joined together with a header row for each state data file
	state string, --State name
	year int, --Year
	period string, --Month preceded by letter 'M'
	label string, --YYYY-MM date label
	value decimal(30, 1)) --Manufacturing employment (in thousands of employees)
row format delimited fields terminated by ','
location '/user/as12366/project/cleaned_data';

create table joined (--table for all state data joined together with the extra header rows removed
	state string, --State name
	year int, --Year
	period string, --Month preceded by letter 'M'
	label string, --YYYY-MM date label
	value decimal(30, 1)); --Manufacturing employment (in thousands of employees)

--Removing the header rows from joined_with_header and inserting into joined
insert into joined 
select * from joined_with_header 
where state<>"State";

exit;
