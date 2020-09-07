![Maven Package](https://github.com/brunodrugowick/spring-authorization-server-ui/workflows/Maven%20Package/badge.svg)

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

Available users when started (`data.sql`):

```sql
insert into user_table (email, password, roles, enabled) values ('user@email.com', 'password', 'USER', true);
insert into user_table (email, password, roles, enabled) values ('admin@email.com', 'password', 'USER,ADMIN', false);
```

Available client(s) (`AuthorizationServerConfig.java`) - not possible to edit in runtime yet:

```java
clients.inMemory()
    .withClient("client")
    .secret("password")
    .authorizedGrantTypes("refresh_token", "password", "client_credentials")
    .scopes("web");
```

To connect from your Resource Server (if Spring-based) you may need something like this:

```properties
security.oauth2.client.client-id=client
security.oauth2.client.client-secret=password
security.oauth2.client.grant-type=password
security.oauth2.resource.user-info-uri=http://localhost:8088/oauth/user
```

You can use the `insomnia_requests-auth_server_ui.json` file to get a token and user info via API.
