INSERT INTO tb_specialty (description ) VALUES ('cardiologia');INSERT INTO tb_specialty (description ) VALUES ('neurologia');
INSERT INTO tb_specialty (description ) VALUES ('oftalmologista');

INSERT INTO tb_doctor (name, cpf, email, phone_number, registry, specialty_id)  VALUES ('House', '29663434007', 'house@gmail.com', '+351935893129', '1234567891011', 1L);
INSERT INTO tb_doctor (name, cpf, email, phone_number, registry, specialty_id)  VALUES ('Stone', '86338370051', 'house@gmail.com', '+351935893129', '1234567891011', 2L);

INSERT INTO tb_user (name, cpf, email, phone_number, password, birth_date, gender)  VALUES ('Melquisedeque dos Santos Marins Junior', '03948583366', 'melck@gmail.com', '+351935893129', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '1989-03-01', 'MALE');
INSERT INTO tb_user (name, cpf, email, phone_number, password, birth_date, gender)  VALUES ('Raissa Natasha Einstein França Amaral Marins', '01748583366', 'raissa@gmail.com', '+351935893142', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '1986-07-18', 'FEMALE');

INSERT INTO tb_schedule (created_At, schedule_date, status, type, doctor_id, user_id) VALUES (NOW(), '2022-08-10 11:00:00Z', 'SCHEDULED', 'CONSULT', 1L, 1L);
INSERT INTO tb_schedule (created_At, schedule_date, status, type, doctor_id, user_id) VALUES (NOW(), '2022-08-10 11:10:00Z', 'SCHEDULED', 'CONSULT', 1L, 2L);
INSERT INTO tb_schedule (created_At, schedule_date, status, type, doctor_id, user_id) VALUES (NOW(), '2022-08-10 11:20:00Z', 'SCHEDULED', 'CONSULT', 2L, 1L);
INSERT INTO tb_schedule (created_At, schedule_date, status, type, doctor_id, user_id) VALUES (NOW(), '2022-08-10 11:30:00Z', 'SCHEDULED', 'CONSULT', 2L, 2L);

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);