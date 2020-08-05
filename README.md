![about](about.png)

[slides](docs/overview.pdf)

Prerequisite
---

have access to a npm repo to deploy artifacts, for example by running Nexus locally (assume that latest
 version has been installed and configured at $HOME/app/nexus-3.25.0-03 )

    cd $HOME/app/nexus-3.25.0-03/bin
    ./nexus start
    
maven's settings.xml and ~/.npmrc should be configured to allow uploading artifacts to the repositories
and 'npms' repository created in nexus (for npm packages)

k8s up and running, via minikube or Docker Desktop

Docker daemon available for image creation ( default with Docker Desktop, )    

Generate Client and Server sides from Interface Definition
---

Well Defined Contract between server and client.
It is quite tolerant of changes as long as rules of evolution are followed  
https://blog.envoyproxy.io/evolving-a-protocol-buffer-canonical-api-e1b2c2ca0dec

Note: every change should update version number, no snapshots!
Note: to avoid version number change we can remove artifacts from repo:  http://localhost:8081/#browse/browse

    cd interface
    ./build-stubs.sh
    
that generates server side implementation stubs and client, plus versioned 
documentation in html and markdown formats. HTML documentation is publisheable
and markdown is convenient for embedding into npm module 
  
Run Postgres DB
---
Easy to run in K8s (for example local docker or other mini k8s)

    cd k8s
    ./render-postgres-chart.sh
    kubectl apply -f target/pg-k8s-descriptor.yaml


that gives us Postgres running in k8s and exposed on port 30303. Let us now
create a database there and call it 'geography' ( or any other name but more adjustments
will be necessary) 

Create Database and DB schema
---

     cd geography-service
     ./create-db.sh  
     ./run-db-migration.sh 

Lets (re)-Generate DAO layer
---

     cd geography-service
     ./run-dao-gen.sh
   

  
Run Service
---

    cd geography-service    
    source set-db-env.sh    
    mvn  spring-boot:run
    
Run Proxy
---
compile or download Envoy or grpcwebproxy binaries for your machine
https://www.envoyproxy.io/
or 
https://github.com/improbable-eng/grpc-web/tree/master/go/grpcwebproxy

then run ./run-grpcweb-proxy.sh or  ./run-envoy-proxy-grpc-auth.sh in proxy subdirectory

    cd proxy
    ./run-envoy-proxy-grpc-auth.sh


# Run Clients



## WEB SPA

it is a standard Angular 8 application

Before we run client we need to set up secrets, if you have given access to the secrets you can simply run

    git-secret reveal
    
if not, then please copy geography-client/src/environments/environment-template.ts into 
geography-client/src/environments/environmen.ts and place there a valid google API key that allows working
with Maps API       

    cd geography-client
    npm install
    ng s
    
now we can access application at http://localhost:4200 log in with Auth0 and start managing areas: lets create one or two

## Application 

after login we can copy Bearer token on browser's network tab and paste it into file     
geography-grpc-client/config/jwt.txt

    cd geography-grpc-client
    mvn test
    
    
