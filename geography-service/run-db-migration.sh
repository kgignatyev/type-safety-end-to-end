export PGHOST=${PGHOST:-127.0.0.1}
export PGPORT=${PGPORT:-30303}
export PGUSER=${PGUSER:-postgres}
export PGPASS=${PGPASS:-admin}
export DBSCHEMA=${DBSCHEMA:-public}
export DB=${DB:-geography}

mvn  -f db-schema-manager.xml \
 -Dmigration.jdbc-url=jdbc:postgresql://${PGHOST}:${PGPORT}/${DB} \
 -Dmigration.db-user=${PGUSER} \
 -Dmigration.db-password=${PGPASS} \
 -Dmigration.db-schema=${DBSCHEMA} \
 -Dproject.dir=$(pwd) \
 flyway:migrate
