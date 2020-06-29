package Application;

import citygraph.City;
import citygraph.Citygraph;
import citygraph.Street;

import java.util.Collections;
import java.util.List;

public class CityApplication {

    public static void main(String[] args) {
        Citygraph graph = new Citygraph(false);
        City graz = new City("Graz", 300000, "AT");
        City wien = new City("Wien", 1000000, "AT");
        City paris = new City("Paris", 18000000, "AT");
        City muenchen = new City("München", 300000, "AT");

        graph.addCity(graz);
        graph.addCity(muenchen);
        graph.addCity(wien);
        graph.addCity(paris);

        graph.addStreet(graz, wien, 250);
        graph.addStreet(graz, muenchen, 400);
        graph.addStreet(wien, muenchen, 500);
        graph.addStreet(paris, muenchen, 1000);
        //zusätzliche Straße wienParis --> Graz-Paris geht dann über Wien, weil Wien zuerst in Queue gelegt wird
        graph.addStreet(wien, paris, 1200);

        boolean grazParis = graph.searchConnection(graz, paris);
        System.out.println(grazParis);

        List<Street> way = graph.searchWay(graz, paris);
        //Liste muss in umgekehrter Reihe ausgegeben werden,
        Collections.reverse(way);
        //über umgekehrte Liste durchgehen
        for (Street street : way){
            System.out.println("von " + street.from.name + " nach " + street.to.name + " /Entfernung: " + street.distance );
            //mit from.name wird Name der Stadt und nicht nur der Objekt-Speicherort ausgegeben
        }

    }

}
