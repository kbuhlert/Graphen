package citygraph;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Citygraph {
    //ist fast genau wie Graph
    //todo: alternativ wären Flugverbindungen oder Bahnverbindungen möglich --> evtl. Klausur?

    public ArrayList<City> cities;      //Array mit sämtlichen Knotenobjekten (=alle Städte)
    public boolean directed;

    public Citygraph(boolean directed) {        //Initialisierne es mit leeren Array
        this.cities = new ArrayList<>();
        this.directed = directed;
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public int numCities() {
        return cities.size();
    }

    public boolean isDirected() {
        return directed;
    }

    public void addStreet(City from, City to, int distance) {
        Street streetForward = new Street(from, to, distance);
        from.connections.add(streetForward);
        if (!directed) {
            Street streetBackward = new Street(to, from, distance);
            to.connections.add(streetBackward);
        }
    }

    public List<Street> getConnectedCities(City city) {
        return city.connections;
    }

    public boolean searchConnection(City fromCity, City toCity) {
        ArrayDeque<City> queue = new ArrayDeque<>();

        //um rauszubekommen welche Knoten besucht wurden, nutzen wir Map, das ist am schnellsten
        //City ist key, Boolean ob besucht ist value
        HashMap<City, Boolean> visited = new HashMap<>();

        queue.add(fromCity);
        visited.put(fromCity, true);    //der aktuelle Knoten wird als beucht in Map markiert

        while (!queue.isEmpty()) {
            City current = queue.remove();  //nächste city aus queue holen
            List<Street> connections = getConnectedCities(current);     //holt die Straßen der Stadt alternativ: current.connections
            for (Street street : connections) {
                if (!visited.containsKey(street.to) || !visited.get(street.to)) {   //wollen wissen ob wir von aktueller city die Zielstadt am Ende der Straße schon besucht haben
                    //außerdem müssen wir zunächst überprüfen, ob der Key bereits enthalten ist, ansonsten bekommen wir nullpointer exception bei abfrage, wenn wert noch nicht auf boolean=true gesetzt, da Wert dann noch null
                    queue.add(street.to);
                    visited.put(street.to, true);
                }
                //Wann ist klar, dass Verbindung besteht? Sobald street.to identisch ist mit City toCity
                if (street.to.equals(toCity)) {
                    return true;
                }
            }
        }
        return false;
    }

    //gibt Liste mit Wegen zwischen zwei Städten
    public List<Street> searchWay(City fromCity, City toCity) {
        ArrayDeque<City> queue = new ArrayDeque<>();

        //um rauszubekommen welche Knoten besucht wurden, nutzen wir Map, das ist am schnellsten
        //City ist key, Straßen.from ist woher wir gekommen sind!!
        HashMap<City, Street> visited = new HashMap<>();

        queue.add(fromCity);
        visited.put(fromCity, null);    //der aktuelle Knoten ist Startknoten und brauch deshalb keinen from-Wert

        while (!queue.isEmpty()) {
            City current = queue.remove();  //nächste city aus queue holen
            List<Street> connections = getConnectedCities(current);     //holt die Straßen der Stadt alternativ: current.connections
            for (Street street : connections) {
                if (!visited.containsKey(street.to)) {   //wollen wissen ob wir von aktueller city die Zielstadt am Ende der Straße schon besucht haben
                    //außerdem müssen wir zunächst überprüfen, ob der Key bereits enthalten ist, ansonsten bekommen wir nullpointer exception bei abfrage, wenn wert noch nicht auf boolean=true gesetzt, da Wert dann noch null
                    queue.add(street.to);
                    visited.put(street.to, street);
                }
                //Wann ist klar, dass Verbindung besteht? Sobald street.to identisch ist mit City toCity
                if (toCity.equals(street.to)) {
                    ArrayList<Street> way = new ArrayList<>();  //hierin werden alle Straßen der Reihe nach abgespeichert
                    while (street != null) {     //bis zur Startcity, denn nur hier ist Wert=null
                        way.add(street);
                        City visitedFrom = street.from;
                        street = visited.get(visitedFrom);
                    }
                    return way;     //durch return an dieser Stelle wird Breitensuche Abgebrochen, sobal erster Weg gefunden wurde
                }
            }
        }
        return null;
    }

    //DIJKSTRA: findet kürzesten Weg
    public List<Street> searchShortestWay(City fromCity, City toCity) {
        ArrayDeque<City> queue = new ArrayDeque<>();    //nächste muss Element mit geringster Enfernung sein --> Queue passt nicht

        //um rauszubekommen welche Knoten besucht wurden, nutzen wir Map, das ist am schnellsten
        //City ist key, Straßen.from ist woher wir gekommen sind!!
        HashMap<City, Street> vorgaengerKnoten = new HashMap<>();
        HashMap<City, Integer> totalDistance = new HashMap<>(); //hält die aktuell kleinste Entfernung für Knoten fest

        //HashMap wird mit allen Knoten und Distanzen befüllt (Distanzen = unendlich)

        for (City city:cities){
            vorgaengerKnoten.put(city,null);         //somit ist HAshMap bereits mit allen Städten befüllt, Vorgängerwert bleibt null, erspart uns später mit .containsKey Hashmap immer wieder zu befragen
            totalDistance.put(city, Integer.MAX_VALUE); //MaxValue steht für unendlich
        }

        queue.add(fromCity);
        totalDistance.put(fromCity,0);  //Startknoten hat Distanz 0 (nicht unendlich)


        while (!queue.isEmpty()) {
            City current = queue.remove();  //Stadt/ Knoten mit geringster Entfernung holen, der ist dann der current-Knoten, hierfür machen wir eine eigene "Heap"
            //ist von Laufzeit jetzt nicht optimal, da wir aber Heap noch nicht behandelt haben bauen wir uns einen eigenen SimpleHeap aus einem Array



            List<Street> connections = getConnectedCities(current);     //holt die Straßen der Stadt alternativ: current.connections/ Wir gehen Verbindungen des Knotens durch
            for (Street street : connections) {
                if (totalDistance.get(street.to)>totalDistance.get(current)+ street.distance) {   //wir prüfen ob Distanz der City die in Liste steht geringer ist als neu berechnete Distanz (aktuellerKnoten + Weg zum Nachbern)

                    //wenn geringerer Wert neu berechnet, dann wird der jetzt in distance-Liste gelegt
                    totalDistance.put(street.to, totalDistance.get(current) + street.distance); //aktualisieren Distanz-Wert des untersuchten Ziel-Knoten in HAshMap auf neues Minimum
                    vorgaengerKnoten.put(street.to, street); //Wir setzen den Richtungspfeil auf den Vorgängerknoten der mit geringster Distanz zum aktuellen Knoten geführt hat
                }



                //Wann ist klar, dass Verbindung besteht? Sobald street.to identisch ist mit City toCity
                if (toCity.equals(street.to)) {
                    ArrayList<Street> way = new ArrayList<>();  //hierin werden alle Straßen der Reihe nach abgespeichert
                    while (street != null) {     //bis zur Startcity, denn nur hier ist Wert=null
                        way.add(street);
                        City visitedFrom = street.from;
                        street = vorgaengerKnoten.get(visitedFrom);
                    }
                    return way;     //durch return an dieser Stelle wird Breitensuche Abgebrochen, sobal erster Weg gefunden wurde
                }
            }
        }
        return null;
    }
}





