# Spring Authorization Server UI

This is a basic, very basic, Authorization Server with a UI. This exists because I have to create users with different roles and restarting the Authorization Server was boring.

# TODO

- [X] Make it possible to create and edit clients.
- [X] ~~Proper passwords (it is `password` for everybody)~~ Users can change their passwords.
- [ ] Make it possible to edit clients' passwords (it is `password` for all of them).
- [ ] Ability to view and revoke tokens of a client.
- [X] Ability to remove entities.
- [ ] Add other grant types (current domain supports enough for the `password` grant type).
- [ ] Create a proper service layer.
- [ ] Better roles, scopes and grant types editing (currently, a csv list).
- [ ] ...

# Info

Available users after first startup (`BootstrapData.java`):

- name: `user@email.com`; password: `password`, roles: `USER`
- name: `admin@email.com`; password: `password`; roles: `USER, ADMIN`

Available clients after first startup (`BootstrapData.java`):

- client_id: `client`; client_secret: `password`; grant_types: `refresh_token, password, client_credentials`; scopes: `web, arrobas, saladas`
- client_id: `client2`; client_secret: `password`; grant_types: `password`; scopes: `web`

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
