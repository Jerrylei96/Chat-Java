USE master;
GO

CREATE DATABASE ChatUser
ON 
PRIMARY( 
	NAME = ChatData,
	FILENAME = 'D:\ChatUser\chatlog.mdf',
	SIZE = 100MB,
	MAXSIZE = 200,
	FILEGROWTH = 20
)


LOG ON(
	NAME = ChatLog,
	FILENAME = 'D:\ChatUser\chatlog.ldf',
	SIZE = 100MB,
	MAXSIZE = 200,
	FILEGROWTH = 20
)
GO

USE ChatUser;
GO

CREATE TABLE account
(
	account_id			VARCHAR(32) ,
	account_password	VARCHAR(32),
	account_name		VARCHAR(32),


