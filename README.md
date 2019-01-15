# Patcher
[![Build Status](https://travis-ci.org/vadim0x60/patcher.svg?branch=master)](https://travis-ci.org/vadim0x60/patcher)
[![codecov](https://codecov.io/gh/vadim0x60/patcher/branch/master/graph/badge.svg)](https://codecov.io/gh/vadim0x60/patcher)
[![Clojars Project](https://img.shields.io/clojars/v/patcher.svg)](https://clojars.org/patcher)

Use REST inside your Clojure programs. Represent an edit to a data structure as a data structure (_patch_) and apply it to any nested combination of maps, lists, vectors and sets. 

```clj
[patcher "0.1.0"]
```

## Usage

A patch is a map with 3 keys: `:type`, `:path` and `:value`. `:type` can be `:put`, `:post`, `:delete` or `:merge`.

`:put` will replace whatever value you would get with `(get-in coll path)` with `value`
`:post` is similar, but it will insert (`conj`) `value` into the existing sequence instead of replacing it
`:merge` assumes that `value` is a map or a sequence and replaces a map or a sequence at `path` with a concatenation (merger) of it and `value`
`:delete` will purge it from the data structure so that `(get-in coll path)` returns `nil`

## Example

`(apply-patch {:type :post :path [:interests] :value :music} {:age 35 :sex :male :interests [:cooking]})`
`=> {:age 35 :sex :male :interests [:cooking :music]}`

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
