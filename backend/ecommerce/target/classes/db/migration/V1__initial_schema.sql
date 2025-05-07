-- Cria schema para isolamento de dados
CREATE SCHEMA IF NOT EXISTS ecommerce;

-- Tabela de usuários com autenticação e papéis
CREATE TABLE ecommerce.users (
  id UUID PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255),
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  role VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

   -- Tabela de produtos com SKU único
CREATE TABLE ecommerce.products (
  id UUID PRIMARY KEY,
  sku VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  stock INTEGER NOT NULL,
  category VARCHAR(50),
  image_url VARCHAR(255),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

-- Tabela de carrinhos
CREATE TABLE ecommerce.carts (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES ecommerce.users(id),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

-- Itens do carrinho
CREATE TABLE ecommerce.cart_items (
  id UUID PRIMARY KEY,
  cart_id UUID REFERENCES ecommerce.carts(id),
  product_id UUID REFERENCES ecommerce.products(id),
  quantity INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

-- Tabela de pedidos
CREATE TABLE ecommerce.orders (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES ecommerce.users(id),
  total_amount DECIMAL(10,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

-- Itens do pedido
CREATE TABLE ecommerce.order_items (
  id UUID PRIMARY KEY,
  order_id UUID REFERENCES ecommerce.orders(id),
  product_id UUID REFERENCES ecommerce.products(id),
  quantity INTEGER NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

