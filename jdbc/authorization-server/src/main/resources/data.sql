INSERT INTO authorities(description)  VALUES('ADMIN');
INSERT INTO authorities(description)  VALUES('USER');

INSERT INTO users(username, password, enabled) VALUES('admin','{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', true); -- password: 123456
INSERT INTO users(username, password, enabled) VALUES('user','{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', true);  -- password: 123456

INSERT INTO user_authorities(user_id, authority_id) VALUES (1,1);
INSERT INTO user_authorities(user_id, authority_id) VALUES (2,2);