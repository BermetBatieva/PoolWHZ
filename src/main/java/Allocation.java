import java.util.*;

/**
 * Klasse, die AllocationRepository-Schnittstelle implementiert
 */
public class Allocation implements AllocationRepository {

    /**
     * lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,2,...n-1]
     */
    public List<Integer> listOfPlaceNumbers = new ArrayList<>();
    public List<Integer> listOfPlacesOccupancy = new ArrayList<>();
    /**
     * GROUP_NR - um die Gruppennummerierung zu steuern
     */
    public int GROUP_NR = 0;

    /**
     * Diese Map ist notwendig um information über Gruppen zu kontrollieren
     * key - Gruppennummer, value - Sonnenliegen(wo diese gruppe ist)
     */
    public Map<Integer, List<Integer>> groupPlaceNumbersMap = new HashMap<>();


    /**
     * Diese Methode ruft alle implementierten Methoden der angegebenen Klasse auf
     * diese Methode(selectCommands) ist für Kontrollpanel implementiert
     * @param command - Befehl des Benutzers, eine Aktion(show_place,show_groups) auszuwählen
     */
    public void selectCommands(String command){
        Scanner scanner = new Scanner(System.in);
        switch (command){
            case "show_place":
                showInfoAboutPlaces();
                break;
            case "show_groups":
                showInfoAboutGroups();
                break;
            case "take_place":
                shift(listOfPlacesOccupancy, listOfPlaceNumbers);
                List<List<Integer>> freePlacesList = getEmptyPlacesList(listOfPlacesOccupancy, listOfPlaceNumbers);
                System.out.println("Personenzahl eingeben:"); //кол-во чел
                int countOfPeople = scanner.nextInt();
                takePlace(countOfPeople, freePlacesList);
                break;
            case "delete_group":
                System.out.println("Geben Sie die Gruppennummer ein:");// номер группы
                int groupNr = scanner.nextInt();
                deleteGroupFromTakenPlaces(groupNr);
                break;
            case "exit":
                break;
            default:
                System.out.println("Ungültiger Befehl!!!"); //Invalid command!!!
                break;
        }
    }


    /**
     * Diese Methode (initial) akzeptiert die Anzahl der Sonnenliegen
     * @param countOfPlaces = n Sonnenliegen
     * lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n-1]
     * lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus
     * alle Sonnenliegen haben den Status 0, da noch keine Kunden vorhanden sind
     */
    @Override
    public void initial(int countOfPlaces){
        for (int i = 0; i < countOfPlaces; i++){
            listOfPlaceNumbers.add(i);
            listOfPlacesOccupancy.add(0);
        }
    }


    /**
     * Diese Methode zeigt Informationen über den aktuellen Zustand der Sonnenliegen an
     * void - gibt nichts zurück
     * lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n-1]
     */
    @Override
    public void showInfoAboutPlaces(){
        StringBuilder line = new StringBuilder("===============");
        String strFormat1 = "%15s:";
        String strFormat2 = "%3s |";
        StringBuilder placeNumbers = new StringBuilder(String.format(strFormat1, "Platznummer"));
        StringBuilder placeOccupancy = new StringBuilder(String.format(strFormat1, "Platzbelegung"));
        for (int i = 0; i < listOfPlaceNumbers.size(); i++){
            placeNumbers.append(String.format(strFormat2, listOfPlaceNumbers.get(i)));
            placeOccupancy.append(String.format(strFormat2, listOfPlacesOccupancy.get(i)));
            line.append("=====");
        }
        System.out.println(line);
        System.out.println(placeNumbers);
        System.out.println(placeOccupancy);
        System.out.println(line);
    }


    /**
     * Diese Methode zeigt Informationen über Gruppen
     * und auf welchen Sonnenliegen sich diese Gruppe befindet
     * gibt eine Set von Key(Gruppennummer) mit ihren Value(besetzte Plätze) aus(mit der Methode HashMap keySet())
     */
    @Override
    public void showInfoAboutGroups(){
        String strFormat = "%15s | %20s";

        System.out.println("=========================================");
        System.out.printf((strFormat) + "\n", "Gruppennummer", "besetzte Plätze");
        System.out.println("-----------------------------------------");
        for (Integer key: groupPlaceNumbersMap.keySet()){
            System.out.printf((strFormat) + "\n", key, groupPlaceNumbersMap.get(key));
        }
        System.out.println("==========================================");
    }



    /**
     * Diese Methode zum Verschieben der Liste,
     * falls die Liste lstOfPlacesOccupancy am Anfang und am Ende 0 hat,
     * verschiebt alle 0-en an das Ende der Liste
     * @param lstOfPlacesOccupancy und verschiebt auch
     * @param lstOfPlaceNumbers, um die Nummerierung beizubehalten

     * lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n]
     */
    @Override
    public void shift(List<Integer> lstOfPlacesOccupancy, List<Integer> lstOfPlaceNumbers){
        if(lstOfPlacesOccupancy.contains(1)){
            while (lstOfPlacesOccupancy.get(0) != 1){
                Collections.rotate(lstOfPlacesOccupancy, -1);
                Collections.rotate(lstOfPlaceNumbers, -1);
            }
        }
    }


