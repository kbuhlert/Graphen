package citygraph;

import java.util.ArrayList;

public class City {

    public String name;     //public erspart uns extra getter und setter
    public int population;
    public String nation;

    public ArrayList<Street> connections;   //Liste mit allen Straßen zu anderen Städten

    public City(String name, int population, String nation) {
        this.name = name;
        this.population = population;
        this.nation = nation;
        this.connections = new ArrayList<>();
    }


}
