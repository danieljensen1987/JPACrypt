delete from Users;
delete from Roles;

INSERT INTO ROLES (ROLENAME)
VALUES ('admin');
INSERT INTO ROLES (ROLENAME)
VALUES ('teacher');
INSERT INTO ROLES (ROLENAME)
VALUES ('student');


insert into Users(id, password, role)
values
('admin@test.com', 'test', 'admin');
insert into Users(id, password, role)
values
('teacher@test.com', 'test', 'teacher');
insert into Users(id, password, role)
values
('student@test.com', 'test', 'student');