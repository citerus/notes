# Notes!

Notes! Is a simple (but still pretty awesome) web application built using [Clojure][1], [Compojure][2], [MongoDB][3] and [Bootstrap][4]. It was
created with the purpose of showing how to deploy an application on different PaaS providers.

[1]: http://www.clojure.org
[2]: https://github.com/weavejester/compojure
[3]: http://www.mongodb.org
[4]: http://twitter.github.com/bootstrap/

## Prerequisites

To build and run you will need [Leiningen][1] 2.x and [Java 1.7][2] installed.

To run, you will also need a MongoDB database instance. 

[1]: https://github.com/technomancy/leiningen
[2]: http://java.oracle.com

## Running locally

First, the defaults expects a locally running MongoDB using the default port. 

If you run in auth mode, you'll have to create a user `mongo` with password `secret` 
on the `notes` database. From the MongoDB shell, run something like (MongoDB 2.4.x and later):

```
> use notes
> db.addUser({ user: "mongo",
              pwd: "secret",
              roles: [ "readWrite" ]
            })
```

See [http://docs.mongodb.org/manual/tutorial/add-user-to-database/][] for futher info.

To start a web server for the application, run:

```lein ring server```

If everything is good, the server should start and your default web browser will launch and open the 
Notes! application.

## License

Source code is licensed under [The MIT License (MIT)][1]. Copyright © 2013 [Citerus AB][1]

jQuery dependencies are licensed under [The MIT License (MIT)][2]

Bootstrap dependencies are licensed under [Apache License, Version 2.0][3]


[1]: http://www.citerus.se/
[2]: http://opensource.org/licenses/MIT
[3]: http://www.apache.org/licenses/LICENSE-2.0
