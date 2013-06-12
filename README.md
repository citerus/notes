# Notes!

Notes! Is a simple (but still pretty awesome) web application built using [Clojure](http://www.clojure.org), 
[Compojure](https://github.com/weavejester/compojure), [MongoDB](http://www.mongodb.org) and 
[Bootstrap](http://twitter.github.com/bootstrap/). It was created with the purpose of showing how to deploy an 
application on different PaaS providers.

## Prerequisites

To build and run you will need [Leiningen](https://github.com/technomancy/leiningen) 2.x and 
[Java 1.7](http://java.oracle.com) installed.

To run, you will also need a MongoDB database instance. 

## Running locally

First, the defaults expects a locally running MongoDB using the default port. 

You are expected to run MongoDB in ```auth``` mode, you'll have to create a user `mongo` with password `secret` 
on the `notes` database. From the MongoDB shell, run something like (MongoDB 2.4.x and later):

```
> use notes
> db.addUser({ user: "mongo",
              pwd: "secret",
              roles: [ "readWrite" ]
            })
```

See http://docs.mongodb.org/manual/tutorial/add-user-to-database/ for futher info.

To start a web server for the application, run:

```lein ring server```

If everything is good, the server should start and your default web browser will launch and open the 
Notes! application. If something went wrong, you're likely to get a Clojure stacktrace that no mortal 
can interpret. Sorry.

## Deploying and running on a PaaS

Please have a look at the individual PaaS deployment guides:

OpenShift

## License

Source code is licensed under [The MIT License (MIT)](http://www.citerus.se/). Copyright © 2013 [Citerus AB](http://www.citerus.se/)

jQuery dependencies are licensed under [The MIT License (MIT)](http://opensource.org/licenses/MIT)

Bootstrap dependencies are licensed under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

