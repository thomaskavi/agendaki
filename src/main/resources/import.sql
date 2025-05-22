-- Cargos
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_PROFESSIONAL');

-- Usuários
INSERT INTO tb_user (name, email, password, phone) VALUES ('Maria Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '99999999');
INSERT INTO tb_user (name, email, password, phone) VALUES ('Alex Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '88888888');

-- Profissional
INSERT INTO tb_professional (id, slug, profession, profile_image_url) VALUES (2, 'alex-tattoo', 'Tattoo Artist', 'alex-img.com');

-- Associação usuário/role
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Tatuagem Pequena', 'Tatuagem de até 5 cm com tinta preta', 150.00, 60, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Tatuagem Média', 'Tatuagem de até 20 cm com tinta preta', 350.00, 240, 2);


INSERT INTO tb_appointment (date_time, client_id, service_id) VALUES ('2026-06-01T14:30:00', 1, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id) VALUES ('2027-06-01T14:30:00', 1, 2);