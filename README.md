# ImdbRenamer
This is a commandline application that can rename series to the imdb names

## How
This program uses jsoup version 1.13.1 to handle the html

## How to use 
To use this application run the jar with the first argument the link to the main imdb page of the show as seccond argument the template for the file names with a %d at the place of the episode index as a third optional argument you can give the prefix of the file name

## Example 
if serie is <a href="https://www.imdb.com/title/tt0944947/">Game of Thrones</a> :<br>
-url=title/tt0944947/<br>
-templateOld= Game of Thrones ~ Season 1 ~ Episode %d<br>
-templateNew=Game Of Thrones ~ Season %d ~ Episode %d ~ %s (optional) default is Season %d ~ Episode %d ~ %s<br>
-foldername=Seizoen %d (optional) default is Season with %d on the place of the index <br>

### Example (old, V1.1 and before)
if serie is <a href="https://www.imdb.com/title/tt0944947/">Game of Thrones</a> :<br>
java imdbRenamer.jar title/tt0944947/ "S1 E%d"(template of files) "Game of Thrones"(optional)

