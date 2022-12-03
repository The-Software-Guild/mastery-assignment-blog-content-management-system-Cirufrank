DROP DATABASE IF EXISTS blogcms;
CREATE DATABASE blogcms;
USE blogcms;

CREATE TABLE author(
	authorId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(40) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    isManager boolean NOT NULL,
    displayName VARCHAR(40) NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post(
	postId INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('active', 'pending','inactive','deleted') NOT NULL DEFAULT 'pending',
    activationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expirationDate TIMESTAMP NULL,
    tite VARCHAR(55) NOT NULL,
    headline VARCHAR(70) NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE postauthor(
	postId int NOT NULL
		REFERENCES post(postId),
	authorId int NOT NULL
		REFERENCES post(postId),
	PRIMARY KEY(postId, authorId)
);

CREATE TABLE tag(
	tagId INT PRIMARY KEY AUTO_INCREMENT,
    tag VARCHAR(50) NOT NULL
);

CREATE TABLE posttag(
	postId INT NOT NULL
		REFERENCES post(postId),
	tagId INT NOT NULL
		REFERENCES tag(tagId),
    PRIMARY KEY(postId, tagId)
);

CREATE TABLE body(
	bodyId INT PRIMARY KEY AUTO_INCREMENT,
    body TEXT NOT NULL
);

CREATE TABLE postbody(
	postId INT NOT NULL
		REFERENCES post(postId),
	bodyId INT NOT NULL
		REFERENCES body(bodyId),
	PRIMARY KEY(postId, bodyId)
);