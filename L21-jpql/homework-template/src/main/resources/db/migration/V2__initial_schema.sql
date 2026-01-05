
create sequence address_SEQ start with 1 increment by 1;

create table address
(
    id bigint not null primary key default nextval('address_SEQ'),
    street varchar(50),
    client_id bigint not null unique,

    constraint fk_address_client
            foreign key (client_id)
            references client(id)
);

create sequence phone_SEQ start with 1 increment by 1;

create table phone
(
    id bigint not null primary key default nextval('phone_SEQ'),
    number varchar(50),
    client_id bigint not null,

    constraint fk_phone_client
            foreign key (client_id)
            references client(id)
);
