- Should the FixtureScript run each time, or should the security be in the database?
- If so, how do the security changes stay persistent?
- Good source https://causeway.apache.org/security/latest/secman/setting-up.html#configure-properties
- admin password should come from an environment variable in the app
# Conventions and Decisions
- default user can be a named user, with a dummy pass. Creating the user runs in prototyping mode.
- That user may have the role `namespace-superuser`, with permission to change everything in the namespace.
- The admin must have this role as well, in both in prototyping and in prod
# Roles for subadmins
- causeway-ext-secman-user
- 
