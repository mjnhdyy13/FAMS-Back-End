-- ----------------------------
-- Insert table user_permission
-- ----------------------------
INSERT INTO user_permission (class_room, learning_material, role_name, syllabus, training_program, user)
VALUES ('FULLACCESS', 'FULLACCESS', 'SUPPERADMIN', 'FULLACCESS', 'FULLACCESS', 'FULLACCESS');
INSERT INTO user_permission (class_room, learning_material, role_name, syllabus, training_program, user)
VALUES ('CREATE', 'CREATE', 'CLASSADMIN', 'CREATE', 'CREATE', 'CREATE');
INSERT INTO user_permission (class_room, learning_material, role_name, syllabus, training_program, user)
VALUES ('VIEW', 'VIEW', 'TRAINER', 'VIEW', 'VIEW', 'VIEW');
-- ----------------------------
-- Insert table user
-- ----------------------------
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Ánh Nguyễn Nghị', 'vi.vy@hotmail.com', '2000-04-22', '0', '0335857968', '123456', 'ACTIVE', 1);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Phi Huỳnh Bác', 'man.diep@vo.name.vn', '2002-11-13', '1', '0335857878', '123456', 'DEACTIVE', 3);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Đoàn Trà Linh', 'linh21@hotmail.com', '1985-11-23', '0', '0334147878', '123456', 'ACTIVE', 3);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Giáp Ngân Hạ', 'eninh@duong.com', '2000-09-02', '0', '0334147741', '123456', 'ACTIVE', 3);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Trác Bình Tùng', 'tracbinh@duong.com', '1992-09-30', '1', '0335879856', '123456', 'DEACTIVE', 1);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Tào Huấn', 'huantao1@vo.name.vn', '2001-11-13', '1', '0335879123', '123456', 'ACTIVE', 1);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Khoa Thiện Châu', 'chau.chau@vo.name.vn', '2002-07-15', '1', '0335878523', '123456', 'ACTIVE', 3);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Xa Vy Đào', 'vivi.vy@hotmail.com', '2000-03-23', '0', '0335878523', '123456', 'DEACTIVE', 3);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Đinh Tân Hà', 'haninh@name.com', '2003-09-30', '0', '0335877741', '123456', 'ACTIVE', 1);
INSERT INTO user (name, email, day_of_birth, gender, phone, password, status_user, role_id)
VALUES ('Trương Mộc Nghi', 'moc.nghinghi@hotmail.vn', '1981-04-09', '0', '033587748', '123456', 'ACTIVE', 3);
