package citygraph;

public class Street {

    //Verbindungen zwischen cities

    public City from;
    public City to;
    public int distance;

    public Street(City from, City to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}
