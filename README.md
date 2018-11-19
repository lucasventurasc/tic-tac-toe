# Tic Tac Toe v2.0

### How to run the game from Docker

Create a configuration file, for example

```properties
field.size=3
player1.character=X
player2.character=O
ai.character=A
```

Put on a folder on your pc and map that folder to a volume into
the docker run command.

For example if you put your configuration file at /tmp-on-your-pc folder
you should call docker run like:
```bash
docker run -v /tmp-on-your-pc:/tmp-inside-container -it lucasventurasc/tictactoe:2.0
```

Supposing that your file calls configuration.properties,
when the game asks the file path, type

```bash
/tmp-inside-docker-container/configuration.properties
```

The game will start.

### How to run the application from the code

*For follow you have to set the JAVA_HOME environment variable with java 10 or superior.

Clone the repository and within project folder root, type

##### For windows
Generate application jar typing on terminal:
```cmd
gradlew.bat jar
```
##### For linux
Generate application jar typing on terminal:
```bash
./gradlew jar
```
##### Running jar
With that command a jar will be created at $project_directory/build/libs/tic-tac-toe-2.0.jar

Within project folder, type on terminal:
```
java -jar build\libs\tic-tac-toe-2.0.jar
```

The first thing that application will ask is the location path of configuration file (absolute path), 
you have to create a ".properties" file on your PC. Following the layout bellow:

```properties
field.size=3
player1.character=X
player2.character=O
ai.character=A
```
Feel free to change field size between 3 and 10 and characters for what you want (just 1 letter is allowed).

After that, the game will start.

## Developing

To run application tests:
```
./gradlew test
```

To run mutation tests:
```
./gradlew pitest
```

After run mutation tests if you want to open the report through command line type (must have firefox installed), not tested with MacOS
```
./gradlew piport
```
The index.html of the report is located at $project_direcotry/build/reports/pitest/index.html
