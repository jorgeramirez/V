
    create table public.caja (
        id int8 not null,
        numero_caja int4 not null,
        primary key (id)
    );

    create table public.cliente (
        id int8 not null,
        apellido varchar(50) not null,
        cedula varchar(10) not null unique,
        direccion varchar(70) not null,
        nombre varchar(50) not null,
        telefono varchar(20),
        primary key (id)
    );

    create table public.factura_compra (
        id int8 not null,
        fecha timestamp not null,
        numero_factura varchar(10) not null unique,
        total float8 not null,
        id_comprador int8 not null,
        id_producto int8 not null,
        primary key (id)
    );

    create table public.factura_detalle_compra (
        id int8 not null,
        cantidad int4 not null,
        subtotal float8 not null,
        id_factura_compra int8 not null,
        id_producto int8 not null,
        primary key (id)
    );

    create table public.factura_detalle_venta (
        id int8 not null,
        cantidad int4 not null,
        subtotal float8 not null,
        id_factura_venta int8 not null,
        id_producto int8 not null,
        primary key (id)
    );

    create table public.factura_venta (
        id int8 not null,
        fecha timestamp not null,
        numero_factura varchar(10) not null unique,
        total float8 not null,
        estado varchar(40) not null,
        saldo float8 not null,
        id_cliente int8 not null,
        id_vendedor int8 not null,
        primary key (id)
    );

    create table public.pago (
        id int8 not null,
        estado varchar(20) not null,
        monto float8 not null,
        numero_pago int4 not null,
        id_caja int8 not null,
        id_factura_venta int8 not null,
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

    create table public.productos_proveedores (
        id_producto int8 not null,
        id_proveedor int8 not null
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
        id_pago int8,
        mensaje_error varchar(70),
        realizado bool not null,
        primary key (id)
    );

    create table public.usuario (
        id int8 not null,
        apellido varchar(50) not null,
        cedula varchar(10) not null unique,
        direccion varchar(70) not null,
        nombre varchar(50) not null,
        telefono varchar(20),
        email varchar(100) not null unique,
        password varchar(64) not null,
        rol varchar(20) not null,
        username varchar(25) not null unique,
        id_caja int8,
        primary key (id)
    );

    alter table public.factura_compra 
        add constraint FK286314257819B4E3 
        foreign key (id_comprador) 
        references public.usuario;

    alter table public.factura_compra 
        add constraint FK28631425C14A03B2 
        foreign key (id_producto) 
        references public.proveedor;

    alter table public.factura_detalle_compra 
        add constraint FKDAE5B8ED8D11C694 
        foreign key (id_producto) 
        references public.producto;

    alter table public.factura_detalle_compra 
        add constraint FKDAE5B8ED82FF7AF 
        foreign key (id_factura_compra) 
        references public.factura_compra;

    alter table public.factura_detalle_venta 
        add constraint FKB5823CFD8D11C694 
        foreign key (id_producto) 
        references public.producto;

    alter table public.factura_detalle_venta 
        add constraint FKB5823CFDAFBD6585 
        foreign key (id_factura_venta) 
        references public.factura_venta;

    alter table public.factura_venta 
        add constraint FKF1D09AC5EADF1DA0 
        foreign key (id_cliente) 
        references public.cliente;

    alter table public.factura_venta 
        add constraint FKF1D09AC5D8DB0F89 
        foreign key (id_vendedor) 
        references public.usuario;

    alter table public.pago 
        add constraint FK346299FDCA21FE 
        foreign key (id_caja) 
        references public.caja;

    alter table public.pago 
        add constraint FK346299AFBD6585 
        foreign key (id_factura_venta) 
        references public.factura_venta;

    alter table public.productos_proveedores 
        add constraint FK7614CE050D3EC68 
        foreign key (id_proveedor) 
        references public.proveedor;

    alter table public.productos_proveedores 
        add constraint FK7614CE08D11C694 
        foreign key (id_producto) 
        references public.producto;

    alter table public.usuario 
        add constraint FKF814F32EFDCA21FE 
        foreign key (id_caja) 
        references public.caja;

    create sequence public.hibernate_sequence;
