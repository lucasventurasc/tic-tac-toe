# Tic Tac Toe v2.0

The tic tac toe's folder structure is following the Maven Directory Structure. I've decided to use Gradle to handle my dependencies and the build of my application to be easier for others to run and build the code.

There is a book called Clean Architecture from Robert Cecil Martin, he purposes an approach to separate your application in layers and make all infrastructure part decoupled from application and domain, to be easier in future to change that kind of technology like a database or user interface or anything else. I followed that principle to build the tic tac toe.

 The Tic Tac Toe architecture is divided into three big blocks, "Application", "Domain" and "Infrastructure".

The application package will have the use cases and all code which will interact with domain and the controller which is the main class TicTacToe.

The domain package will have all domain entities with their own knowledge, single responsibilities, and constraints.

The infrastructure package will have classes that are implementing application interfaces to make the code works for a specific environment, in that case, we have the "Console Environment" here, so, every class of application package doesn't know that it handling console input or console output, they just know a behavior and that behavior is implemented on infrastructure package.

With that architecture,  none part of application layer neither domain has to be changed to introduce for example another user interface, this code with minor changes could be changed to be a WebGame just by re-implementing the behaviors on infrastructure package.

Well the "AI" that I've implemented is not really an AI, it is just a random number which not fills an already filled position,  I've decided to not implement a highly evolved AI, besides that, I focused my effort to make easier to introduce another one, without changing the application code.

I've decided to separate the configurations into two interfaces inside of the application which is FieldConfiguration and PlayersConfiguration. I did this because the code has two different parts handling the configuration and I've not wanted to break the "Interface segregation principle" by having a code receiving through dependency injection, for example,  a configuration which has size configuration and player configuration when I just need the size.

I decided to create a Wrapper for the user input called "Move" instead of using integers during the application because it makes easier to understand when you could read the meaning of the parameters directly on code instead of integers, avoiding an anti-pattern called Primitive Obsession.

I've created an Interface called ApplicationFactory, every new environment could be implemented by just implementing the methods inside that interface, the first one is the ConsoleApplicationFactory, which is implementing all methods to work with Console Input and Output.

If someone wants to implement a JSwing version, it just needs to make a concrete class called, for example, JSwingApplicationFactory, and change the defaults on TicTacToe main class.

I had to create a Wrapper for Scanner because it is final and the cleanest way to Mock it was creating that Wrapper and injecting it.

I've added some features not required in exercise to guarantee that application will work correctly with any provided input, for example, a length validation on player character information.

To make tests easier and predictable I've decided to make the algorithm that will sort the player's order as a dependency of the main user case, which is the Game class.

I've created an Interface called UserInteraction, that interface has the methods to the use case interact with the user, that interface is the most important when we are thinking to create another interface to TicTacToe gaming, that concrete class of that interface will be the center of the implementation if a new interface is required.

I've created the class GameDependencies because was getting ugly to receive four parameters into the Game constructor, at that moment I thought if the Game class was not doing too many things and breaking the Single Responsibility Principle, I'm not sure about that because the game class is like an orchestrator and the only logic that its have is to orchestrate the game, I couldn't see clearly if there are points to refactor, if I was doing this with a team I would like more opinions here.

I've created three smoke tests into TicTacToe main to guarantee that the code there is not breaking, I've created which a player wins and another that the game draw, and another one, to verify that unhandled exceptions are not showed to the user.

I would like to have an integration test traveling all layers and all main components and I usually have but in that case, I couldn't, because is not possible to override the System.in, so, at some level, we will still have a mock there.

About technologies.
I'm using JUnit 5 and Java 10, I've decided to Java 10 because of the mutation test plugin (Pitest) it is working stable, and I think that is a great tool to make our tests better. if you want to run the mutation tests check the README at mutation test part. In docker image, I'm using Java 12 because it is the only after Java 8 which have an "alpine" image, and I not wanted to have a too large image to the game.

### Things that probably could be improved.
Verify if it is possible to reduce how many dependencies the Game use case is receiving.

Develop a  highly evolved AI to make the game more interesting.

Maybe the readability of smoke tests could be improved.
