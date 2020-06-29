package citygraph;

import java.util.ArrayList;

//Klasse dient dazu rauszufinden welcher Knoten hat den geringsten Wert
public class SimpleHeap {

    private ArrayList<CityDistance> heap;

    public SimpleHeap() {
        this.heap = new ArrayList<>();
    }

    public boolean isEmpty() {
        return heap.isEmpty();  //wenn heap leer ist, dann ist isEmpty() true
    }

    public void add(City city, int totalDistance) {
        CityDistance cd = new CityDistance(city, totalDistance);
        heap.add(cd);
        //wir fügen ein und sortieren dann direkt
        sort();
    }

    private void sort() {
        // Sortieren (absteigend) --> max(Anfang), min(Ende), dann kann immer Stadt/Knoten am Ende der Liste genutzt werden
        //programmierung von insertion sort:
        for (int i = 1; i < heap.size(); i++) {   //äußere Schleife. Starten for-Loop bei 1, well wir mit vorherigen Element vergleichen wollen fangen wir an zweiter Position an
            int j = i;
            while (j > 0) {    //innere Schleife sortiert vorhergehende Karte an richtige Stelle
                CityDistance aktuell = heap.get(j);
                CityDistance previous = heap.get(j - 1);
                if (aktuell.totalDistance < previous.totalDistance) {     //wenn vorheriges kleiner ist wird getauscht
                    heap.set(j - 1, aktuell);
                    heap.set(j, previous);
                    j--;
                } else {
                    //wir sind mit sortieren fertig, nix ist kleiner, wir brechen ab
                    break;
                }
            }
        }
    }

    public City remove() {       //mit der Methode können wir dann die Stadt/Knoten mit geringster Distanz rausholen
        //zunächst müssen wir Stadt mit geringster Distanz finden, die liegt an letzter stelle

        CityDistance minCD = heap.remove(heap.size() - 1);    //letztes Element wird entfernt
        return minCD.city;
    }


    public void update(City cityToChange, int newTotalDistance) {
        //wenn dich Knotenwert /Distanzwert ändert, dann muss dieser neu in Liste eingegeben werden
        //Liste muss dann auch neu sortiert werden
        for (CityDistance cd : heap) {

            if (cd.city.equals(cityToChange)) {
                cd.totalDistance = newTotalDistance;
                break;
            }
        }
        sort(); // Wert wurde geämndert, es muss also neu sortiert werden
    }
}
