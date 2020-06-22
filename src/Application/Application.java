package Application;

import Graph.ListGraph;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ListGraph graph = new ListGraph(8,false);   //neuer Graph mit 8 Knoten
        graph.addEdge(0,5,3);
        graph.addEdge(5,7,4);
        graph.addEdge(7,3,-2);  //wir wissen, einfacher Graph, gewichtet, nicht zusammenhängend (einige Knoten ohne Verbindung)
        graph.addEdge(1,2,8);
        graph.addEdge(2,4,6);
        graph.addEdge(2,6,8);

        //graph.addEdge(4,5,8);   //durch diesen Knoten wird Graph zusammenhängend

        System.out.println(graph.hasEdge(3,7)); //diese Verbindung gibt es automatisch weil Knoten nicht gerichtet ist (wird gleich miterstellt)
        System.out.println(graph.getEdgeWeight(5,7));
        System.out.println(graph.isConnected());
    }
}
