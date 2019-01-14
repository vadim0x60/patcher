# Patcher
[![Build Status](https://travis-ci.org/vadim0x60/patcher.svg?branch=master)](https://travis-ci.org/vadim0x60/patcher)
[![codecov](https://codecov.io/gh/vadim0x60/patcher/branch/master/graph/badge.svg)](https://codecov.io/gh/vadim0x60/patcher)
[![Clojars Project](https://img.shields.io/clojars/v/patcher.svg)](https://clojars.org/patcher)

Use REST inside your Clojure programs. Represent an edit to a data structure as a data structure (_patch_) and apply it to any nested combination of maps, lists, vectors and sets. 

## Installation

Add this to `project.clj` (if you use Leiningen)

```clj
[patcher "0.0.0"]
```

or this to `deps.edn` (if you don't)

```clj
{patcher {:mvn/version "0.0.0"}}
```

## Usage

First, things first, let's get imports and namespaces off our chest.

```clj
(require '[patcher.core :refer [apply-patch])
```

Then fire up the REPL and type a definition in-o:

```clj
(def patch {:type :put :path [:some :keys] :value "A new value"})
```

And apply it to a thing-o

```clj
(apply-patch patch {:some {:keys "An old value"} :other {:keys "I am a value too, y'know"}})
=> {:some {:keys "A new value"} :other {:keys "I am a value too, y'know"}}
```

Apply it to a thing-o

```clj
(apply-patch {:type :post :path [:interests] :value :music} {:age 35 :sex :male :interests [:cooking]})
=> {:age 35 :sex :male :interests [:cooking :music]}
```

A patch is a map with 3 keys: `:type`, `:path` and `:value`. `:type` can be `:put`, `:post` or `:delete`.

`:put` will replace whatever value you would get with `(get-in coll path)` with `value`

`:post` is similar, but it will `conj` or `merge` (in case of maps) `value` to existing data instead of replacing it

`:delete` will purge it from the data structure so that `(get-in coll path)` returns `nil`

## License

Copyright © 2018 Skolkovo Institute of Science and Technology

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
