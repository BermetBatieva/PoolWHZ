import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Allocation allocation = new Allocation();

        System.out.println("Eingabe Anzahl der Plätze(Sonnenliegen):");
        int n = scan.nextInt();
        allocation.initial(n);

        while (true){
            System.out.println("Wählen Sie einen Befehl und geben Sie ihn ein: \n" +
                    "< show_place - alle Plätze(Sonnenliegen) mit Nummerierung und Belegung anzeigen\n" +
                    "< show_groups - alle Gruppen mit der Nummerierung der besetzten Plätze(Sonnenliegen) anzeigen\n" +
                    "< take_place - besetzt einen Platz für die Gruppe\n" +
                    "< delete_group - löscht die Gruppe und gibt den belegten Platz frei\n" +
                    "< exit - ausstieg aus dem System\n");
            System.out.println("Geben Sie Ihren Befehl ein:");
            String command = scan.next().toLowerCase(Locale.ROOT).trim();
            allocation.selectCommands(command);
            if (command.equals("exit")){
                break;
            }
        }
    }
}
