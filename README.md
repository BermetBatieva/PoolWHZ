# Einen Algorithmus, der die Platzverteilung berechnet
![java](https://github.com/BermetBatieva/PoolWHZ/blob/main/Image/img.png)
```java
listOfPlaceNumbers =    [0, 1, 3 ...]
listOfPlacesOccupancy = [0, 1, 1, 1 ,0,0,0, 0] - Liste mit Sonnenliegenstatus (0 -ist nicht beschäftigt, 1 - ist beschäftigt)

shift - verschieben der Liste der Platznummern und der Liste der Beschäftigungsinformationen
ein Beispiel:
listOfPlaceNumbers =    [0 1 2 3 4 5 6 7 8 9]
listOfPlacesOccupancy = [0 0 0 1 1 0 1 0 1 0]

shift() => 0 am Anfang gehen an das Ende der Liste und behalten ihre Nummerierung bei
listOfPlaceNumbers =    [3 4 5 6 7 8 9 0 1 2]
listOfPlacesOccupancy = [1 1 0 1 0 1 0 0 0 0]


getFreePlacesListgetFreePlacesList(numbersOfPlaces, infoAboutPlaces) => return [[] [] ... []]
ein Beispiel:
listOfPlaceNumbers =    [3 4 5 6 7 8 9 0 1 2]
listOfPlacesOccupancy = [1 1 0 1 0 1 0 0 0 0]

return [[5], [7], [9, 0, 1, 2]]

void takePlace(int countOfPeople, List<List<Integer>> emptyPlacesList);
takePlace void: countOfPeople = k = 3
emptyPlacesList - Liste der leeren Plätze(Sonnenliegen)
ein Beispiel:
listOfPlaceNumbers =    [3 4 5 6 7 8 9 0 1 2]
listOfPlacesOccupancy = [1 1 0 1 0 1 0 0 0 0]

emptyPlacesList = [[5], [7], [9, 0, 1, 2]]

=> void Ergebnis:
listOfPlaceNumbers =    [3 4 5 6 7 8 9 0 1 2]
listOfPlacesOccupancy = [1 1 0 1 0 1 1 1 1 0]
emptyPlacesList = [[5], [7], [2]]

void deleteGroupFromTakenPlaces(int groupNr);

Map<Integer, List<Integer>> groupPlaceNumbersMap = new HashMap<>();
Key - groupNr = die Gruppennummer
Value - List<Integer> besetzte Plätze durch diese Gruppe