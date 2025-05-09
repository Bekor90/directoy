-- Usuarios
INSERT INTO users (user_id, name, email, password, created, modified, last_login, token, is_active) VALUES
('11111111-1111-1111-1111-111111111111', 'Juan Rodriguez', 'juan@dominio.cl', 'hunter2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'token-1', 1);
-- Teléfonos
INSERT INTO phone (id, number, citycode, contrycode, user_id) VALUES (2, '1234567', '1', '57', '11111111-1111-1111-1111-111111111111');
