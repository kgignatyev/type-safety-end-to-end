FROM kgignatyev/db-manager:2020-07-25-16-51
COPY src/db_migrations/* /usr/share/dbmigration/src/db_migrations/
