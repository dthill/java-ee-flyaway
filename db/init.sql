CREATE DATABASE IF NOT EXISTS flyaway;
USE flyaway;

create table if not exists Destinations (code varchar(3) not null, name varchar(255) not null, primary key (code));

create table if not exists Airlines (code varchar(3) not null, name varchar(255), primary key (code));