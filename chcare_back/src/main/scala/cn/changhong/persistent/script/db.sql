DROP DATABASE IF EXISTS crazycat;
CREATE DATABASE crazycat CHARACTER SET utf8;
USE crazycat;
DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id BIGINT NOT NULL auto_increment,
    username VARCHAR(20) NOT NULL,
    iphone VARCHAR(15),
    email VARCHAR(40),
    password VARCHAR(20) NOT NULL,
    type INT NOT NULL,--用户类型 1开发者，0使用者
    CONSTRAINT pk_user PRIMARY KEY(id),
    UNIQUE KEY ix_username_unique(username),
    UNIQUE KEY ix_iphone_unique(iphone),
    UNIQUE KEY ix_email_unique(email)
)Engine=InnoDB DEFAULT Charset=utf8;
DROP TABLE IF EXISTS dev_app;
CREATE TABLE dev_app(
    id BIGINT NOT NULL,---//主要是第三方和本地服务器
    user_id BIGINT NOT NULL,
    key VARCHAR(200) NOT NULL,
    secret VARCHAR(80) NOT NULL,
    desc VARCHAR(500),
    type INT NOT NULL,
    status INT NOT NULL,
    scope VARCHAR(200),
    CONSTRAINT pk_app PRIMARY KEY (id),
    UNIQUE KEY ix_key_unique(key)
    CONSTRAINT fk_app_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;
DROP TABLE IF EXISTS auto_code;
CREATE TABLE auto_code(
    authorization_code VARCHAR(80) NOT NULL,
    app_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    redirect_uri VARCHAR(400),
    created_at INT NOT NULL,
    scope VARCHAR(200) NOT NULL,
    expires_in INT NOT NULL,
    CONSTRAINT pk_auth_code PRIMARY KEY (authorization_code),
    CONSTRAINT fk_auth_code_client_id FOREIGN KEY (app_id) REFERENCES client(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_auth_code_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;
DROP TABLE IF EXISTS access_token;
create table access_token(
    access_token VARCHAR(100) NOT NULL,
    refresh_token VARCHAR(100) NOT NULL,
    app_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    client_id VARCHAR(200) NOT NULL,
    created_at BIGINT NOT NULL,
    expires_in BIGINT NOT NULL,
    app_type INTEGER NOT NULL,---//0代表用户，1代表本地服务器,2代表第三方
    token_type INTEGER NOT NULL,---//0x00代表用户token,0x01代表本地服务器token,0x02代表第三方token
    CONSTRAINT pk_access_token PRIMARY KEY (access_token),
    CONSTRAINT fk_access_token_app_id FOREIGN KEY (app_id) REFERENCES app(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_access_token_user_id FOREIGN key (user_id) REFERENCES user(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)Engine=InnoDB DEFAULT Charset=utf8;

