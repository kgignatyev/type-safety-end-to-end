FROM openjdk:12
ENV MAVEN_HOME=/usr/share/maven
COPY temp/apache-maven-3.6.3 /usr/share/maven
ENV PATH=$PATH:/usr/share/maven/bin

COPY pom.xml /usr/share/dbmigration/pom.xml
RUN echo $PATH && cd /usr/share/dbmigration  \
   && mvn dependency:go-offline

ENV DBHOST_PORT=_DBHOST_PORT
ENV DBNAME=_DBNAME
ENV DBUSER=_DBUSER
ENV DBPASS=_DBPASS
ENV DBSCHEMA=public


WORKDIR /usr/share/dbmigration
CMD mvn --offline -f pom.xml -Dmigration.jdbc-url=jdbc:postgresql://$DBHOST_PORT/$DBNAME -Dmigration.db-user=$DBUSER -Dmigration.db-password=$DBPASS -Dmigration.default-schema=$DBSCHEMA  flyway:migrate
