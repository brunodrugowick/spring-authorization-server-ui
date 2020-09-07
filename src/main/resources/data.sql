insert into user_table (email, password, roles, enabled) values ('user@email.com', 'password', 'USER', true);
insert into user_table (email, password, roles, enabled) values ('admin@email.com', 'password', 'USER, ADMIN', true);

insert into client_table (client_id, client_secret, grant_types, scopes) values ('client', 'password', 'refresh_token, password, client_credentials', 'web, arrobas, saladas');
insert into client_table (client_id, client_secret, grant_types, scopes) values ('client2', 'password', 'password', 'web');
