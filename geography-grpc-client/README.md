About
---

This codebase shows how to make direct gRPC calls from client to a server, and how to handle authentication by proxy

Preparation
--

capture JWT bearer token and replace content of file config/jwt.txt
( for example copy Bearer value from network tab in the browser after making an authenticated call from 
web client for geography service )


Run
--
mvn test
