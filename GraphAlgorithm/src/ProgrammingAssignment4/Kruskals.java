package ProgrammingAssignment4;
/**
 * Bowen Kruse
 * 6/4/2019
 * Java implementation of Kruskals algorithm to find the MST
 * of a tree built from file AdjacencyList.txt that then
 * prints the MST to Kruskalsout.txt
 * Simplified version of Kruskals algorithm example
 * from "Algorithms, 4th Edition by Robert
 * Sedgewick and Kevin Wayne" pages 624 - 627
 * or at https://algs4.cs.princeton.edu/43mst/
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Kruskals {

    static class Edge {
        int here;
        int there;
        int weight;

        public Edge(int here, int there, int weight) {
            this.here = here;
            this.there = there;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<Edge> allEdges = new ArrayList<>();

        Graph(int vertices) {
            this.vertices = vertices;
        }

        public void addEdge(int here, int there, int weight) {
            Edge edge = new Edge(here, there, weight);
            allEdges.add(edge);
        }

        public void Kruskal() throws IOException {
            PriorityQueue<Edge> EdgeQueue = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.weight));
            for (int i = 0; i <allEdges.size() ; i++) {
                EdgeQueue.add(allEdges.get(i));
            }

            int [] parent = new int[vertices];

            makeSet(parent);

            ArrayList<Edge> mst = new ArrayList<>();

            int index = 0;
            while(index<vertices-1){
                Edge edge = EdgeQueue.remove();

                int x_set = find(parent, edge.here);
                int y_set = find(parent, edge.there);

                if(x_set==y_set){

                }
                else {
                    mst.add(edge);
                    index++;
                    combine(parent,x_set,y_set);
                }
            }

            printGraph(mst);
        }


        public void makeSet(int [] parent){
            for (int i = 0; i <vertices ; i++) {
                parent[i] = i;
            }
        }

        public int find(int [] parent, int vertex){
            if(parent[vertex]!=vertex)
                return find(parent, parent[vertex]);;
            return vertex;
        }

        public void combine(int [] parent, int x, int y){
            int x_parent = find(parent, x);
            int y_parent = find(parent, y);
            parent[y_parent] = x_parent;
        }

        public void printGraph(ArrayList<Edge> edgeList) throws IOException {
            PrintWriter Fileout = new PrintWriter(new FileWriter("Kruskalsout.txt"));
            Fileout.println("Minimum Spanning Tree Via Kruskal's Algorithm: ");
            for (int i = 0; i <edgeList.size() ; i++) {
                Edge edge = edgeList.get(i);

                Fileout.println("\n\nEdge-" + i + " source: " + edge.here +
                        " destination: " + edge.there +
                        " weight: " + edge.weight);
            }
            Fileout.close();
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File( "src/AdjacencyList.txt");
        Scanner source = new Scanner(file);

        int vertices = 6;

        Graph Test = new Graph(vertices);
        while (source.hasNext()) {
            String output = source.nextLine();
            String[] split = output.split(" ");
            Test.addEdge(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
        }
        Test.Kruskal();
    }
}
