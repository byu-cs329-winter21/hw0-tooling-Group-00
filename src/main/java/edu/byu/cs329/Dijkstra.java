package edu.byu.cs329;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
  static final int M = Integer.MAX_VALUE;
  private int[][] graph;
  private int dimension;
  private int[][] li = null;

  /** constructor of Dijkstra class.
  * @return void
  */
  public Dijkstra(int[][] g) {

    if (g == null) {
      throw new IllegalArgumentException("The graph must be non-null");
    }
    dimension = g.length;
    if (dimension == 0) {
      throw new IllegalArgumentException("The graph must be non-empty");
    }
    for (int i = 0; i < dimension; ++i) {
      if (g[i].length != dimension) {
        throw new IllegalArgumentException("The graph must be square");
      }
    }
    graph = g;
  }

  /** get all shortest path lengths.
  * @return void
  */
  private void allShortestPathLengths() {
    int[][] lengths = new int[graph.length][graph.length];
    initializeL(graph, lengths);
    for (int a = 0; a < lengths.length; ++a) {
      int[] thisL = lengths[a];
      Set<Integer> s = new HashSet<Integer>();
      while (s.size() < lengths.length) {
        int u = getMinimumIndex(thisL, s);
        s.add(u);
        for (int v = 0; v < thisL.length; ++v) {
          if (s.contains(v)) {
            continue;
          }
          int newDistance = thisL[u] + graph[u][v];
          if (newDistance > 0 && newDistance < thisL[v]) {
            thisL[v] = newDistance;
          }
        }
      }
    }

    li = lengths;
  }

  /** find a single shortest path. 
  * @return the length of a shortest path
  */
  public int shortestPath(int from, int to) {
    if (from < 0 || to < 0) {
      throw new IllegalArgumentException("Indices must be nonnegative!");
    }
    if (from >= graph.length || to >= graph.length) {
      throw new IllegalArgumentException("Indices must be within the graph's dimension!");
    }
    if (li == null) {
      allShortestPathLengths();
    }
    return li[from][to];
  }

  static int getMinimumIndex(final int[] thisL, final Set<Integer> s) {
    int u = M;
    final int length = thisL.length;
    for (int i = 0; i < length; ++i) {
      if (s.contains(i)) {
        continue;
      }
      if (u == M || thisL[i] < thisL[u]) {
        u = i;
      }
    }
    return u;
  }

  private void initializeL(int[][] graph, int[][] li) {
    for (int i = 0; i < graph.length; ++i) {
      for (int j = 0; j < graph.length; ++j) {
        if (i == j) {
          li[i][j] = 0;
        } else {
          li[i][j] = M;
        }
      }
    }
  }

  /** convert the table to a string.
  * @return string
  */
  public static String tableToString(int[][] l) {
    StringBuilder sb = new StringBuilder();
    String eol = System.getProperty("line.separator");
    int length = l.length;
    for (int i = 0; i < length; ++i) {
      int[] tl = l[i];
      for (int j = 0; j < length; ++j) {
        int value = tl[j];
        if (value == M) {
          sb.append("-");
        } else {
          sb.append(tl[j]);
        }
        if (j < length - 1) {
          sb.append(" ");
        }
      }
      if (i < length - 1) {
        sb.append(eol);
      }
    }

    return sb.toString();
  }

}
