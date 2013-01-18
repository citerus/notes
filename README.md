# Notes!

Notes! Is a simple web application built using [Clojure][1], [Compojure][2], [MongoDB][3] and [Bootstrap][4]. It was
created with the purpose of having something to use for showing how to deploy stuff onto the dotCloud PaaS.

[1]: http://www.clojure.org
[2]: https://github.com/weavejester/compojure
[3]: http://www.mongodb.org
[4]: http://twitter.github.com/bootstrap/

## Prerequisites

You will need [Leiningen][1] 2.0.0-preview10 or above installed.

[1]: https://github.com/technomancy/leiningen
[2]: http://java.oracle.com

## Running

First, make sure the connection string in handler.clj points to a valid MongoDB instance.

To start a web server for the application, run:

    lein ring server

## License

Source code is licensed under [The MIT License (MIT)][1]. Copyright © 2013 [Citerus AB][1]

jQuery dependencies are licensed under [The MIT License (MIT)][2]

Bootstrap dependencies are licensed under [Apache License, Version 2.0][3]


[1]: http://www.citerus.se/
[2]: http://opensource.org/licenses/MIT
[3]: http://www.apache.org/licenses/LICENSE-2.0
