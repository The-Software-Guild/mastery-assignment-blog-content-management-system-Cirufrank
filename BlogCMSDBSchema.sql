DROP DATABASE IF EXISTS blogcms;
CREATE DATABASE blogcms;
USE blogcms;

CREATE TABLE author(
	authorId INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('active', 'inactive','deleted') NOT NULL DEFAULT 'active',
    firstName VARCHAR(40) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    role ENUM('manager', 'marketing') NOT NULL DEFAULT 'marketing',
    displayName VARCHAR(40) NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE post(
	postId INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('active', 'pending','inactive','deleted') NOT NULL DEFAULT 'pending',
    activationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expirationDate TIMESTAMP NULL,
    title VARCHAR(55) NOT NULL,
    headline VARCHAR(70) NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE postauthor(
	postId int NOT NULL,
	authorId int NOT NULL,
    FOREIGN KEY fk_postauthor_postId (postId)
        REFERENCES post(postId),
    FOREIGN KEY fk_postauthor_authorId (authorId)
        REFERENCES author(authorId),
	PRIMARY KEY(postId, authorId)
);

CREATE TABLE tag(
	tagId INT PRIMARY KEY AUTO_INCREMENT,
    tag VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('active', 'deleted') NOT NULL DEFAULT 'active'
);

CREATE TABLE posttag(
	postId INT NOT NULL,
	tagId INT NOT NULL,
    FOREIGN KEY fk_posttag_postId (postId)
        REFERENCES post(postId),
    FOREIGN KEY fk_posttag_tagId (tagId)
        REFERENCES tag(tagId),
    PRIMARY KEY(postId, tagId)
);

CREATE TABLE body(
	bodyId INT PRIMARY KEY AUTO_INCREMENT,
    body TEXT NOT NULL
);

CREATE TABLE postbody(
	postId INT NOT NULL,
	bodyId INT NOT NULL,
    FOREIGN KEY fk_postbody_postId (postId)
        REFERENCES post(postId),
    FOREIGN KEY fk_postbody_body (bodyId)
        REFERENCES body(bodyId),
	PRIMARY KEY(postId, bodyId)
);