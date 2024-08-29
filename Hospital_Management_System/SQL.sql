create database hospital;
use hospital;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

insert into users values(1,'admin','admin');

create table patients(
	id int auto_increment primary key,
    name varchar(255) not null,
    age int not null,
    gender varchar(20) not null
);

create table DOCTORS(
		id int auto_increment primary key,
        name varchar(255) not null,
        specialization varchar(255) not null
);

CREATE TABLE APPOINTMENTS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);


insert into doctors(name,specialization) values ("Raunak Mishra","Cardiologist");
insert into doctors(name,specialization) values ("Harsh singh","Neurologist");

