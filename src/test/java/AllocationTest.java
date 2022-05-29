import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AllocationTest {
    /**
     * Dieser Test prüft die Machbarkeit der shift()-Funktion,
     * um zu prüfen, ob die Verschiebung korrekt war
     */
    @Test
    public void shift() {
        Allocation alObject = new Allocation();
        alObject.initial(10); // listOfPlacesOccupancy : [0,0,0,0,0...0] ,listOfPlaceNumbers : [0,1,2,3...9]
        alObject.listOfPlacesOccupancy.set(2,1);
        alObject.listOfPlacesOccupancy.set(5,1);// listOfPlacesOccupancy :[0,0,1,0,0,1,...0] ,listOfPlaceNumbers : [0,1,2,3...9]
        alObject.shift(alObject.listOfPlacesOccupancy,alObject.listOfPlaceNumbers);

        //erwartete Werte
        String expectedOccupancyResult =    "1001000000";
        String expectedPlaceNumbersResult = "2345678901";

        //result
        StringBuilder occupancyResult = new StringBuilder();
        StringBuilder placeNumbersResult = new StringBuilder();

        for (int i = 0 ; i < alObject.listOfPlacesOccupancy.size(); i++){
            occupancyResult.append(alObject.listOfPlacesOccupancy.get(i));
            placeNumbersResult.append(alObject.listOfPlaceNumbers.get(i));
        }
        //zwei Werte vergleichen
        assertEquals(expectedOccupancyResult,occupancyResult.toString());
        assertEquals(expectedPlaceNumbersResult,placeNumbersResult.toString());
    }

    /**
     * Dieser Test überprüft die Machbarkeit der Funktion getEmptyPlacesList(),
     * um die korrekten aufeinanderfolgenden leerer Plätze (Sonnenliegen) bekommen.
     */
    @Test
    public void getEmptyPlacesList() {
        Allocation alObject = new Allocation();
        alObject.initial(10); // listOfPlacesOccupancy : [0,0,0,0,0...0] , listOfPlaceNumbers : [0,1,2,3...9]
        alObject.listOfPlacesOccupancy.set(0,1);
        alObject.listOfPlacesOccupancy.set(5,1);// listOfPlacesOccupancy:[1,0,0,0,0,1,1...0] ,listOfPlaceNumbers:[0,1,2,3...9]
        alObject.listOfPlacesOccupancy.set(6,1);

        List<Integer> expectedEmptyList1 = new ArrayList<>();
        expectedEmptyList1.add(7);
        expectedEmptyList1.add(8);
        expectedEmptyList1.add(9);

        List<Integer> expectedEmptyList2 = new ArrayList<>();
        expectedEmptyList2.add(1);
        expectedEmptyList2.add(2);
        expectedEmptyList2.add(3);
        expectedEmptyList2.add(4);

        List<List<Integer>> resultEmptyLists = alObject.getEmptyPlacesList(alObject.listOfPlacesOccupancy,alObject.listOfPlaceNumbers);
        assertArrayEquals(expectedEmptyList1.toArray(),resultEmptyLists.get(0).toArray());
        assertArrayEquals(expectedEmptyList2.toArray(),resultEmptyLists.get(1).toArray());
    }


    /**
     * Dieser Test überprüft die Funktionalität der Funktion takePlace()
     * ob die aufeinanderfolgenden leeren Plätze richtig vergeben wurden
     */
    @Test
    public void takePlace() {
        Allocation alObject = new Allocation();
        alObject.initial(7); // listOfPlacesOccupancy:[0,0,0,0,0...0] ,listOfPlaceNumbers:[0,1,2,3...9]
        alObject.listOfPlacesOccupancy.set(0,1);
        alObject.listOfPlacesOccupancy.set(5,1);// listOfPlacesOccupancy: [1,0,0,0,0,1,...0] ,listOfPlaceNumbers:[0,1,2,3...9]
        List<List<Integer>> emptyPlacesList1 = alObject.getEmptyPlacesList(alObject.listOfPlacesOccupancy,alObject.listOfPlaceNumbers);
        alObject.takePlace(3,emptyPlacesList1);

        List<Integer> expectedOccupancyList = new ArrayList<>();

        expectedOccupancyList.add(1);
        expectedOccupancyList.add(1);
        expectedOccupancyList.add(1);
        expectedOccupancyList.add(1);
        expectedOccupancyList.add(0);
        expectedOccupancyList.add(1);
        expectedOccupancyList.add(0);

        assertArrayEquals(expectedOccupancyList.toArray(),alObject.listOfPlacesOccupancy.toArray());

        // für den Fall, dass die Anzahl der Personen die aufeinanderfolgenden
        // freien Plätze übersteigt
        List<List<Integer>> emptyPlacesList2 = alObject.getEmptyPlacesList(alObject.listOfPlacesOccupancy,alObject.listOfPlaceNumbers);
        alObject.takePlace(4,emptyPlacesList2);

        assertArrayEquals(expectedOccupancyList.toArray(),alObject.listOfPlacesOccupancy.toArray());
    }

    /**
     * Dieser Test prüft die Funktionalität der Funktion deleteGroupFromTakenPlaces(),
     * ob es richtig war, die Gruppe und die Sonnenliegen, auf denen diese Gruppe lag, zu löschen
     */
    @Test
    public void deleteGroupFromTakenPlaces() {
        Allocation alObject = new Allocation();
        alObject.initial(6);//[0,0,0,0,0,0]
        List<Integer> listOfGroup1 = new ArrayList<>();
        listOfGroup1.add(0);
        listOfGroup1.add(1);//[1,1,0,0,0,0]
        alObject.groupPlaceNumbersMap.put(1,listOfGroup1);

        List<Integer> listOfGroup2 = new ArrayList<>();
        listOfGroup2.add(2);
        listOfGroup2.add(3);//[1,1,1,1,0,0]
        alObject.groupPlaceNumbersMap.put(2,listOfGroup2);

        alObject.deleteGroupFromTakenPlaces(1);

        Map<Integer,List<Integer>> expectedGroupPlaceNumbersMap = new HashMap<>();
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(2);
        expectedList.add(3);
        expectedGroupPlaceNumbersMap.put(2,expectedList);

        assertEquals(expectedGroupPlaceNumbersMap, alObject.groupPlaceNumbersMap);
    }
}