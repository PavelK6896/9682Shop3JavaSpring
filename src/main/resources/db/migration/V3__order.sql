drop table if exists orders cascade;
create table orders (
id bigserial,
user_id bigint not null,
price numeric(8, 2) not null,
address varchar (255) not null,
phone_number varchar(30) not null,
primary key(id),
constraint fk_user_id foreign key (user_id) references users (id));


drop table if exists orders_items cascade;
create table orders_items (id bigserial,
order_id bigint not null,
product_id bigint not null,
quantity int,
price numeric(8, 2),
primary key(id),
constraint fk_prod_id foreign key (product_id) references products (id),
constraint fk_order_id foreign key (order_id) references orders (id));
