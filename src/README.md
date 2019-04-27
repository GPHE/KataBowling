Bowling kata
============

Java solution to [this bowling kata](http://codingdojo.org/kata/Bowling/) in Java 8 and JUnit 5.

The version in the default package is the basic one corresponding to the kata itself.<br>
The version in the extended package just add the possibility to add rolls on the fly.<br>

As the kata precise this solution is not intend to provide scores for intermediate frames. If it was supposed to it would probably have been simpler to work frame by frame and not by the rolls as here.<br>
However even if it's not optimal it is possible to do it by considering that the frame score is a game score for which the maximum frame number is 1 and by off-setting the roll list to the first one of the frame.