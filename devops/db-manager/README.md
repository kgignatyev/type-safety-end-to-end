Base for creation of DB migrations with flyway for Postgres DB

first, download apache maven in this directory 

https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

then build docker image  and push

        ./build.sh

To produce migration container run


        FROM kgignatyev/flyway-db-manager:<tag>
        COPY src/db_migrations/* /usr/share/dbmigration/src/db_migrations/
        
Then set appropriate env variables


          -e  DBHOST_PORT=127.0.0.1:5432
          -e  DBNAME=db
          -e  DBUSER=postgres
          -e  DBPASS=admin
          -e  DBSCHEMA=public
          
