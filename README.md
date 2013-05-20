## Glassfish / Arquillian JPA Test Example
This is a Java EE JPA database integration test example.

The integration tests are run in an embedded Glassfish container
using Shrinkwrap and the Arquillian test framework.

The following technologies are used:

* **Glassfish Embedded** 
* **EJB / CDI**
* **Arquillian**
* **DBUnit / JUnit**
* **OpenJPA**
* **Maven**

_Note:_ Glassfish comes prepackaged with EclipseLink as default JPA persistence provider.
In order to use OpenJPA these steps were necessary:

* Explicitly set the persistence provider in `persistence.xml`
* Include the `openjpa` dependency in Maven. Don't use `openjpa-all` since it depends on apache bval, which will crash on startup. Glassfish already uses hibernate validator.
* Use the OpenJPA processor and enhancer in the maven pom.

Clone this repo and run with `mvn clean install`.


Inspired by [https://github.com/stijnvanpoucke/stijnvp.examples]()

This project is licensed under the [Apache License](http://www.apache.org/licenses/LICENSE-2.0.html).

May 2013, Kai Sternad
