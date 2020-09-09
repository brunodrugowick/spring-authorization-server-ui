# Spring Authorization Server UI

This is a basic, very basic, Authorization Server with a UI. This exists because I have to create users with different roles and restarting the Authorization Server was boring.

# TODO

- [X] Make it possible to create and edit clients.
- [ ] Ability to view and revoke tokens of a client.
- [ ] Ability to remove entities.
- [ ] Proper passwords (it is `password` for everybody).
- [ ] Add other grant types (current domain supports enough for the `password` grant type).
- [ ] Create a proper service layer.
- [ ] Better roles, scopes and grant types editing (currently, a csv list).
- [ ] ...

# Info

Available users after first startup (`data.sql`):

```sql
insert into user_table (email, password, roles, enabled) values ('user@email.com', 'password', 'USER', true);
insert into user_table (email, password, roles, enabled) values ('admin@email.com', 'password', 'USER,ADMIN', false);
```

Available clients after first startup (`data.sql`):

```sql
insert into client_table (client_id, client_secret, grant_types, scopes) values ('client', 'password', 'refresh_token, password, client_credentials', 'web, arrobas, saladas');
insert into client_table (client_id, client_secret, grant_types, scopes) values ('client2', 'password', 'password', 'web');
```

To connect from your Resource Server (if Spring-based) you may need something like this:

```properties
security.oauth2.client.client-id=client
security.oauth2.client.client-secret=password
security.oauth2.client.grant-type=password
security.oauth2.resource.user-info-uri=http://localhost:8088/oauth/user
```

The most important endpoints are: 

- `/oauth/token` to get a token via a POST request with username and password as well as clientId as clientPassword.
- `/oauth/user` to retrieve user info (by sending an Authorization HTTP header with `Bearer <token>`)

You can use the `insomnia_requests-auth_server_ui.json` file (import into Insomnia) to test them and other endpoints.
