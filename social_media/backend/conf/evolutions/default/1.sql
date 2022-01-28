-- Person schema

-- !Ups

create table "person" (
  "id" SERIAL NOT NULL,
  "name" TEXT NOT NULL,
  "email" TEXT NOT NULL PRIMARY KEY,
  "password" TEXT NOT NULL,
  -- "created" TIMESTAMP WITH TIME ZONE,
  "created" TEXT NOT NULL,
  -- "updated" TIMESTAMP WITH TIME ZONE
  "updated" TEXT NOT NULL
) 

-- !Downs
drop table "person"