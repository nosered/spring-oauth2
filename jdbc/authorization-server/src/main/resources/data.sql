INSERT INTO authorities(authority)  VALUES('FOO_WRITE');
INSERT INTO authorities(authority)  VALUES('FOO_READ');

INSERT INTO users(username, password, enabled, uuid) VALUES('admin','{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', true, 'e3b60352-896b-4c14-96ce-0486539b7bf0'); -- password: 123456
INSERT INTO users(username, password, enabled, uuid) VALUES('user','{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', true, '0778443c-37bc-4926-b321-2ac3a030b584');  -- password: 123456

INSERT INTO user_authorities(user_id, authority_id) VALUES (1,1);
INSERT INTO user_authorities(user_id, authority_id) VALUES (1,2);
INSERT INTO user_authorities(user_id, authority_id) VALUES (2,2);