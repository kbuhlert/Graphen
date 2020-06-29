package citygraph;

public class CityDistance {
    public City city;
    public int totalDistance;

    //CityDistance ist Hilfsklasse um weiterers Attribut (totalDistance) zu City nutzen zu können ohne die Cityklasse ändern zu müssen

    public CityDistance(City city, int totalDistance) {
        this.city = city;
        this.totalDistance = totalDistance;
    }
}
