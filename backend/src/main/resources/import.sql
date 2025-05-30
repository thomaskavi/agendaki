-- Usuários
INSERT INTO tb_user (name, email, password) VALUES ('Admin', 'admin@gmail.com', '$2a$10$TV5k7DcVvmKoQUsisrx3huuBLvEHibKxXIVSLXECaCTnSB4F/uB7y');
INSERT INTO tb_user (name, email, password, phone, birth_date) VALUES ('Maria Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '99999999', '1995-02-12');
INSERT INTO tb_user (name, email, password, phone, birth_date) VALUES ('Alex Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '7787878', '1992-02-15');
INSERT INTO tb_user (name, email, password, phone, birth_date) VALUES ('tatuadorNome', 'tatuador@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '88888888', '2001-07-25');
INSERT INTO tb_user (name, email, password, phone, birth_date) VALUES ('dentistaNome', 'dentista@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11111111', '1985-03-15');
INSERT INTO tb_user (name, email, password, phone, birth_date) VALUES ('advogadoNome', 'advogado@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '44444444', '1987-05-20');

-- admin ID = 1, maria (client) ID = 2, alex (client) ID = 3, tatuador ID = 4, dentista ID = 5, advogado ID = 6


-- Profissionais
INSERT INTO tb_professional (id, slug, profession, profile_image_url) VALUES (4, 'tatuador', 'Tatuador', 'tatuador-img.com');
INSERT INTO tb_professional (id, slug, profession, profile_image_url) VALUES (5, 'dentista', 'Dentista', 'dentista-img.com');
INSERT INTO tb_professional (id, slug, profession, profile_image_url) VALUES (6, 'advogado', 'Advogado', 'advogado-img.com');

-- Cargos
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_PROFESSIONAL');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

-- Associação usuário/role
-- admin ID = 1, maria (client) ID = 2, alex (client) ID = 3, tatuador ID = 4, dentista ID = 5, advogado ID = 6
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (5, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (6, 2);

-- Serviços oferecidos
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Tatuagem Artística Minimalista', 'Design exclusivo para tatuagens de até 5cm, com traço fino ou estilo aquarela. Perfeita para quem busca um toque sutil e personalizado, feita com total segurança e higiene.', 180.00, 75, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Desenho de Tatuagem Elaborada', 'Criação e aplicação de tatuagem de médio porte (até 20cm), com detalhes e sombreamento complexo. Inclui sessão de consultoria para desenvolver a arte perfeita para você.', 450.00, 270, 4);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Consulta e Limpeza Dental Preventiva', 'Avaliação completa da sua saúde bucal, seguida de uma limpeza profissional para remover placa e tártaro. Mantenha seu sorriso saudável e prevenido contra cáries e gengivite.', 95.00, 50, 5);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Sessão de Clareamento Dental Profissional', 'Conquiste um sorriso mais branco e radiante com nosso clareamento dental seguro e eficaz. Procedimento realizado com técnicas modernas para realçar a luminosidade dos seus dentes.', 320.00, 75, 5);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Consultoria Jurídica Rápida', 'Esclareça suas dúvidas jurídicas urgentes com um advogado especialista. Consulta online de até 45 minutos para orientação sobre questões em diversas áreas do direito.', 200.00, 45, 6);
INSERT INTO tb_service_offered (name, description, price, duration_in_minutes, professional_id) VALUES ('Análise Detalhada de Contratos', 'Revisão e parecer jurídico completo de contratos (ex: aluguel, compra e venda, prestação de serviços). Garanta a segurança dos seus acordos com uma análise profissional e detalhada.', 350.00, 90, 6);

-- Agendamentos com clientes Maria (ID 2) e Alex (ID 3)
-- Tatuador (ID 4) oferece service_id 1 (Tatuagem Artística Minimalista) e 2 (Desenho de Tatuagem Elaborada)
-- Dentista (ID 5) oferece service_id 3 (Consulta e Limpeza Dental Preventiva) e 4 (Sessão de Clareamento Dental Profissional)
-- Advogado (ID 6) oferece service_id 5 (Consultoria Jurídica Rápida) e 6 (Análise Detalhada de Contratos)

-- Maria (ID 2) agendou Tatuagem Minimalista (ID 1) com Tatuador (ID 4)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-01T14:30:00', '2025-06-01T15:45:00', 2, 1, 4, 'PENDING');

-- Alex (ID 3) agendou Tatuagem Elaborada (ID 2) com Tatuador (ID 4)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-01T16:00:00', '2025-06-01T19:00:00', 3, 2, 4, 'PENDING');

-- Maria (ID 2) agendou Limpeza Dental (ID 3) com Dentista (ID 5)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-03T09:00:00', '2025-06-03T09:50:00', 2, 3, 5, 'PENDING');

-- Alex (ID 3) agendou Clareamento Dental (ID 4) com Dentista (ID 5)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-04T14:00:00', '2025-06-04T15:15:00', 3, 4, 5, 'PENDING');

-- Maria (ID 2) agendou Consultoria Jurídica (ID 5) com Advogado (ID 6)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-05T11:00:00', '2025-06-05T11:45:00', 2, 5, 6, 'PENDING');

-- Alex (ID 3) agendou Análise de Contratos (ID 6) com Advogado (ID 6)
INSERT INTO tb_appointment (start_time, end_time, client_id, service_id, professional_id, status) VALUES ('2025-06-06T10:00:00', '2025-06-06T11:30:00', 3, 6, 6, 'PENDING');


-- Slots disponíveis do Tatuador (id 4)
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (4, '2025-06-01T10:00:00', '2025-06-01T11:15:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (4, '2025-06-02T09:30:00', '2025-06-02T12:00:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (4, '2025-06-02T13:00:00', '2025-06-02T14:15:00', false);

-- Slots disponíveis do Dentista (id 5)
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (5, '2025-06-03T08:00:00', '2025-06-03T08:50:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (5, '2025-06-03T10:00:00', '2025-06-03T11:15:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (5, '2025-06-04T10:00:00', '2025-06-04T11:15:00', false);

-- Slots disponíveis do Advogado (id 6)
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (6, '2025-06-05T09:00:00', '2025-06-05T09:45:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (6, '2025-06-05T13:30:00', '2025-06-05T15:00:00', false);
INSERT INTO tb_available_slot (professional_id, start_time, end_time, is_booked) VALUES (6, '2025-06-06T13:00:00', '2025-06-06T14:30:00', false);