    /**
     * Diese Methode gibt eine Liste leerer Plätze als Listen innerhalb einer Liste zurück.
     * Da aufeinanderfolgende (nebeneinander) Plätze berücksichtigt werden.
     * @param lstOfPlacesOccupancy - Liste mit Sonnenliegenstatus (0 -ist nicht besetzt, 1 - ist besetzt)
     * @param lstOfPlaceNumbers - Nummerierung der Sonnenliegen [0,1,3,...n-1]
     * @return freePlacesList - leerer Plätze(Sonnenliegen)
     */
    @Override
    public List<List<Integer>> getEmptyPlacesList(List<Integer> lstOfPlacesOccupancy, List<Integer> lstOfPlaceNumbers){
        List<List<Integer>> freePlacesList = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < lstOfPlacesOccupancy.size(); i++){
            if (lstOfPlacesOccupancy.get(i) == 0){
                tmp.add(lstOfPlaceNumbers.get(i));
                if (i == lstOfPlacesOccupancy.size() - 1) // случай когда в конце 0
                    freePlacesList.add(tmp);
            }else if(lstOfPlacesOccupancy.get(i) == 1) {
                if (tmp.size() > 0)
                    freePlacesList.add(tmp);
                tmp = new ArrayList<>();
            }
        }
        //aufsteigend sortieren
        freePlacesList.sort(Comparator.comparingInt(List::size));
        return freePlacesList;
    }


    /**
     * Diese Methode nimmt Leerplätze(Sonnenliegen) für die Personen(countOfPeople = k) in der Gruppe
     * Wenn die maximale Anzahl aufeinanderfolgender freier Plätze größer
     * oder gleich der Anzahl der Personen in der Gruppe ist,
     * werden die leeren Plätze mit Personen dieser Gruppe aufgefüllt
     * und der Sonnenliegenstatus wird 1 - ist besetzt
     * @param countOfPeople = k - Anzahl der Personen in der Gruppe
     * @param emptyPlacesList - Liste der leeren Plätze(Sonnenliegen)
     */
    @Override
    public void takePlace(int countOfPeople, List<List<Integer>> emptyPlacesList){
        int maxCountOfConsecutiveEmptyPlaces = 0;
        for(List<Integer> emptyPlace: emptyPlacesList){
            maxCountOfConsecutiveEmptyPlaces = Math.max(maxCountOfConsecutiveEmptyPlaces, emptyPlace.size());
        }

        List<Integer> takenPlaceNumbersList = new ArrayList<>();

        if (maxCountOfConsecutiveEmptyPlaces >= countOfPeople){
            for (List<Integer> freePlace: emptyPlacesList){
                int count = 1;
                if (freePlace.size() >= countOfPeople){
                    for (Integer placeNumber: freePlace){
                        if (count <= countOfPeople){
                            int index = listOfPlaceNumbers.indexOf(placeNumber);
                            listOfPlacesOccupancy.set(index, 1);
                            takenPlaceNumbersList.add(placeNumber);
                        }
                        count ++;
                    }
                    GROUP_NR ++;
                    groupPlaceNumbersMap.put(GROUP_NR, takenPlaceNumbersList);
                    break;
                }
            }
            System.out.printf("Die Gruppe Nr.%s hat erfolgreich ihre Plätze(Sonnenliegen) eingenommen!\n", GROUP_NR);
        }else {
            System.out.println("Für diese Gruppe gibt es keine freien Plätze(Sonnenliegen)!");
        }
    }


    /**
     * Diese Methode entfernt die verlassende Gruppe
     (mit Map, wo es einen Key(groupNr) und einen Value(listOfPlaceNumbers) gibt)
     * Das heißt, es ersetzt belegte(1) Plätze durch freie(0) Plätze in der listOfPlacesOccupancy-Liste.
     * @param groupNr -die Gruppennummer
     */
    @Override
    public void deleteGroupFromTakenPlaces(int groupNr){
        if (groupPlaceNumbersMap.containsKey(groupNr)){
            List<Integer> vacatedPlaces = groupPlaceNumbersMap.remove(groupNr);
            for (Integer vacatedPlace: vacatedPlaces){
                int index = listOfPlaceNumbers.indexOf(vacatedPlace);
                listOfPlacesOccupancy.set(index, 0);
            }
            System.out.printf("Die Gruppe Nr.%s hat ihre Plätze(Sonnenliegen) verlassen!\n", groupNr);
        }else {
            System.out.println("Es gibt keine Gruppe mit dieser Nummer!");
        }
    }
}


