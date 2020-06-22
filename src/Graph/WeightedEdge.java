package Graph;

public class WeightedEdge {
	public int from_vertex;	//Knoten sind als Index-Nr. gespeichert
	public int to_vertex;
	public int weight;

	public WeightedEdge(int from_vertex, int to_vertex, int weight) {
		this.from_vertex = from_vertex;
		this.to_vertex = to_vertex;
		this.weight = weight;
	}
	
}
