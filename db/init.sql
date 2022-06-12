CREATE DATABASE IF NOT EXISTS flyaway;
USE flyaway;

create table Destinations (code varchar(3) not null, name varchar(255) not null, primary key (code));

