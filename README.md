# Proof-of-concept: Property mapper

[![Build Status][1]][2]
[![Coverage Status][3]][4]
[![License][5]][6]

## Goal

This was designed both as a learning project and a proof-of-concept.

The principle was to be able back and forth between a Map<String, String> and a POJO.

The POJO's fields are to be annotated with the keys of the values to retrieve in or store to the
map, so the (un)mapping engine could perform its task autonomously, converting the fields from or to
Strings.

## TODO

* Coverage for custom getter

[1]: http://img.shields.io/travis/cyChop/property-mapper/master.svg
[2]: https://travis-ci.org/cyChop/property-mapper
[3]: http://img.shields.io/coveralls/cyChop/property-mapper/master.svg
[4]: https://coveralls.io/r/cyChop/property-mapper?branch=master
[5]: https://img.shields.io/badge/license-New%20BSD-blue.svg
[6]: http://opensource.org/licenses/BSD-3-Clause
