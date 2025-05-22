-- ROLES
INSERT INTO tb_role (authority) VALUES ('ROLE_PROFESSIONAL');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

-- PROFESSIONALS
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'maria-brown', 'Cabeleireira', '11988887771', 'https://img.com/alice.jpg');
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Alex Green', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'alex-green', 'Barbeiro', '11988887772', 'https://img.com/bruno.jpg');

-- SERVICES OFFERED (para Maria - id 1)
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte Feminino', 'Corte moderno e personalizado', 70.0, 45, 1);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Hidratação', 'Tratamento capilar profundo', 90.0, 60, 1);

-- SERVICES OFFERED (para Alex - id 2)
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte Masculino', 'Corte máquina ou tesoura', 45.0, 30, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Barba', 'Modelagem de barba com toalha quente', 35.0, 20, 2);


-- PROFESSIONAL ROLES
INSERT INTO tb_professional_role (professional_id, role_id) VALUES (1, 1); -- Maria: ROLE_PROFESSIONAL
INSERT INTO tb_professional_role (professional_id, role_id) VALUES (2, 2); -- Alex: ROLE_ADMIN


-- CLIENTS
INSERT INTO tb_client (name, email, password, phone, professional_id) VALUES ('João Silva', 'joao@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11911112222', 1);
INSERT INTO tb_client (name, email, password, phone, professional_id) VALUES ('Mariana Costa', 'mariana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11911113333', 1);
INSERT INTO tb_client (name, email, password, phone, professional_id) VALUES ('Carlos Pereira', 'carlos@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11911114444', 2);
INSERT INTO tb_client (name, email, password, phone, professional_id) VALUES ('Fernanda Lima', 'fernanda@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11911115555', 2);

-- CLIENT ROLES
INSERT INTO tb_client_role (client_id, role_id) VALUES (1, 3); -- João Silva: ROLE_CLIENT
INSERT INTO tb_client_role (client_id, role_id) VALUES (2, 3); -- Mariana Costa: ROLE_CLIENT
INSERT INTO tb_client_role (client_id, role_id) VALUES (3, 3); -- Carlos Pereira: ROLE_CLIENT
INSERT INTO tb_client_role (client_id, role_id) VALUES (4, 3); -- Fernanda Lima: ROLE_CLIENT

-- APPOINTMENTS
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-06-01 10:00:00', 1, 1, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-06-01 11:00:00', 2, 2, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-06-01 14:00:00', 3, 3, 2);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-06-01 15:00:00', 4, 4, 2);
