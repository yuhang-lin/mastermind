# Mastermind
|Build Status| 
|:---:|
|[![Travis CI build status](https://travis-ci.org/yuhang-lin/mastermind.svg?branch=master)](https://travis-ci.org/yuhang-lin/mastermind)|

A command line user interface simulating the board game Mastermind in Java, which has two modes: user guess mode and computer guess mode. 

In user guess mode, it generates a code and user will be asked to provide a guess for the code at most 12 turns. If the user provides the correct code as the computer generated, user wins the game, otherwise, user loses the game. Users can also ask for a hint for at most two times during the game. Each time the user wins the game, a record of number of guesses used is stored in a text file so the user will be able to see some summary statistics (minimum, maximum and average number of guesses used previously to win the game, last number of guesses, number of times playing the game and number of times winning the game) at the end of the game or by request. 

In computer guess mode, the user is required to give a code and the program is able to guess the correct code within five guesses by using Donald Knuth’s five-guess algorithm. 
