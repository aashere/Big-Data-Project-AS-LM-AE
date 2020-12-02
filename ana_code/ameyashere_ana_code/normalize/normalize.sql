use as12366;
drop table if exists state_codes;
drop table if exists dates;
drop table if exists normalized_with_header;
drop table if exists normalized;

create external table normalized_with_header (--table for the non-normalized data
	state string, --State name
	year int, --Year
	period string, --Month, preceded by letter 'M'
	label string, --YYYY-MM date label
	value decimal(30, 1)) --Manufacturing employment (in thousands of employees)
row format delimited fields terminated by ','
location '/user/as12366/project/joined_data';

create external table state_codes (--table that maps state names to state codes
	state string, --State name
	code string) --State code
row format delimited fields terminated by ','
location '/user/as12366/project/state_codes';

create external table dates (--table that maps old date labels to date labels with leading 0s in front of month digits removed
	old string, --YYYY-MM date label with a leading 0 before single digit months
	new string) --YYYY-MM date label without a leading 0 before single digit months
row format delimited fields terminated by ','
location '/user/as12366/project/dates';

create table normalized (--table that contains the normalized data with state codes and date labels with leading 0s removed
	state string, --State code
	label string, --Normalized YYYY-MM date label
	value decimal(30,1)); --Manufacturing employment value

--Normalizing the data
insert into normalized (state, label, value)
select s.code, d.new, n.value 
from state_codes s, dates d, normalized_with_header n
where 
	n.state<>"State" and --Remove any extra header rows
	n.label>="2019-01" and --Only grab data from Jan. 2019 and later
	s.state = n.state and
	d.old = n.label;

select * from normalized;

exit;
