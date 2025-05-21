INSERT INTO tb_role (authority) VALUES ('ROLE_PROFESSIONAL');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

-- PROFESSIONALS
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Maria Brown', 'maria@barbearia.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'almariaice-brown', 'Cabeleireira', '11988887771', 'https://img.com/alice.jpg');
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Alex Green', 'alex@barbearia.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'alex-green', 'Barbeiro', '11988887772', 'https://img.com/bruno.jpg');
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('SUPER ADMIN', 'admin@barbearia.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'super-admin', 'Admin', '11988887773', 'https://img.com/carla.jpg');
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Diego Rocha', 'diego@barbearia.com', 'senha123', 'diego-rocha', 'Barbeiro', '11988887774', 'https://img.com/diego.jpg');
INSERT INTO tb_professional (name, email, password, slug, profession, phone, profile_image_url) VALUES ('Eduarda Reis', 'eduarda@barbearia.com', 'senha123', 'eduarda-reis', 'Esteticista', '11988887775', 'https://img.com/eduarda.jpg');

INSERT INTO tb_user_role (professional_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (professional_id, role_id) VALUES (3, 2);
INSERT INTO tb_user_role (professional_id, role_id) VALUES (1, 1);


-- SERVICES OFFERED
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte Feminino', 'Corte moderno e personalizado', 70.0, 45, 1);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Hidratação', 'Tratamento capilar profundo', 90.0, 60, 1);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Penteado', 'Para festas e eventos', 120.0, 60, 1);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte Masculino', 'Corte máquina ou tesoura', 45.0, 30, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Barba', 'Modelagem de barba com toalha quente', 35.0, 20, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Sobrancelha', 'Design masculino', 20.0, 15, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte + Barba', 'Pacote completo', 70.0, 50, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Relaxamento Capilar', 'Alisamento leve', 100.0, 60, 2);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Manicure', 'Limpeza e esmaltação', 30.0, 30, 3);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Pedicure', 'Higiene dos pés + esmaltação', 35.0, 40, 3);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Corte Degradê', 'Corte com fade', 50.0, 40, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Barba Navalhada', 'Acabamento navalhado', 40.0, 20, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Design de Corte', 'Riscos e desenhos', 60.0, 45, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Tratamento Capilar', 'Revitalização com óleo', 80.0, 60, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Limpeza de Pele', 'Higienização facial profunda', 90.0, 60, 5);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Massagem Relaxante', 'Corpo inteiro', 120.0, 60, 5);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Depilação', 'Corpo ou áreas específicas', 80.0, 45, 5);

-- CLIENTS
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('João Silva', 'joao@email.com', '11911112222', 1);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Mariana Costa', 'mariana@email.com', '11911113333', 1);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Carlos Pereira', 'carlos@email.com', '11911114444', 1);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Fernanda Lima', 'fernanda@email.com', '11911115555', 2);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Lucas Rocha', 'lucas@email.com', '11911116666', 2);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Aline Dias', 'aline@email.com', '11911117777', 2);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Tiago Neves', 'tiago@email.com', '11911118888', 3);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Camila Soares', 'camila@email.com', '11911119999', 3);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Renan Silva', 'renan@email.com', '11912223333', 3);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Juliana Ramos', 'juliana@email.com', '11912224444', 4);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Fábio Gonçalves', 'fabio@email.com', '11912225555', 4);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Patrícia Souza', 'patricia@email.com', '11912226666', 4);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('André Martins', 'andre@email.com', '11912227777', 5);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Bruna Ferreira', 'bruna@email.com', '11912228888', 5);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Eduardo Nunes', 'eduardo@email.com', '11912229999', 5);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Vanessa Melo', 'vanessa@email.com', '11913334444', 1);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Rodrigo Lopes', 'rodrigo@email.com', '11913335555', 2);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Gabriela Tavares', 'gabriela@email.com', '11913336666', 3);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Leandro Alves', 'leandro@email.com', '11913337777', 4);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Beatriz Cunha', 'beatriz@email.com', '11913338888', 5);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Henrique Dias', 'henrique@email.com', '11914441111', 1);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Tatiane Borges', 'tatiane@email.com', '11914442222', 2);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Matheus Silva', 'matheus@email.com', '11914443333', 3);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Paula Cardoso', 'paula@email.com', '11914444444', 4);
INSERT INTO tb_client (name, email, phone, professional_id) VALUES ('Douglas Reis', 'douglas@email.com', '11914445555', 5);

-- APPOINTMENTS (datas daqui 1 ano)
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-21 10:00:00', 1, 1, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-21 11:00:00', 2, 2, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-21 14:00:00', 4, 4, 2);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-21 15:00:00', 5, 5, 2);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-22 09:30:00', 7, 9, 3);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-22 10:30:00', 8, 10, 3);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-22 13:00:00', 10, 11, 4);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-22 14:30:00', 11, 12, 4);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-23 10:00:00', 13, 15, 5);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-23 11:30:00', 14, 16, 5);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-23 13:00:00', 15, 17, 5);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-24 09:00:00', 3, 3, 1);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-24 11:00:00', 6, 6, 2);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-24 13:00:00', 9, 9, 3);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-24 14:00:00', 12, 13, 4);
INSERT INTO tb_appointment (date_time, client_id, service_id, professional_id) VALUES ('2026-05-24 15:30:00', 20, 17, 5);
