kotlinc -script ../devops/db-manager/find_last_db_migration.kts
source target/db_migration_info

export TAG=$LAST_DB_MIGRATION

export IMAGE=kgignatyev/geography-db-migrations:$TAG

# this is a shortcut
# normally we should package a given versioned artifact from repository
# so we can repackage with different base image


# todo: script to extract versions of interfaces implemented and required
# a simple groovy script

docker build -t $IMAGE \
   -f DB-Migrations.Dockerfile .

docker push $IMAGE

echo $IMAGE
