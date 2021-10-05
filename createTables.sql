Create table Department(
	dept_name	Varchar(20),
    building	Varchar(15),
    budget		Numeric(12,2), -- need to make sure its above 0
    Primary Key(dept_name));
    
Create table Instructor(
	id			Varchar(5),
    name		Varchar(20)		not Null,
	dept_name	Varchar(20),
    salary		Numeric(8,2),
    Primary Key(id),
    foreign key(dept_name) references Department(dept_name) on delete set null);
    
create table course(
	course_id	varchar(8),
    title		varchar(50),
    dept_name	varchar(20),
    credits		numeric(2,0), -- make sure it is above 0
    primary key(course_id),
    foreign key(dept_name) references department(dept_name) on delete set null);

create table classroom(
	building	varchar(15),
    room_number	varchar(7),
    capacity	numeric(4,0),
    primary key(building, room_number));

create table section(
	course_id	varchar(8),
    sec_id		varchar(8),
    semester	varchar(6),
    year		numeric(4,0),
    building	varchar(15),
    room_number	varchar(7),
    time_slot_id varchar(4),
    primary key(course_id, sec_id, semester,year),
    foreign key(course_id) references course(course_id) on delete cascade,
    foreign key(building, room_number) references classroom(building, room_number) on delete set null);
    
Create table teaches(
	id			varchar(5),
    course_id	varchar(8),
    sec_id		varchar(8),
    semester	varchar(6),
    year		numeric(4,0),
    Primary Key(id, course_id, sec_id, semester, year),
    foreign Key(id) references instructor(id) on delete cascade);
    
    
create table student(
	id			varchar(5),
    name		varchar(20)		not null,
    dept_name	varchar(20),
    tot_cred	numeric(3,0),
    primary key(id),
    foreign key(dept_name) references department(dept_name) on delete set null);
    
create table takes(
	id			varchar(5),
    course_id	varchar(8),
    sec_id		varchar(8),
    semester	varchar(6),
    year		numeric(4,0),
    grade		varchar(2),
    primary key(id, course_id, sec_id, semester, year),
    foreign key(course_id, sec_id,semester,year) references section(course_id, sec_id,semester,year)
				on delete cascade);
                
create table advisor(
	s_id		varchar(5),
    i_id		varchar(5),
    primary key(s_id),
    foreign key(s_id) references student(id) on delete cascade,
    foreign key(i_id) references instructor(id) on delete set null);
    
create table prereq(
	course_id	varchar(8),
    prereq_id	varchar(8),
    primary key(course_id, prereq_id),
    foreign key(course_id) references course(course_id),
    foreign key(prereq_id) references course(course_id));
    
create table timeslot(
	time_slot_id	varchar(4),
    day				varchar(1),
    start_hour		int,
    start_min		int,
    end_hour		int,
    end_min			int,
    primary key(time_slot_id, day, start_hour, start_min));
                