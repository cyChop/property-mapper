# Labs (Java) [![Build Status][1]][2]
## Property mapper

A proof-of-concept for an annotation-based map-to-object property mapper.

### Still to do

#### BooleanConverter
Rules:
* true if matches ^(?:true|1|y(?:es)?|o(?:ui)?)$
* false if matches ^(?:false|0|n(?:o(?:n)?)?)$
* ConversionException otherwise

#### Metadata annotation and mapping
* default value
* custom setter (bypass converter)
* mandatory

#### Temporal
* allow _now_ as a default value?

#### Object-to-map mapping
* specify a list of values (@OutgoingMapping?)

[1]: https://secure.travis-ci.org/cyChop/property-mapper.png
[2]: http://travis-ci.org/cyChop/property-mapper
