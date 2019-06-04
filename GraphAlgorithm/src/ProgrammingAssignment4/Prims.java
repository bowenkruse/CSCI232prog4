package ProgrammingAssignment4;
/**
 * Bowen Kruse
 * 6/1/2019
 * Implementation of Prims algorithm to find the MST
 * from a weighted adjacency matrix
 * Simplified version of Prims algorithm example
 * from "Algorithms, 4th Edition by Robert
 * Sedgewick and Kevin Wayne" pages 616 - 622
 * or at https://algs4.cs.princeton.edu/43mst/
 * This Program takes from an adjacency matrix and prints to
 * file PrimOutput.txt
 */
import java.io.*;
import java.util.Scanner;

public class Prims {
    private boolean[] unset;
    private boolean[] set;
    private int vertixQuan;
    private int AdjacencyMatrix[][];
    private int key[];
    private static final int INFINITE = 999;
    private int parent[];


    public Prims(int vertixQuan) throws IOException {
        this.vertixQuan = vertixQuan;
        unset = new boolean[vertixQuan + 1];
        set = new boolean[vertixQuan + 1];
        AdjacencyMatrix = new int[vertixQuan + 1][vertixQuan + 1];
        key = new int[vertixQuan + 1];
        parent = new int[vertixQuan + 1];
    }

    public int unsetCount(boolean unset[]) {
        int count = 0;
        for (int index = 0; index < unset.length; index++) {
            if (unset[index]){
                count ++;
            }
        }
        return count;
    }

    public void PrimAlgo(int AdjacencyMatrix[][]) {
        int evalVertx;
        for (int i = 1; i <= vertixQuan; i++) {
            for (int j = 1; j <= vertixQuan; j++) {
                this.AdjacencyMatrix[i][j] = AdjacencyMatrix[i][j];
            }
        }
        for (int x = 1; x <= vertixQuan; x++) {
            key[x] = INFINITE;
        }
        key[1] = 0;
        unset[1] = true;
        parent[1] = 1;

        while (unsetCount(unset) != 0) {
            evalVertx = getMinKeyFromUnsettled(unset);
            unset[evalVertx] = false;
            set[evalVertx] = true;
            evalNeighbors(evalVertx);
        }
    }

    private int getMinKeyFromUnsettled(boolean[] unsetB) {
        int min = Integer.MAX_VALUE;
        int node = 0;
        for (int i = 1; i <= vertixQuan; i++) {
            if (unset[i] == true && key[i] < min) {
                node = i;
                min = key[i];
            }
        }
        return node;
    }

    public void evalNeighbors(int evalVertex) {
        for (int desti = 1; desti <= vertixQuan; desti++) {
            if (set[desti] == false) {
                if (AdjacencyMatrix[evalVertex][desti] != INFINITE) {
                    if (AdjacencyMatrix[evalVertex][desti] < key[desti]) {
                        key[desti] = AdjacencyMatrix[evalVertex][desti];
                        parent[desti] = evalVertex;
                    }
                    unset[desti] = true;
                }
            }
        }
    }

    public void printPrims() throws IOException {
        PrintWriter Fileout = new PrintWriter(new FileWriter("PrimOutput.txt"));
        Fileout.println("Source  : Destination = Weight");
        for (int vertex = 2; vertex <= vertixQuan; vertex++) {
            Fileout.println(parent[vertex] + "\t:\t" + vertex + "\t=\t" + AdjacencyMatrix[parent[vertex]][vertex]);
     }
        Fileout.close();
    }

    public static void main(String args[]) throws IOException {
        File file = new File( "src/matrix.txt");
        Scanner source = new Scanner(file);
        source.useDelimiter("[\\s,]+");
        int[][] adjacencyMatrix = new int[6][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                adjacencyMatrix[i][j] = source.nextInt();
            }
        }
        source.close();
        Prims prim = new Prims(5);
        prim.PrimAlgo(adjacencyMatrix);
        prim.printPrims();

    }
}



