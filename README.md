GRPC for the win!


Generate Interface
---

Well Defined Contract between server and client.
It is quite tolerant of changes as long as rules of evolution are followed  
https://blog.envoyproxy.io/evolving-a-protocol-buffer-canonical-api-e1b2c2ca0dec

Note: every change should update version number, no snapshots!

    cd interface
    ./build-stubs.sh
    
that generates server side implementation stubs and client, plus versioned 
documentation in html and markdown formats. HTML documentation is publisheable
and markdown is convenient for embedding into npm module 
  

  
