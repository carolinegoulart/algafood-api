set foreign_key_checks = 0;

delete from cities;
delete from cuisines;
delete from states;
delete from payment_options;
delete from groups;
delete from groups_permissions;
delete from permissions;
delete from products;
delete from restaurants;
delete from restaurants_payment_options;
delete from users;
delete from users_groups;

set foreign_key_checks = 1;

alter table cities auto_increment = 1;
alter table cuisines auto_increment = 1;
alter table states auto_increment = 1;
alter table payment_options auto_increment = 1;
alter table groups auto_increment = 1;
alter table permissions auto_increment = 1;
alter table products auto_increment = 1;
alter table restaurants auto_increment = 1;
alter table users auto_increment = 1;

INSERT INTO cuisines (id, name) VALUES (1, 'Tailandesa');
INSERT INTO cuisines (id, name) VALUES (2, 'Indiana');
INSERT INTO cuisines (id, name) VALUES (3, 'Argentina');
INSERT INTO cuisines (id, name) VALUES (4, 'Brasileira');

INSERT INTO states (id, name) VALUES (1, 'Minas Gerais');
INSERT INTO states (id, name) VALUES (2, 'São Paulo');
INSERT INTO states (id, name) VALUES (3, 'Ceará');

insert into cities (id, name, state_id) values (1, 'Uberlândia', 1);
insert into cities (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into cities (id, name, state_id) values (3, 'São Paulo', 2);
insert into cities (id, name, state_id) values (4, 'Campinas', 2);
insert into cities (id, name, state_id) values (5, 'Fortaleza', 3);

INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (1, 'Thai Gourmet', 10, 1, '34948-000', 'Rua Francisco Paiva', '123', 'Apt 302', 'Vila Santo Amaro', 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (2, 'Thai Delivery', 9.50, 1, '32468-000', 'Rua Irmão Norberto', '123', 'Casa 1', 'Jardim Carvalho', 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (3, 'Tuk Comida Indiana', 15, 2, '64830-000', 'Rua Antônio de Carvalho', '123', 'Casa 1', 'Vila Clementina', 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (4, 'Java Steakhouse', 12, 3, '92735-000', 'Rua Assis Brasil', '123', 'Casa 1', 'Brooklin', 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, '09230-000', 'Rua Frederico Dias', '123', 'Casa 1', 'Vila Madalena', 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurants (id, name, delivery_fee, cuisine_id, address_zip_code, address_street_name, address_number, address_complement, address_neighborhood, address_city_id, created_at, updated_at) VALUES (6, 'Bar da Maria', 6, 4, '93590-000', 'Rua das Hortências', '123', 'Casa 1', 'Chácara das Pedras', 1, utc_timestamp, utc_timestamp);

insert into payment_options (id, description) values (1, 'Cartão de crédito');
insert into payment_options (id, description) values (2, 'Cartão de débito');
insert into payment_options (id, description) values (3, 'Dinheiro');

insert into permissions (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissions (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurants_payment_options (restaurant_id, payment_option_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into products (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into products (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into products (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into products (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into products (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into products (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into products (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into products (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into products (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into users (id, email, name, password, created_at, updated_at) values (1, 'joao@gmail.com', 'João Silva', 'abcd', utc_timestamp, utc_timestamp);
insert into users (id, email, name, password, created_at, updated_at) values (2, 'maria@gmail.com', 'Maria Santos', '1234', utc_timestamp, utc_timestamp);
