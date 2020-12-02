use as12366;
drop table if exists manufacturing_vs_covid;
drop table if exists manufacturingandcovid;
drop table if exists covid_with_header;
drop table if exists covid;

create external table covid_with_header (--This table is for Lucille's Covid data including the header row
	month string,--YYYY-MM date label
	state string,--State Code
	cases int)--Number of Covid cases
row format delimited fields terminated by ','
location '/user/as12366/project/covid_data';

create table covid (--This table is to store Lucille's Covid data without the extra header row and without the whitespace surrounding the state code
	month string,--YYYY-MM date label
	state string,--State Code
	cases double);--Number of Covid cases

insert into covid (month, state, cases)
select month, substr(state,2,2), cases from covid_with_header --Remove the whitespace around the state codes
where month<>"monthlycasesoutput.month" --Remove the header row
order by state ASC, month ASC limit 441;

create table manufacturingandcovid (--This table is to store the joined manufacturing and covid tables
	state string,--State Code
	label string,--YYYY-MM date label
	m_value double,--Manufacturing Employment value (in thousands of employees)
	c_value double);--Number of Covid cases

insert into manufacturingandcovid (state, label, m_value, c_value)
select n.state, n.label, n.value, c.cases from normalized n, covid c
where 
	n.label>="2020-1" and --Only insert dates from Jan. 2020 and beyond so that both columns of data have matching date labels
	n.state<>"AK" and --Remove Alaska as the Covid data does not contain it
	n.label=c.month and
	n.state=c.state
order by state ASC, label ASC limit 441;

create table manufacturing_vs_covid (--This table is for the final manufacturing vs covid analytic
	state string, --State code
	r double); --Correlation coefficient indicating correlation between manufacturing employment and Covid cases

insert into manufacturing_vs_covid (state, r)
select m.state, (count(m.state)*sum(m.m_value*m.c_value)-sum(m.m_value)*sum(m.c_value))/sqrt((count(m.state)*sum(m.m_value*m.m_value)-sum(m.m_value)*sum(m.m_value))*(count(m.state)*sum(m.c_value*m.c_value)-sum(m.c_value)*sum(m.c_value))) --Calculate the correlation coefficient for each state
from manufacturingandcovid m
group by m.state
order by m.state ASC limit 49;


select * from manufacturing_vs_covid;

exit;
