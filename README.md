# Additional software
It is supposed that the following software is installed on a computer:
* java 8 or newer ([see official website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
* maven 3.x ([see official website](https://maven.apache.org/download.cgi)) or it might be included in a used IDE
* nodejs 8.x and npm 5.x ([see official website](https://nodejs.org/en/download/))
* angular cli ([see official website](https://cli.angular.io/))
* web browser (Google Chrome is preferred)

# Before building the project
* be sure that there is a live Internet connection for downloading required dependency packages
* it is necessary to install node modules for the ui part of the project. 
To do this execute the command `npm install` in the folder `src/main/ui`.

**Note:** The ui part of the project is tested and built on the maven's `validate` phase.

# Credentials
By default the following users are present in the database:
* admin - username: a@a.com, password: pass
* operator - username: b@b.com, password: pass
* publisher1 - username: c@c.com, password: pass
* publisher2 - username: x@x.com, password: pass

# General notes:
* UI part of the project is not complete, because Vladimir Borodulyn mentioned that it is not required. 
It lacks create/update/delete functionality for app/publisher/operator. Nevertheless the backend part exposes such api.
* Task description does not contain information about a test coverage, so the ui part is covered only partly, but the backend part is covered better.
* CSRF support is disabled for simplicity, because the task description does not contain requirements concerning how security should be organized and in which bounds.
* UI part is not optimized for mobile or tablet screen sizes and there is not a support for variety of web browsers.
It was developed and tested for a modern Google Chrome browser. 

