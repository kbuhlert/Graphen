package Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ListGraph implements Graph {

	private ArrayList<WeightedEdge>[] edges;
	private int numVertices;
	private boolean directed;
	
	@SuppressWarnings("unchecked")
	public ListGraph(int numVertices, boolean directed) {
		edges = new ArrayList[numVertices];
		for (int i=0; i < numVertices; i++)
			edges[i] = new ArrayList<WeightedEdge>();
		this.numVertices = numVertices;
		this.directed = directed;
	}
	
	public int numVertices() {
		return numVertices;
	}
	
	public boolean isDirected() {
		return directed;
	}

	public boolean hasEdge(int u, int v) {		//Überprüft ob Kante zwischen zwei Knoten existiert
		WeightedEdge pv = findEdge(u, v);		// wenn ja, dann return true, sonst false
		return pv != null;
	}

	public int getEdgeWeight(int u, int v) {
		WeightedEdge pv = findEdge(u, v);
		return pv.weight;			//hier ist null-Pointer-Exception (falls Kante nicht gefunden wird), die ignorieren wir aber erst mal
	}

	public void addEdge(int u, int v) {
		addEdge(u, v, 1);
	}	//Rekursiver Aufruf von addEdge mit Gewichtung 1
	
	public void addEdge(int u, int v, int weight) {		//Zufügen der Kanten mit Gewichtung
		edges[u].add(new WeightedEdge(u, v, weight));
		if (!directed) {								//Wenn kannten nicht gerichtet, wird Kante gleich auch in andere Richtung erstellt
			edges[v].add(new WeightedEdge(v, u, weight));
		}
	}

	//Kante finden
	//u = IndexNr von Startknoten
	//v = IndexNr von Zielknoten

	private WeightedEdge findEdge(int u, int v) {
		for (WeightedEdge we: edges[u]) {
			if (we.to_vertex == v) {
				return we;				//return weighted Edge wenn Kannte gefunden, sonst null
			}
		}
		return null;
	}

	public void removeEdge(int u, int v) {
		// TODO
	}

	public List<WeightedEdge> getEdges(int v) {
		return edges[v];
	}	//returniert die gesamte ArrayList, weil die alle Kanten enthält

	//Methode Prüft ob es sich um zusammenhängende Liste handelt -->wenn true = Graph ist zusammenhängend
	public boolean isConnected(){		//todo: =Breitensuche!!
		boolean visited[] = new boolean[numVertices];
		ArrayList<Integer> queue = new ArrayList<>();	//Ginge auch micht echter queue und ArrayDeque
		queue.add(0);	//Startknoten kann zufällig gewählt werden, wir wählen Knoten=0
		//Füge Knoten mit Kanten in Queue ein, damit wir wissen was angeschaut wurde

		//visited[0] = true; -->nicht sicher ob das notwendig ist, legt Startknoten auf visited

		while (!queue.isEmpty()){
			//holen un saktuellen Knoten, den wir anschauen
			int current = queue.remove(0);	//Knoten bei Queue-Index=0 (= Anfang der Queue)
			//wollen uns alle Knoten die angeschaut wurden an
			//visited[current] = true;
			List<WeightedEdge> currentEdges = getEdges(current); //holen uns Kanten vom aktuellen Knoten
			for(WeightedEdge we : currentEdges){	//alle Kanten des Knoten durchgehen
				//nur Kanten zufügen, die noch nicht besucht wurden:
				if(!visited[we.to_vertex]){		//to_vertex ist Attribut aus Kanten-Klasse/ da if(!visited) in Schleife liegt, kommt es nicht zu doppelten Besuchen
				//wir gehen über alle Kanten des Knoten und fügen die Zugehörigen Knoten in Queue
				queue.add(we.to_vertex);
					visited[current] = true;	//wenn visited=true an dieser stelle gesetzt wird, ist Knoten besucht sobald er in Queue gelegt wird, das verhindert, dass Knoten doppelt in Queue gelegt werden
				}
			}
		}
		//Überprüfung: haben wir alle Knoten besucht? -->Ist Graph zusammenhängend?
		for(boolean visit : visited){	//alle Knotenelemente im visited[]-Array
			if(!visit){		//wenn wir einen Knoten finden, der nicht besucht wurde ist Graph nicht zusammenhängend
				return false;	//return fals = Graph ist nicht zusammenhängend
			}
		}
		return true;	//true, wenn alle Knoten visited, (ansonsten vorher return false)
	}
}
