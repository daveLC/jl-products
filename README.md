# JL Products App

## Versions
| Tech | Version |
| :--- |:-------| 
| Gradle | 5.2.1 |
| Java | 8 |
| Spring Boot | 2.1.3.RELEASE|
| Spock | 1.2 |

## App

This application contains a single endpoint for retriveing JL products that have price reductions applied to them.

To run the app:

    > ./gradlew bootRun

Actuator is added using the non-standard `/monitor` base path with the 'info', 'health', 'mappings' and 'logfile' endpoints active.

The devtools dependency has been added that allows live reload of classes and static files by running `./gradlew build`
