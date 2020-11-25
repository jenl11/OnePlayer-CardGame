# My Personal Project: King's Row

## Details of the Project

For my personal project I will be building a one player card game.

**Rules:** This game will have the player get rid of all the
cards in their hand by disposing 2 or more cards in alternating colour and consecutive order. If the player isn't able
to dispose any cards that round, then the player will be dealt 3 cards to their hand. The player's goal is to
get rid all the cards in their hand or finish the rows of each colour of Kings (hence the name *King's Row*) in the 
least amount of rounds as possible. 

With this in mind, this project will include two classes for the model, a class defining a card, and a class for the 
deck that can contain the cards. There will also be test classes for both. For the ui, there is the main class and a 
class for the actual game. This Game class will be in charge of all the user inputs and the display of the game on the 
console. The game will also be telling the player if they made any mistakes in the user input or if they didn't meet the 
game requirements and allow the user to try again.  

This game could be used for those who want to play a simple card game but has no one to play with, especially when you 
get bored during these times of isolation. This idea interested me because I am a big fan of card games, especially 
this game called *King's Corner*. Therefore, I thought it would be fun to design a similar game for this project, but a 
little more simple to play since this is only for one player.

## User Stories

- As a user, I want to be able to add cards that I have in my hand to one of the decks if I meet the requirement of 
alternating colour and consecutive rank
- As a user, I want to be able to input the specification of the colour and rank of the card I want to dispose
- As a user, I want to have a choice to play or quit the game
- As a user, I want to be able to choose which deck I want to put my cards into
- As a user, I want to be able to tell the game I do or don't have cards to play that round

Data persistence:
- as a user, I want to be able to save the state of my game to a file
- as a user, I want to be able to load a saved game from file and continue playing from that saved game
- as a user, I want the option to load the saved game before the game starts

## Phase 4: Task 2

For task 2, I have chosen the option to test and design a class in my model package that is robust. I choose that class 
to be the Card class in which the constructor throws checked exceptions, IllegalArgumentException() and 
IndexOutOfBoundsException(), if a card is constructed with a colour other than red or black or if the rank of the card
a negative number or bigger than 13.

## Phase 4: Task 3
If I had more time to improve the design of my project, I think I would mainly focus on the GUI class. The model classes
I think are pretty simple and basic to work with, however, I think because I haven't worked with GUI's before, I didn't know 
what would be a good approach to organize it and it just ended up all in one class. I think that this can cause a lot of 
confusion due to there being so much code in one class, and it also is not very organized. So, next time I would like 
to organize my GUI with multiple classes that are in charge of maybe each of the panels I have on the game board. 
Since in my GUI there are a total of 5 panels when playing the game, I could make each of those panels a separate class 
so that I can easily find my way through and work on each panel separately without having a clutter of code around it. 


