# Property mapper

[![Build Status][1]][2]
[![Coverage Status][3]][4]
[![License][5]][6]

## History

Sometime at work, I was confronted with a broker used to map key-value ``String`` pairs from and to
Java objects. This broker was a pain (for maintenance, and lacking scalibility) and we intended to
write it from scratch for a long time.

I add a global sketch of the new architecture (I *did* sketch it, on a flipboard sheet, side-by-side
with the previous one), and the main question that remained was how to easily perform the mapping,
without sacrificing readability, maintenability and scalibility.

I proposed an annotation-based solution, and wrote a proof-of-concept, which was the base of this
project, and which explains the formats of some data, inspired by the particulars of the project I
was designing it for. This also was the occasion for me to work with annotations of my own. Learning
the basics is always useful and helps understand how a framework may work behind the scenes.

This solution was not retained (my superior prefered the mappings done explicitly in order to have a
finer control on it, but I kept this project nonetheless and tried to bring it up to correct coding
standards.

## Warning

> **This project was designed as a proof-of-concept and was never used in a production environment.
The test coverage suggests it works, and I would be happy to bring some support would you be to use
it.**
>
> **When presenting this PoC to fellow developers, I was asked if [Dozer][7] would be a better match
for my needs. I propose you check on it as it is a much more mature framework than mine, but would
you be to turn back to me, I would gladly take the opportunity of making it grow.**

## Goal

This was designed to allow back and forth mapping between a ``Map<String, String>`` and a POJO.

The POJO's fields are to be annotated with the keys of the values to retrieve in or store to the
map, so the (un)mapping engine could perform its task autonomously, converting the fields from or to
Strings.

## TODO

* [ ] Documentation (annotations, defining parsers, (un)mapping engine)
* [ ] Correct ``@BooleanValues`` for singleton ``BooleanParser``
* [x] Rename ``Converter``s to ``Parser``s
* [ ] Better coverage (custom getter)
* [ ] Make version 1.0.0-SNAPSHOT once the standards are met

[1]: http://img.shields.io/travis/cyChop/property-mapper/master.svg
[2]: https://travis-ci.org/cyChop/property-mapper
[3]: http://img.shields.io/coveralls/cyChop/property-mapper/master.svg
[4]: https://coveralls.io/r/cyChop/property-mapper?branch=master
[5]: https://img.shields.io/badge/license-New%20BSD-blue.svg
[6]: http://opensource.org/licenses/BSD-3-Clause
[7]: http://dozer.sourceforge.net/
