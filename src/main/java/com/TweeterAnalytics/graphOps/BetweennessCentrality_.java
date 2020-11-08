package com.TweeterAnalytics.graphOps;

import com.TweeterAnalytics.GraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BetweennessCentrality_<V> {
    private Graph<V, DefaultWeightedEdge> G;
    private FloydWarshallShortestPaths<V, DefaultWeightedEdge> fw;
    BetweennessCentrality<V, DefaultWeightedEdge> bc;

    public BetweennessCentrality_( Graph<V, DefaultWeightedEdge> g ) {

    G = g;
    G.edgeSet().forEach( e -> G.setEdgeWeight( e, 1 ) );
    fw = new FloydWarshallShortestPaths<>(G);
    this.bc = new BetweennessCentrality<>(G);
    }

    public List<Double> allCentralities() {
        int n = G.vertexSet().size();
        return bc.
                getScores().
                values().
                stream().
                mapToDouble( x -> x / ( ( n - 1 ) * ( n - 2 ) ) ).
                boxed().
                collect(Collectors.toList());
    }

    public double averageCentrality() {
        int n = G.vertexSet().size();
        return  bc.
                getScores().
                values().
                stream().
                mapToDouble( x -> x / ( ( n - 1 ) * ( n - 2 ) ) ).
                sum() / n;
    }

    public double getBiggestCentrality() {
        int n = G.vertexSet().size();
        G.edgeSet().forEach( e -> G.setEdgeWeight( e, 1 ) );
        return  bc.
                getScores().
                values().
                stream().
                mapToDouble( x -> x / ( ( n - 1 ) * ( n - 2 ) ) ).
                max().
                getAsDouble();
    }

    public double clusterBetweennessCentrality(V v, Set<V> cluster1, Set<V> cluster2 ) {
        if ( cluster1.contains(v) || cluster2.contains(v) )
            throw new IllegalArgumentException("the clusters must not contain v");

        int n = cluster1.size() + cluster2.size();

        AllDirectedPaths<V, DefaultWeightedEdge> pathsObj = new AllDirectedPaths<>(G);

        List<GraphPath<V, DefaultWeightedEdge>> paths =
                cluster1.stream().map( v1 ->
                    cluster2.stream().map( v2 -> {
                        int shortestPathLength = (int) fw.getPathWeight( v1, v2 );
                        List<GraphPath<V, DefaultWeightedEdge>> pathsv1Tov2;

                        if ( shortestPathLength < 771 ) {
                            pathsv1Tov2 = pathsObj.getAllPaths( v1, v2, true, shortestPathLength );
                            return pathsv1Tov2;
                        }

                        return new ArrayList<GraphPath<V, DefaultWeightedEdge>>();

                    } ).collect(Collectors.toList())
                ).collect(Collectors.toList()).
                        stream().
                        flatMap(List::stream).
                        flatMap(List::stream).
                        collect(Collectors.toList());

        int numOfShortestPaths = paths.size();
        int numOfShortestPathsThroughV =
                paths.
                stream().
                        filter( path -> path.getVertexList().contains(v) ).
                        collect(Collectors.toList()).
                        size();

        if (!(numOfShortestPathsThroughV == 0 && numOfShortestPaths == 0))
            return ( (double) numOfShortestPathsThroughV / (double)numOfShortestPaths ) / (double)( ( n - 1 ) * ( n - 2 ) );
        else
            return 0;
    }

}
