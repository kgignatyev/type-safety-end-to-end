FROM kgignatyev/db-manager:2020-04-15-14-20
COPY src/db_migrations/* /usr/share/dbmigration/src/db_migrations/
