How to run it

idea open as a meaven project, after dependency installed, click run test
![image](https://github.com/tingpengJ/real-time-data-pipeline2/blob/master/run.PNG)

test case explaination

newDeck: check if the total number of cards is correct(52 without jokers),check if response status is success

drawOneOrMoreFromDeck: after draw one or more cards from deck,  check if response status is success , and if the cards are valid , and the number of remaining cards is valid.

drawTooManyCardsFromDeck:after draw too many cards from deck, check if response status is not success,  and if the cards are valid , and the number of remaining cards is valid. also, there is a error message return from server, need to check if the error message is expected.

newDeckWithJokers:  check if the total number of cards is correct(54 includes jokers)

mvn test:
![image](https://github.com/tingpengJ/real-time-data-pipeline2/blob/master/mvn%20test.PNG)

result：
![image](https://github.com/tingpengJ/real-time-data-pipeline2/blob/master/result.PNG)
