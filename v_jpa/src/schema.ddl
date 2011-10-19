
    create table public.caja (
        id int8 not null,
        numero_caja int4 not null,
        primary key (id)
    );

    create table public.cajero (
        id int8 not null,
        apellido varchar(50) not null,
        cedula varchar(10) not null unique,
        nombre varchar(50) not null,
        telefono varchar(20),
        email varchar(100) not null unique,
        primary key (id)
    );

    create table public.cliente (
        id int8 not null,
        apellido varchar(50) not null,
        cedula varchar(10) not null unique,
        nombre varchar(50) not null,
        telefono varchar(20),
        primary key (id)
    );

    create table public.factura_compra (
        id int8 not null,
        fecha timestamp not null,
        numero_factura varchar(10) not null unique,
        total float8 not null,
        primary key (id)
    );

    create table public.factura_detalle_compra (
        id int8 not null,
        cantidad int4 not null,
        subtotal float8 not null,
        primary key (id)
    );

    create table public.factura_detalle_venta (
        id int8 not null,
        cantidad int4 not null,
        subtotal float8 not null,
        primary key (id)
    );

    create table public.factura_venta (
        id int8 not null,
        fecha timestamp not null,
        numero_factura varchar(10) not null unique,
        total float8 not null,
        estado varchar(40) not null,
        saldo float8 not null,
        primary key (id)
    );

    create table public.pago (
        id int8 not null,
        estado varchar(20) not null,
        monto float8 not null,
        numero_pago int4 not null,
        primary key (id)
    );

    create table public.producto (
        id int8 not null,
        cantidad int4 not null,
        codigo varchar(15) not null unique,
        costo float8 not null,
        nombre varchar(50) not null,
        porcentaje_ganancia float4 not null,
        primary key (id)
    );

    create table public.proveedor (
        id int8 not null,
        direccion varchar(70) not null,
        nombre varchar(50) not null,
        ruc varchar(15) not null unique,
        telefono varchar(20),
        primary key (id)
    );

    create table public.registro_pago (
        id int8 not null,
        fecha timestamp not null,
        mensaje_error varchar(70),
        realizado bool not null,
        primary key (id)
    );

    create sequence public.hibernate_sequence;
