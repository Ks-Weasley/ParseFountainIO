# ParseFountainIO
A parser for screen play writing found at fountain.io

# Notes on Regular Expressions used
Center text must be checked before Transition element in order to avoid false positives. This can be easily circumvented by changing the Transition Regular Expression to not accept Center text.

## Strict
Regular Expressions for BOLD ITALICS, BOLD, ITALICS must be executed respectively. <br>

## Packages 
*errors* - Contains custom made Warning class <br>
*regularExpression* - Contains the regular expressions for all the elements mentioned @[fountain.io] <br>
*repository* - Contains the characterization class for Fountain elements. <br>

## Media
*outfile.html* - Contains the converted screenplay document in HTML format <br>
*styling.css* - Contains the style aspects of the *outfile.html* <br>
*test.txt* - Default input file in case an input is not provided <br>

## Instructions to run
Navigate to the ParseFountainIO/

> java ParseFountainIO.java testFile.txt

<center>OR</center>

> java ParseFountainIO.java

