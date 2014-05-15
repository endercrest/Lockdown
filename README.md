Lockdown
========

Lock your server down

Lock down allows you to lock the server for only certain groups or users to access. You can add as many lock down types as you want. How to use:

Commands:
/lock disable - removes a current lock allowing all players back online.
/lock status - tells you info about current lock down
/lock [name] - Activates a lock with the following settings.

Permissions:
lock.* - Allows you to connect whenever(Default by OP)
lock.set - Can activate a lock down(Default by OP)
lock.# - # is the level they have.

Config: - Comes with an example lock
full:
message: '&cServer is locked for everyone'
level: 0

Config Info:
full - this is the name of the lock down. To activate type /lock full.
message - The message sent to client when kicked.
level - This is what level you need to log-in. This makes it so everyone gets disconnect unless they have the level 0 permission(lock.0 or lock.*)
