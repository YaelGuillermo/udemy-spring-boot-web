-- Primero insertar los usuarios
INSERT INTO users (username, password, enabled) 
VALUES 
('yael', '$2a$10$4RdZo2utzYXzt5wVRFoYgO0jVOu59L2PjsKrv3L6TXESOrzA4rg.m', 1),
('jhon', '$2a$10$OyTTShX0bYBiy24cKZ9YqeNniauK/fjdtsD73TC5IFd9ypGd9eS0K', 1);

-- Luego insertar las autoridades (roles)
INSERT INTO authorities (user_id, authority) 
VALUES 
(1, 'ROLE_USER'),
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- Insertar 1 cliente
INSERT INTO clients (name, last_name, email, created_at, photo)
VALUES 
('Juan', 'Perez', 'juan.perez@email.com', DATE '2024-06-18', '');

-- Insertar 10 productos
INSERT INTO products (name, price, created_at) 
VALUES 
('Laptop Dell XPS 13', 1299.99, DATE '2024-01-15'),
('iPhone 15 Pro', 999.99, DATE '2024-02-20'),
('Samsung TV 55" 4K', 799.99, DATE '2024-03-10'),
('Sony Headphones WH-1000XM5', 349.99, DATE '2024-04-05'),
('Apple Watch Series 9', 399.99, DATE '2024-05-12'),
('Microsoft Surface Pro', 1199.99, DATE '2024-06-18'),
('Nintendo Switch OLED', 349.99, DATE '2024-07-22'),
('iPad Air', 599.99, DATE '2024-08-30'),
('Bose Soundbar 700', 799.99, DATE '2024-09-05'),
('Canon EOS R6', 2499.99, DATE '2024-10-11');

-- Insertar 2 facturas para el cliente 1 (Juan Perez)
INSERT INTO invoices (description, observation, created_at, client_id) 
VALUES 
('Compra electrónica personal', 'Pago con tarjeta de crédito', DATE '2024-06-20', 1),
('Equipo de trabajo', 'Para home office', DATE '2024-07-05', 1);

-- Insertar items de factura para la factura 1
INSERT INTO invoice_items (quantity, product_id, invoice_id) 
VALUES 
(1, 1, 1),  -- 1 Laptop Dell
(1, 3, 1),  -- 1 Samsung TV
(2, 4, 1);  -- 2 Sony Headphones

-- Insertar items de factura para la factura 2  
INSERT INTO invoice_items (quantity, product_id, invoice_id)
VALUES
(1, 2, 2),  -- 1 iPhone
(1, 5, 2),  -- 1 Apple Watch
(1, 7, 2);  -- 1 Nintendo Switch