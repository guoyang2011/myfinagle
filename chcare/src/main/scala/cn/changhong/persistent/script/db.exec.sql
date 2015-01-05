DROP DATABASE IF EXISTS crazycat;
CREATE DATABASE crazycat CHARACTER SET utf8;
USE crazycat;
DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id BIGINT NOT NULL auto_increment,
    username VARCHAR(20) NOT NULL,
    iphone VARCHAR(15) NOT NULL,
    email VARCHAR(40) NOT NULL,
    password VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    utype VARCHAR(20) NOT NULL,
    bind VARCHAR(40) NOT NULL,
    promoted_type VARCHAR(40) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY(id),
    UNIQUE KEY ix_username_unique(username),
    UNIQUE KEY ix_iphone_unique(iphone),
    UNIQUE KEY ix_email_unique(email)
)Engine=InnoDB DEFAULT Charset=utf8;

DROP TABLE IF EXISTS Family_Member;
CREATE TABLE Family_Member(
    user_id BIGINT NOT NULL,
    sex VARCHAR(10),
    age INT,
    height DOUBLE,
    weight DOUBLE,
    role VARCHAR(20) NOT NULL,
    created BIGINT NOT NULL,
    CONSTRAINT uk_unique_family_member UNIQUE (user_id, role),
    CONSTRAINT fk__family_member_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;



DROP TABLE IF EXISTS client;
CREATE TABLE client(
    id VARCHAR(200) NOT NULL,
    secret VARCHAR(80) NOT NULL,
    redirect_uri VARCHAR(400),
    scope VARCHAR(200),
    CONSTRAINT pk_client PRIMARY KEY (id)
)Engine=InnoDB DEFAULT Charset=utf8;
DROP TABLE IF EXISTS auto_code;
CREATE TABLE auto_code(
    authorization_code VARCHAR(80) NOT NULL,
    user_id BIGINT NOT NULL,
    redirect_uri VARCHAR(400),
    created_at INT NOT NULL,
    scope VARCHAR(200) NOT NULL ,
    client_id VARCHAR(200) NOT NULL,
    expires_in INT NOT NULL,
    CONSTRAINT pk_auth_code PRIMARY KEY (authorization_code),
    CONSTRAINT fk_auth_code_client_id FOREIGN KEY (client_id) REFERENCES client(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_auth_code_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;
DROP TABLE IF EXISTS access_token;
create table access_token(
    access_token VARCHAR(100) not null,
    refresh_token VARCHAR(100) not null,
    user_id BIGINT not null,
    client_id VARCHAR(200) not null,
    created_at BIGINT not null,
    expires_in BIGINT not null,
    client_type INTEGER not null,
    token_type INTEGER not null,
    CONSTRAINT pk_access_token PRIMARY KEY (access_token),
    CONSTRAINT fk_access_token_user_id FOREIGN key (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;

