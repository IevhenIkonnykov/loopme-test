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
* publisher - username: c@c.com, password: pass
