-- First drop any existing database and user
DROP DATABASE IF EXISTS Billy;
DROP USER IF EXISTS 'userBilly'@'%';

create database billy; -- Creates the new database
create user 'userBilly'@'%' identified by 'pwBilly'; -- Creates the user
grant all on billy.* to 'userBilly'@'%'; -- Gives all privileges to the new user on the newly created database