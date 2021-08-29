CREATE TABLE cuisines (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE states (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cities (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  state_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_cities_states FOREIGN KEY (state_id) REFERENCES states (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE payment_options (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE restaurants (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  delivery_fee decimal(19,2) NOT NULL,
  cuisine_id bigint(20) NOT NULL,
  address_street_name varchar(255) DEFAULT NULL,
  address_number varchar(255) DEFAULT NULL,
  address_complement varchar(255) DEFAULT NULL,
  address_neighborhood varchar(255) DEFAULT NULL,
  address_city_id bigint(20) NOT NULL,
  address_zip_code varchar(255) DEFAULT NULL,
  created_at datetime NOT NULL,
  updated_at datetime NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_restaurants_cities FOREIGN KEY (address_city_id) REFERENCES cities (id),
  CONSTRAINT fk_restaurants_cuisines FOREIGN KEY (cuisine_id) REFERENCES cuisines (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE restaurants_payment_options (
  restaurant_id bigint(20) NOT NULL,
  payment_option_id bigint(20) NOT NULL,
  CONSTRAINT fk_restaurants_payment_options_restaurants FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
  CONSTRAINT fk_restaurants_payment_options_payment_options FOREIGN KEY (payment_option_id) REFERENCES payment_options (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE groups (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE groups_permissions (
  group_id bigint(20) NOT NULL,
  permission_id bigint(20) NOT NULL,
  CONSTRAINT fk_group_permissions_groups FOREIGN KEY (group_id) REFERENCES groups (id),
  CONSTRAINT fk_group_permissions_permissions FOREIGN KEY (permission_id) REFERENCES permissions (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE products (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  description varchar(255) NOT NULL,
  name varchar(255) DEFAULT NULL,
  price decimal(19,2) NOT NULL,
  restaurant_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_products_restaurants FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  email varchar(255) DEFAULT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  created_at datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users_groups (
  user_id bigint(20) NOT NULL,
  group_id bigint(20) NOT NULL,
  CONSTRAINT fk_users_groups_users FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_users_groups_groups FOREIGN KEY (group_id) REFERENCES groups (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  subtotal decimal(10,2) NOT NULL,
  delivery_fee decimal(10,2) NOT NULL,
  total_price decimal(10,2) NOT NULL,

  user_id bigint(20) NOT NULL,
  payment_id bigint(20) NOT NULL,
  restaurant_id bigint(20) NOT NULL,

  address_city_id bigint(20) NOT NULL,
  address_street_name varchar(255) NOT NULL,
  address_number varchar(255) NOT NULL,
  address_complement varchar(255) NULL,
  address_neighborhood varchar(255) NOT NULL,
  address_zip_code varchar(255) NOT NULL,

  status varchar(10) NOT NULL,
  created_at datetime NOT NULL,
  updated_at datetime NOT NULL,
  confirmedAt datetime NULL,
  canceledAt datetime NULL,
  deliveredAt datetime NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_orders_payment_options FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_orders_users FOREIGN KEY (payment_id) REFERENCES payment_options (id),
  CONSTRAINT fk_orders_restaurants FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
  CONSTRAINT fk_orders_cities FOREIGN KEY (address_city_id) REFERENCES cities (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE order_items (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  quantity smallint(6) NOT NULL,
  unit_price decimal(10,2) NOT NULL,
  total_price decimal(10,2) NOT NULL,

  details varchar(255) NULL,
  product_id bigint(20) NOT NULL,
  order_id bigint(20) NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_order_items FOREIGN KEY (product_id) REFERENCES products (id),
  CONSTRAINT fk_order_products FOREIGN KEY (order_id) REFERENCES orders (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;