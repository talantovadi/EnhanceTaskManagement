create table tasks(
	id serial,
	type varchar(20),
	name varchar(50),
	description varchar(50), 
	deadline date,
	priority varchar(10),
	completed boolean
);

insert into tasks (type, name, description, deadline, priority, completed) values 
('Homework task', 'web', 'dignissim vestibulum', '27-12-2023', 'MEDIUM', false),
('Shopping task', 'clothing', 'non pretium', '26-01-2024', 'LOW', false),
('Shopping task', 'groceries', 'vestibulum ac', '03-12-2023', 'LOW', false),
('Homework task', 'database', 'morbi quis', '28-12-2023', 'HIGH', true),
('Meeting task', 'meeting', 'quis justo', '13-12-2023', 'LOW', true);

drop table tasks;


select * from tasks;

delete from tasks where "type" = 'Shopping task' and "name" = 'clothing';

insert into tasks (type, name, description, deadline, priority, completed) 
select 'Shopping task', 'clothing', 'non pretium', '26-01-2024', 'LOW', false
where not exists (select 1 from tasks where type = 'Shopping task' and name = 'clothing' and description = 'non pretium');

update tasks 
set type = 'Homework task', description = 'exam', deadline = '20-12-2023', priority = 'HIGH', completed = true 
where "name" = 'database';

