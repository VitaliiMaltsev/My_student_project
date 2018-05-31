DROP TABLE IF EXISTS sp_student_order_child;
DROP TABLE IF EXISTS sp_student_order;

CREATE TABLE sp_student_order
(
   student_order_id SERIAL,
   student_order_date date,
   h_surName varchar(50) not null,
   h_givenName varchar(50) not null,
   h_patronymic varchar(50) not null,
   h_date_of_birth date not null,
   h_passport_seria varchar(10) not null,
   h_passport_number varchar(10) not null,
   h_date_issue date not null,
   h_date_expire date,
   w_surName varchar(50) not null,
   w_givenName varchar(50) not null,
   w_patronymic varchar(50) not null,
   w_date_of_birth date not null,
   w_passport_seria varchar(10) not null,
   w_passport_number varchar(10) not null,
   w_date_issue date not null,
   w_date_expire date,
   primary key (student_order_id)
);


CREATE TABLE sp_student_order_child
(
   student_child_id SERIAL,
   student_order_id int not null,
   c_surName varchar(50) not null,
   c_givenName varchar(50) not null,
   c_patronymic varchar(50) not null,
   c_date_of_birth date not null,
   c_birth_document varchar(50),
   primary key (student_child_id)
);


alter table sp_student_order_child add constraint sp_refChild
    foreign key (student_order_id)
    references sp_student_order(student_order_id);