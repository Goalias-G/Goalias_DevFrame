create table user
(
    id            int auto_increment
        primary key,
    sex           varchar(10)  default ''                ,
    username      varchar(255) default ''                ,
    password      varchar(255) default ''                ,
    phone_number  varchar(20)  default ''                ,
    age           int          default 0                 ,
    register_time datetime     default CURRENT_TIMESTAMP
);