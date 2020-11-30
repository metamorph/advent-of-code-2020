# aop2020

Advent of Code 2020 is here! Yay!

## Status

![CI](https://github.com/metamorph/advent-of-code-2020/workflows/CI/badge.svg)

## Usage

Run the project's tests (they'll fail until you edit them):

    $ clojure -A:test:runner -M:runner

Build a deployable jar of this library:

    $ clojure -A:jar -M:jar

Install it locally:

    $ clojure -A:install -M:install

Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment variables:

    $ clojure -A:deploy -M:deploy

## License

Copyright © 2020 Anders Engström

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
