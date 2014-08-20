# Labs (Java) [![Build Status][1]][2]
## Property mapper

This was designed both as a learning project and a proof-of-concept.

The principle was to be able back and forth between a Map<String, String> and a POJO.

The POJO's fields are to be annotated with the keys of the values to retrieve in or store to the map, so the (un)mapping engine could perform its task autonomously, converting the fields from or to Strings.

[1]: https://secure.travis-ci.org/cyChop/property-mapper.png
[2]: http://travis-ci.org/cyChop/property-mapper
