delete from Users;
delete from Roles;

insert into Roles(rolename)
values
('Admin'),
('Teacher'),
('Student')
;

insert into Users(id, password, role)
values
('admin@test.com', 'test', 'Admin'),
('teacher@test.com', 'test', 'Teacher'),
('student@test.com', 'test', 'Student')
;


