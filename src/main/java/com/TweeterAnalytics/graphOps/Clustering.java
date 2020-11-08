package com.TweeterAnalytics.graphOps;

import com.TweeterAnalytics.User;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.flow.DinicMFImpl;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.flow.GusfieldGomoryHuCutTree;
import org.jgrapht.alg.interfaces.MaximumFlowAlgorithm;
import org.jgrapht.alg.interfaces.MinimumSTCutAlgorithm;
import org.jgrapht.graph.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Clustering<V> {
    private Graph<V, DefaultWeightedEdge> G;
    private Graph<V, DefaultWeightedEdge> GHTree;
    private List<Set<V>> clusters;

    public Clustering( Graph<V, DefaultWeightedEdge> g, double alpha ) {
        /*
        *
        * recommended value for alpha: 0.2
        *
        */

        DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> directed = new DefaultDirectedWeightedGraph<>( DefaultWeightedEdge.class );
        DefaultUndirectedWeightedGraph<V, DefaultWeightedEdge> undirected = new DefaultUndirectedWeightedGraph<>( DefaultWeightedEdge.class );

        g.vertexSet().stream().forEach( v -> directed.addVertex(v) );
        g.edgeSet().stream().forEach( e -> directed.addEdge( g.getEdgeSource(e), g.getEdgeTarget(e), e ) );

        removeAntiParallelEdges(directed);

        normaliseEdgeWeights(directed);

        directed.vertexSet().stream().forEach( v -> undirected.addVertex(v) );
        directed.edgeSet().stream().forEach( e -> undirected.addEdge( directed.getEdgeSource(e), directed.getEdgeTarget(e), e ) );

        G = undirected;
        cluster( alpha );
    }

    private void removeAntiParallelEdges( Graph<V, DefaultWeightedEdge> g ) {
        Set<DefaultWeightedEdge> toBeRemoved = new HashSet<>();

        g.vertexSet().stream().forEach( v -> {
            g.outgoingEdgesOf(v).stream().forEach( e -> {
                g.outgoingEdgesOf( g.getEdgeTarget(e) ).stream().filter( e2 ->
                g.getEdgeTarget(e2) == v ).forEach( e2 -> {
                    double total = g.getEdgeWeight(e2) + g.getEdgeWeight(e);
                    g.setEdgeWeight(e2, total);
                    toBeRemoved.add(e);
                } );
            });
        } );

        toBeRemoved.forEach( e -> g.removeEdge(e) );

    }

    private void normaliseEdgeWeights(Graph<V, DefaultWeightedEdge> g ) {
        g.vertexSet().stream().forEach( v -> {

            double sum = g.outgoingEdgesOf(v).stream().mapToDouble( e -> g.getEdgeWeight(e) ).sum();

            g.outgoingEdgesOf(v).stream().forEach( e -> g.setEdgeWeight(e, g.getEdgeWeight(e) / sum) );

        } );
    }

    public List<Set<V>> getClusters() { return clusters; }

    private void cluster( double alpha ) {
        V added;
        Graph<V, DefaultWeightedEdge> G_alpha = new DefaultUndirectedWeightedGraph<>( DefaultWeightedEdge.class );
        added = getExpandedGraph( G, alpha, G_alpha );

        float density = G_alpha.vertexSet().size() / G_alpha.edgeSet().size();
        MinimumSTCutAlgorithm maxFlow = ( density < 1 ) ? new DinicMFImpl( G_alpha ) : new EdmondsKarpMFImpl( G_alpha );
        GHTree = new GusfieldGomoryHuCutTree( G_alpha, maxFlow ).getGomoryHuTree();

        GHTree.removeVertex( added );

        ConnectivityInspector inspector = new ConnectivityInspector( GHTree );
        List<Set<V>> allCs = inspector.connectedSets();
        this.clusters = allCs.stream().filter(set -> set.size() > 1).sorted(setSizeComparator())
                .collect(Collectors.toList());

    }

    private V getExpandedGraph( Graph<V, DefaultWeightedEdge> g, double alpha, Graph<V, DefaultWeightedEdge> expanded ) {

//        V t = (V)g.vertexSet().toArray()[0];
        V t = (V)(new User());

        g.vertexSet().stream().forEach( v -> expanded.addVertex(v) );
        expanded.addVertex( t );
        g.edgeSet().stream().forEach( e -> { expanded.addEdge( g.getEdgeSource(e), g.getEdgeTarget(e), e ); } );
        g.vertexSet().stream().forEach( v -> {
            DefaultWeightedEdge e = new DefaultWeightedEdge();
            expanded.addEdge( t, v, e ) ;
            expanded.setEdgeWeight( e, alpha );
        } );

        return t;
    }

    public void getBiggestClusters( int minSize ) {

        if (! (minSize >= 0 && minSize <= clusters.stream().mapToInt( set -> set.size() ).max().getAsInt()) )
            throw new IllegalArgumentException( "Size must be a positive integer, at most the size of the biggest cluster." );

        this.clusters =
                clusters.
                stream().
                        filter( set -> set.size() >= minSize ).
                        sorted(setSizeComparator()).
                        collect(Collectors.toList());
    }

    private Comparator<Set<V>> setSizeComparator() {
        return new Comparator<Set<V>>() {
            @Override
            public int compare(Set<V> vs, Set<V> t1) {
                int s1 = vs.size();
                int s2 = t1.size();

                if ( s1 > s2 ) return  1;
                if ( s1 < s2 ) return -1;
                return 0;
            }
        };
    }

}
