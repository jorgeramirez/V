
    create table public.cliente (
        id int8 not null,
        apellido varchar(50) not null,
        cedula varchar(10) not null unique,
        nombre varchar(50) not null,
        telefono varchar(20),
        primary key (id)
    );

    create sequence public.hibernate_sequence;
