source set-db-env.sh

mvn  -X -f dao-gen-pom.xml \
 -Dmigration.jdbc-url=jdbc:postgresql://${PGHOST}:${PGPORT}/${DB} \
 -Dmigration.db-user=${PGUSER} \
 -Dmigration.db-password=${PGPASSWORD} \
 -Dmigration.db-schema=${DBSCHEMA} \
 mybatis-generator:generate
