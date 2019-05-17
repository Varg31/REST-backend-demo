create sequence hibernate_sequence start 1 increment 1;

create table institution.classes
(
    class_id int8 not null,
    title varchar(255) null,
    primary key (class_id)
);

create table institution.disciplines
(
    id int8 not null,
    title varchar(255) not null,
    primary key (id)
);

create table institution.students
(
    student_id int8 not null,
    date_of_birth varchar(255),
    gender        varchar(255),
    middle_name   varchar(255),
    name          varchar(255) not null,
    surname       varchar(255) not null,
    classes_class_id int8,
    primary key (student_id)
);

create table institution.teachers
(
    teacher_id int8 not null,
    date_of_birth varchar(255),
    gender        varchar(255),
    middle_name   varchar(255),
    name          varchar(255) not null,
    surname       varchar(255) not null,
    primary key (teacher_id)
);

create table classes_has_disciplines
(
    classes_class_id int8 not null,
    disciplines_dspl_id int8 not null,
    primary key (classes_class_id, disciplines_dspl_id)
);

create table classes_has_teachers
(
    classes_class_id int8 not null,
    teachers_teacher_id int8 not null,
    primary key (classes_class_id, teachers_teacher_id)
);

create table disciplines_has_teachers
(
    teachers_teacher_id int8 not null,
    disciplines_dspl_id int8 not null,
    primary key (teachers_teacher_id, disciplines_dspl_id)
);

create table user_role
(
    user_id int8 not null,
    authorities varchar(255)
);

create table usr
(
    user_id int8 not null,
    activation_code         varchar(1024),
    email                   varchar(1024) not null,
    enabled                 boolean not null,
    last_visit              timestamp,
    password                varchar(1024) not null,
    username                varchar(1024) not null,
    primary key (user_id)
);

alter table if exists usr add constraint UK_dfui7gxngrgwn9ewee3ogtgym unique (username);
alter table if exists institution.students add constraint FKm0t20cm1c6l2oa9g40r1b8x7n
    foreign key (classes_class_id) references institution.classes;
alter table if exists classes_has_disciplines add constraint FK59vnm1u44f5dtf46ie3xfcfhq
    foreign key (disciplines_dspl_id) references institution.disciplines;
alter table if exists classes_has_disciplines add constraint FK7l1rgdv6v0ep8ao2438vkw52y
    foreign key (classes_class_id) references institution.classes;
alter table if exists classes_has_teachers add constraint FKbcqabxyygp9h87d4q4e8j402e
    foreign key (teachers_teacher_id) references institution.teachers;
alter table if exists classes_has_teachers add constraint FK82x3fq2vfdbmf2p8d6nm9t8s4
    foreign key (classes_class_id) references institution.classes;
alter table if exists disciplines_has_teachers add constraint FK96iooxuosohkve210mlvbjn4b
    foreign key (disciplines_dspl_id) references institution.disciplines;
alter table if exists disciplines_has_teachers add constraint FKprc5p4gtgwsucxsiuqfoifjf
    foreign key (teachers_teacher_id) references institution.teachers;
alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;