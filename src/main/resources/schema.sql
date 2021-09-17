create table if not exists history
(
    id         serial primary key,
    message_id numeric,
    message    varchar
);