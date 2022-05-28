import java.util.List;

/**
 * diese AllocationRepository-Schnittstelle enthält alle notwendigen Methoden
   zur Implementierung des Algorithmus,
   der die räumliche Verteilung berechnet
 */
public interface AllocationRepository {

    /** (Implementierung in der Klasse Allocation.java)
     * Diese Methode akzeptiert die Anzahl der Sonnenliegen
     * @param countOfPlaces = n Sonnenliegen
     */
    void initial(int countOfPlaces);

    /** (Implementierung in der Klasse Allocation.java)
     * Diese Methode zeigt Informationen zu den Sonnenliegen an
     */
    void showInfoAboutPlaces();

    /**
     * Diese Methode zeigt Informationen über die Gruppen an (Implementierung in der Klasse Allocation.java)
       (auf welchen Sonnenliegen sie sich befinden)
     */
    void showInfoAboutGroups();

    /** (Implementierung in der Klasse Allocation.java)
     * Diese Methode zum Verschieben der Liste (Implementierung in der Klasse Allocation)
     * @param lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * @param lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n]
     */
    void shift(List<Integer> lstOfPlacesOccupancy, List<Integer> lstOfPlaceNumbers);


    /**(Implementierung in der Klasse Allocation.java)
     * diese Methode, um leere(unbesetzt) Sonnenliegen zu bekommen
     * @param lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * @param lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n-1]
     * @return freePlacesList - leerer Plätze(Sonnenliegen)
     */
    List<List<Integer>> getEmptyPlacesList(List<Integer> lstOfPlacesOccupancy, List<Integer> lstOfPlaceNumbers);

    /**
     * (Implementierung in der Klasse Allocation.java)
     * Diese Methode nimmt Leerplätze(Sonnenliegen) für die Personen(countOfPeople = k) in der Gruppe
     * @param countOfPeople = k - Anzahl der Personen in der Gruppe
     * @param freePlacesList - Liste der leeren Plätze(Sonnenliegen)
     */
    void takePlace(int countOfPeople, List<List<Integer>> freePlacesList);


    /** (Implementierung in der Klasse Allocation.java)
     * Diese Methode entfernt die verlassende Gruppe
     * @param groupNr - die Gruppennummer
     */
    void deleteGroupFromTakenPlaces(int groupNr);
}
