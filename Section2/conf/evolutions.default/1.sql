# --- !Ups
create table "customer"(
"id" bigint generated by default as identity(start with 1) not null primary key,
"name" varchar not null,
"age" int not null
);
# --- !Downs
drop table "customer" if exists;