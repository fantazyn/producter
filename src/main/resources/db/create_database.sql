create database "producter";
create user "producter"  with password 'secret';
grant all privileges on database "producter" to "producter";

create table product
(
    id      VARCHAR(32) not null
        constraint product_pk
            primary key,
    name    VARCHAR(64),
    deleted BOOLEAN     NOT NULL DEFAULT FALSE,
    price   NUMERIC(20, 2),
    created TIMESTAMP
);

create sequence seq_order increment by 1;

create table orders
(
    id          BIGINT not null
        constraint order_pk
            primary key,
    buyer_email VARCHAR(64),
    created     TIMESTAMP
);

create sequence seq_product_in_order increment by 1;

CREATE TABLE product_in_order
(
    id          BIGINT not null
        constraint product_in_order_pk
            primary key,
    product_id VARCHAR(32)
        constraint product_fk
            references product,
    order_id   BIGINT
        constraint order_fk
            references orders,
    number     INTEGER NOT NULL DEFAULT 1
);
