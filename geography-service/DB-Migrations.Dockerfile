FROM kgignatyev/db-manager:2020-04-15-11-36
COPY src/db_migrations/* /usr/share/dbmigration/src/db_migrations/
