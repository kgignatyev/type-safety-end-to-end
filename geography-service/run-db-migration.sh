source set-db-env.sh

mvn  -f ../devops/db-manager/pom.xml \
 -Dmigration.jdbc-url=jdbc:postgresql://${PGHOST}:${PGPORT}/${DB} \
 -Dmigration.db-user=${PGUSER} \
 -Dmigration.db-password=${PGPASSWORD} \
 -Dmigration.db-schema=${DBSCHEMA} \
 -Dmigration.sources=$(pwd)/src/db_migrations \
 -Dproject.dir=$(pwd) \
 flyway:migrate
