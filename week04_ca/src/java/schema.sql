drop schema if exists authdb;

create schema authdb;

use authdb;

create table users (
	userid varchar(32) not null,
	password varchar(128) not null,
	primary key (userid)
) engine=InnoDB default charset=utf8;

create table groups (
	groupid varchar(32) not null,
	userid varchar(32) not null,
	primary key (groupid, userid)
) engine=InnoDB default charset=utf8;

CREATE TABLE `notices` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `postedDateTime` datetime NOT NULL,
  `category` varchar(20) NOT NULL,
  `content` mediumtext,
  `userid` varchar(32) NOT NULL,
  PRIMARY KEY (`noticeId`),
  KEY `userid` (`userid`),
  CONSTRAINT `notices_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;

/*
	Create jdbcRealm
	Add jdbc connection pool and jdbc resource
	Assume jdbc resource is jdbc/authdb

	# realm name referenced in web.xml
	name: authdb-realm 
	# hard coded to jdbcRealm
	JAAS context: jdbcRealm (must be this)
	JNDI: jdbc/authdb
	User Table: users
	User Name Column: userid
	Password Column: password
	Group Table: groups
	Group Table user Name Column: userid
	Group Name Column: groupid
	# Cannot find the use of this in the source code.
	# Need to have a value, enter NONE
	Password Encryption Algorithm: AES
	# digest is use to hash password, user the same digest before updating password
	Digest Algorithm: SHA-256 
	Encoding: Hex
*/
